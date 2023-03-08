package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    Transfer sendMoney(Transfer sendTransfer);

    Transfer requestMoney(Transfer receiveTransfer);

    //View details of specific transfer by transfer id
    Transfer viewTransferById(int transferId);

    //View all transfers by specific account
    List<Transfer> viewTransfersByUser(int accountId);

    List<Transfer> getPending(int accountId);
}
