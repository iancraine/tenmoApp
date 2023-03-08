package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class JdbcTransferDao implements TransferDao{


    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Override
    public void sendMoney(Transfer sendTransfer) {
        Transfer returnedTransfer = new Transfer();
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) "+
                "VALUES((SELECT transfer_type_id FROM transfer_type WHERE transfer_type_desc = 'Send'), " +
                "(SELECT transfer_status_id FROM transfer_status WHERE transfer_status_desc = 'Pending')," +
                "?, ?, ?);";
        jdbcTemplate.update(sql, sendTransfer.getAccountFrom(), sendTransfer.getAccountTo(), sendTransfer.getAmount());
    }

    @Override
    public void requestMoney(Transfer receiveTransfer) {

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
