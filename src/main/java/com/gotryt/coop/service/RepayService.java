package com.gotryt.coop.service;

import com.gotryt.coop.dto.RepayDto;
import com.gotryt.coop.dto.RepayResponse;
import com.gotryt.coop.exception.LoanException;
import com.gotryt.coop.exception.RepayException;
import com.gotryt.coop.model.User;

public interface RepayService {

    public RepayResponse repayNow(User user, Long loanId, RepayDto repayDto) throws LoanException, RepayException;

    
}
