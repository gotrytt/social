package com.gotryt.coop.service;

import java.math.BigDecimal;

import com.gotryt.coop.model.Saving;
import com.gotryt.coop.model.User;

public interface SavingService {

    public Saving saveNow(User user, BigDecimal amount);
    
}
