package com.gotryt.coop.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gotryt.coop.dto.LoanDto;
import com.gotryt.coop.exception.LoanException;
import com.gotryt.coop.model.Loan;
import com.gotryt.coop.model.User;
import com.gotryt.coop.repository.LoanRepository;
import com.gotryt.coop.repository.UserRepository;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Loan applyLoan(User user, LoanDto loanRequest) throws LoanException {
        Loan applyLoan = Loan.builder()
            .amount(loanRequest.getAmount())
            .balance(BigDecimal.ZERO)
            .type(loanRequest.getType())
            .duration(loanRequest.getDuration())
            .user(user)
            .bankName(loanRequest.getBankName())
            .aza(loanRequest.getAza())
            .accountName(loanRequest.getAccountName())
            .purpose(loanRequest.getPurpose())
            .status("submitted")
            .build();

        return loanRepository.save(applyLoan);
    }

    @Override
    public Loan approveLoan(Long loanId) throws LoanException {
        Loan applyLoan = loanRepository.findById(loanId)
            .orElseThrow(() -> new LoanException("Loan not found"));

        // Get the current date
        LocalDate todaysDate = LocalDate.now();

        // Set loan details
        applyLoan.setStatus("approved");
        applyLoan.setBalance(applyLoan.getAmount());
        applyLoan.setStartDate(todaysDate);

        // Convert duration to long before adding months
        long durationInMonths = Long.parseLong(applyLoan.getDuration());
        applyLoan.setEndDate(todaysDate.plusMonths(durationInMonths));

        // Calculate repayment amount
        if (durationInMonths > 0) {
            applyLoan.setRepayAmount(applyLoan.getAmount().divide(BigDecimal.valueOf(durationInMonths), RoundingMode.HALF_UP));
        } else {
            throw new LoanException("Duration must be greater than zero");
        }

        User user = applyLoan.getUser();
        user.setLoanBalance(user.getLoanBalance().add(applyLoan.getAmount()));
        userRepository.save(user);

        return loanRepository.save(applyLoan);
    }
}
