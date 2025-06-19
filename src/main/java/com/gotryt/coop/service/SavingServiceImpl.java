package com.gotryt.coop.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gotryt.coop.model.Saving;
import com.gotryt.coop.model.User;
import com.gotryt.coop.repository.SavingRepository;
import com.gotryt.coop.repository.UserRepository;
import com.gotryt.coop.utils.TxnIdGen;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SavingServiceImpl implements SavingService {

    private final SavingRepository savingRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Saving saveNow(User user) {
        if (user == null || user.getPlan() == null) {
            throw new IllegalArgumentException("User or Monthly Plan cannot be null");
        }

        // Ensure existing balance is not null
        BigDecimal currentBalance = user.getSavingsBalance() != null ? user.getSavingsBalance() : BigDecimal.ZERO;
        BigDecimal updatedBalance = currentBalance.add(user.getPlan());

        // Update user's savings balance
        user.setSavingsBalance(updatedBalance);
        userRepository.save(user);

        // Create and save new saving record
        Saving newSaving = Saving.builder()
            .amount(user.getPlan())
            .txnId(TxnIdGen.generateTransactionId())
            .balance(updatedBalance)
            .status("submitted")
            .user(user)
            .build();

        log.info("Saving recorded: User={}, Amount={}, New Balance={}", user.getId(), user.getPlan(), updatedBalance);

        return savingRepository.save(newSaving);
    }
}
