package com.gotryt.coop.service;

import com.gotryt.coop.dto.SharesResponse;
import com.gotryt.coop.exception.SharesException;
import com.gotryt.coop.model.Shares;
import com.gotryt.coop.model.User;

public interface SharesService {
    Shares addShares(User user, Shares sharesDetails) throws SharesException;
    SharesResponse withdrawShares(User user, Shares sharesDetails) throws SharesException;
    SharesResponse approveWithdraw(Long shareId) throws SharesException;
    SharesResponse declineWithdraw(Long shareId, Shares sharesDetails) throws SharesException;
}
