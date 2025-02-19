package com.gotryt.coop.service;

import com.gotryt.coop.dto.LoanDto;
import com.gotryt.coop.exception.LoanException;
import com.gotryt.coop.model.Loan;
import com.gotryt.coop.model.User;

public interface LoanService {

    public Loan applyLoan(User user, LoanDto loanRequest) throws LoanException;
    public Loan approveLoan(Long loanId) throws LoanException;

}
