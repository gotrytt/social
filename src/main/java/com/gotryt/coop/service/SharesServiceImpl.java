package com.gotryt.coop.service;

import com.gotryt.coop.dto.SharesResponse;
import com.gotryt.coop.exception.SharesException;
import com.gotryt.coop.model.Shares;
import com.gotryt.coop.model.User;
import com.gotryt.coop.repository.SharesRepository;
import com.gotryt.coop.repository.UserRepository;
import com.gotryt.coop.utils.TxnIdGen;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SharesServiceImpl implements SharesService {

    private final SharesRepository sharesRepository;
    private final UserRepository userRepository;

    @Override
    public Shares addShares(User user, Shares sharesDetails) {
        BigDecimal latestBalance = user.getSharesBalance() != null ? user.getSharesBalance() : BigDecimal.ZERO;
        BigDecimal newBalance = latestBalance.add(sharesDetails.getAmount());

        Shares shares = Shares.builder()
                .user(user)
                .txnId(TxnIdGen.generateTransactionId())
                .type("credit")
                .status("submitted")
                .amount(sharesDetails.getAmount())
                .balance(newBalance)
                .createdAt(LocalDateTime.now())
                .build();

        // Update user balance
        user.setSharesBalance(newBalance);
        userRepository.save(user);

        return sharesRepository.save(shares);
    }

    @Override
    public SharesResponse withdrawShares(User user, Shares sharesDetails) throws SharesException {
        BigDecimal latestBalance = user.getSharesBalance() != null ? user.getSharesBalance() : BigDecimal.ZERO;
        BigDecimal amountToWithdraw = sharesDetails.getAmount();

        if (amountToWithdraw == null || amountToWithdraw.compareTo(BigDecimal.ZERO) <= 0) {
            return SharesResponse.builder()
            .responseCode("419")
            .responseMessage("Invalid withdrawal amount.")
            .build();
        }

        if (latestBalance.compareTo(amountToWithdraw) < 0) {
            return SharesResponse.builder()
            .responseCode("419")
            .responseMessage("Insufficient shares balance.")
            .build();

        }

        BigDecimal newBalance = latestBalance.subtract(amountToWithdraw);

        Shares shares = Shares.builder()
                .user(user)
                .txnId(TxnIdGen.generateTransactionId())
                .type("debit")
                .status("submitted")
                .amount(amountToWithdraw)
                .balance(newBalance)
                .accountDetails(sharesDetails.getAccountDetails())
                .createdAt(LocalDateTime.now())
            .build();

        // Update user balance
        user.setSharesBalance(newBalance);
        userRepository.save(user);

        sharesRepository.save(shares);

        return SharesResponse.builder()
            .responseCode("100")
            .responseMessage("Withdrawal has been placed successfuly...")
            .shares(shares)
            .build();
    }

@Override
public SharesResponse approveWithdraw(Long shareId) throws SharesException {
    Shares share = sharesRepository.findById(shareId)
        .orElseThrow(() -> new SharesException("Share not found"));

    if ("credit".equalsIgnoreCase(share.getType())) {
        return SharesResponse.builder()
            .responseCode("419")
            .responseMessage("No need to approve credit")
            .build();
    }

    if (!"submitted".equalsIgnoreCase(share.getStatus())) {
        return SharesResponse.builder()
            .responseCode("419")
            .responseMessage("Share has already been attended to")
            .build();
    }

    // Approve and update share
    share.setStatus("approved");
    sharesRepository.save(share);

    return SharesResponse.builder()
        .responseCode("100")
        .responseMessage("Withdrawal has been approved successfully...")
        .shares(share)
        .build();
}

@Override
public SharesResponse declineWithdraw(Long shareId, Shares sharesDetails) throws SharesException {
    Shares share = sharesRepository.findById(shareId)
        .orElseThrow(() -> new SharesException("Share not found"));

    if ("credit".equals(share.getType())) {
        return SharesResponse.builder()
            .responseCode("419")
            .responseMessage("No need to decline credit")
            .build();
    }

    if (!"submitted".equals(share.getStatus())) {
        return SharesResponse.builder()
            .responseCode("419")
            .responseMessage("Share has already been attended to")
            .build();
    }

    // Mark the original share as declined with remark
    share.setStatus("declined");
    share.setRemark(sharesDetails.getRemark());
    sharesRepository.save(share);

    // Use user from the original share instead of input
    User user = share.getUser();
    BigDecimal amount = share.getAmount();

    user.setSharesBalance(user.getSharesBalance().add(amount));
    userRepository.save(user);

    Shares shareReversal = Shares.builder()
        .txnId(TxnIdGen.generateTransactionId())
        .type("credit")
        .status("submitted")
        .amount(amount)
        .remark("Reversed: " + sharesDetails.getRemark())
        .balance(user.getSharesBalance())
        .user(user)
        .createdAt(LocalDateTime.now())
        .build();

    sharesRepository.save(shareReversal);

    return SharesResponse.builder()
        .responseCode("100")
        .responseMessage("Withdrawal has been declined successfully...")
        .shares(share)
        .build();
}
}
