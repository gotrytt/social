package com.gotryt.coop.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gotryt.coop.dto.RepayDto;
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
    @Transactional // Ensures all DB operations are atomic
    public Repay repayNow(User user, Long loanId, RepayDto repayDto) throws LoanException, RepayException {

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

        // Create a repayment transaction
        Repay repayLoan = Repay.builder()
            .amount(repaymentAmount)
            .txnId(TxnIdGen.generateTransactionId()) // Generate unique transaction ID
            .balance(remainingBalance)
            .loan(loan)
            .user(user)
            .build();

        // Update the loan balance
        loan.setBalance(remainingBalance);
        loanRepository.save(loan);

        // Update user balance correctly
        user.setLoanBalance(remainingBalance);
        userRepository.save(user);

        return repayRepository.save(repayLoan);
    }
}
