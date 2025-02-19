package com.gotryt.coop.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gotryt.coop.dto.LoanDto;
import com.gotryt.coop.exception.LoanException;
import com.gotryt.coop.model.Loan;
import com.gotryt.coop.model.User;
import com.gotryt.coop.repository.LoanRepository;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Override
    public Loan applyLoan(User user, LoanDto loanRequest) throws LoanException {

        Loan applyLoan = Loan.builder()
            .amount(loanRequest.getAmount())
            .balance(BigDecimal.ZERO)
            .type(loanRequest.getType())
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
            .orElseThrow(() -> new LoanException("User not found"));

            applyLoan.setStatus("approved");
            applyLoan.setBalance(applyLoan.getAmount());

    return loanRepository.save(applyLoan);

    }

}
