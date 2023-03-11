package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    void sendMoney(Transfer sendTransfer);

    void requestMoney(Transfer receiveTransfer);

    //View details of specific transfer by transfer id
    Transfer viewTransferById(int transferId);

    //View all transfers by specific account
    List<Transfer> viewTransfersByUser(int userId);

    List<Transfer> getPending(int userId);

    boolean approveTransfer(Transfer transfer, int userId);

    void rejectTransfer(int transferId);
}
