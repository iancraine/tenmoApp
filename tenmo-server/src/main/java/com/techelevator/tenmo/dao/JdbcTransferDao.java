package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public class JdbcTransferDao implements TransferDao{

    @Override
    public Transfer sendMoney(Transfer sendTransfer) {
        return null;
    }

    @Override
    public Transfer requestMoney(Transfer receiveTransfer) {
        return null;
    }

    @Override
    public Transfer viewTransferById(int transferId) {
        return null;
    }

    @Override
    public List<Transfer> viewTransfersByUser(int accountId) {
        return null;
    }

    @Override
    public List<Transfer> getPending(int accountId) {
        return null;
    }
}
