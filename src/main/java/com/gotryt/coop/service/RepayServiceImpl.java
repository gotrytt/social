package com.gotryt.coop.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gotryt.coop.dto.RepayDto;
import com.gotryt.coop.dto.RepayResponse;
import com.gotryt.coop.exception.LoanException;
import com.gotryt.coop.exception.RepayException;
import com.gotryt.coop.model.Loan;
import com.gotryt.coop.model.Repay;
import com.gotryt.coop.model.User;
import com.gotryt.coop.repository.LoanRepository;
import com.gotryt.coop.repository.RepayRepository;
import com.gotryt.coop.repository.UserRepository;
import com.gotryt.coop.utils.TxnIdGen;

@Service
public class RepayServiceImpl implements RepayService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private RepayRepository repayRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public RepayResponse repayNow(User user, Long loanId, RepayDto repayDto) throws LoanException, RepayException {

        if (user == null) {
            throw new RepayException("User not found");
        }

        Loan loan = loanRepository.findById(loanId)
            .orElseThrow(() -> new LoanException("Loan not found"));

        BigDecimal repaymentAmount = repayDto.getAmount();
        BigDecimal remainingBalance = loan.getBalance().subtract(repaymentAmount);

        if (remainingBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new LoanException("Repayment amount exceeds outstanding loan balance");
        }

        // Create repayment transaction
        Repay repayLoan = Repay.builder()
            .amount(repaymentAmount)
            .txnId(TxnIdGen.generateTransactionId())
            .balance(remainingBalance)
            .loan(loan)
            .parentLoanId(loan.getId())
            .loanType(loan.getType())
            .status("submitted")
            .user(user)
            .build();

        // Save repayment and update loan
        repayRepository.save(repayLoan);
        loan.setBalance(remainingBalance);

        if (remainingBalance.compareTo(BigDecimal.ZERO) == 0) {
            loan.setStatus("completed");
        }

        loanRepository.save(loan);

        // ✅ Subtract repayment amount from total loan balance
        BigDecimal updatedLoanBalance = user.getLoanBalance().subtract(repaymentAmount);
        user.setLoanBalance(updatedLoanBalance.max(BigDecimal.ZERO)); // Avoid negative

        userRepository.save(user);

        return RepayResponse.builder()
            .responseCode("100")
            .responseMessage("Repayment successful")
            .repay(repayLoan)
            .build();
    }

}
