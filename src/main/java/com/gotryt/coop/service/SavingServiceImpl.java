package com.gotryt.coop.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gotryt.coop.model.Saving;
import com.gotryt.coop.model.User;
import com.gotryt.coop.repository.SavingRepository;
import com.gotryt.coop.repository.UserRepository;
import com.gotryt.coop.utils.TxnIdGen;

@Service
public class SavingServiceImpl implements SavingService {

    @Autowired
    private SavingRepository savingRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Saving saveNow(User user, BigDecimal amount) {
        
        // Update user's savings balance before saving the transaction
        user.setSavingsBalance(user.getSavingsBalance().add(amount));
        userRepository.save(user); // Save updated user balance

        // Create and save new saving record
        Saving newSaving = Saving.builder()
            .amount(amount)
            .txnId(TxnIdGen.generateTransactionId())
            .balance(user.getSavingsBalance()) // Ensure balance reflects the updated amount
            .user(user)
            .build();

        return savingRepository.save(newSaving);
    }
}
