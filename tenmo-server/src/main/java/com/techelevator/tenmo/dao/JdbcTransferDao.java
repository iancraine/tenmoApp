package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcTransferDao implements TransferDao{


    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    public JdbcTransferDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void sendMoney(Transfer sendTransfer) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) "+
                "VALUES((SELECT transfer_type_id FROM transfer_type WHERE transfer_type_desc = 'Send'), " +
                "(SELECT transfer_status_id FROM transfer_status WHERE transfer_status_desc = 'Pending')," +
                "?, ?, ?);";
        jdbcTemplate.update(sql, sendTransfer.getAccountFrom(), sendTransfer.getAccountTo(), sendTransfer.getAmount());
    }

    @Override
    public void requestMoney(Transfer requestTransfer) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) "+
                "VALUES((SELECT transfer_type_id FROM transfer_type WHERE transfer_type_desc = 'Request'), " +
                "(SELECT transfer_status_id FROM transfer_status WHERE transfer_status_desc = 'Pending')," +
                "?, ?, ?);";
        jdbcTemplate.update(sql, requestTransfer.getAccountFrom(), requestTransfer.getAccountTo(), requestTransfer.getAmount());
    }

    @Override
    public Transfer viewTransferById(int transferId) {
        String sql = "SELECT transfer_id, transfer_type_desc, transfer_status_desc, account_from, account_to, amount " +
                "FROM transfer t " +
                "JOIN transfer_type type ON type.transfer_type_id = t.transfer_type_id " +
                "JOIN transfer_status status ON status.transfer_status_id = t.transfer_status_id " +
                "WHERE transfer_id = ?;";
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, transferId);
        Transfer transfer = null;
        while(row.next()){
            transfer = mapRowToTransfer(row);

        }
        return transfer;
    }

    @Override
    public List<Transfer> viewTransfersByUser(int userId) {
        List<Transfer> transfersList = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_desc, transfer_status_desc, account_from, account_to, amount " +
                "FROM transfer t " +
                "JOIN transfer_type type ON type.transfer_type_id = t.transfer_type_id " +
                "JOIN transfer_status status ON status.transfer_status_id = t.transfer_status_id " +
                "JOIN account a ON a.account_id = account_to OR a.account_id = account_from " +
                "WHERE user_id = ?;";
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, userId);
        while (row.next()){
            Transfer transfer = mapRowToTransfer(row);
            transfersList.add(transfer);
        }

        return transfersList;
    }

    @Override
    public List<Transfer> getPending(int userId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_desc, transfer_status_desc, account_from, account_to, amount " +
                "FROM transfer t " +
                "JOIN transfer_type type ON type.transfer_type_id = t.transfer_type_id " +
                "JOIN transfer_status status ON status.transfer_status_id = t.transfer_status_id " +
                "JOIN account a ON a.account_id = account_to " +
                "WHERE status.transfer_status_id = 1 AND user_id = ?;";
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, userId);
        while(row.next()){
            transfers.add(mapRowToTransfer(row));
        }
        return transfers;
    }

    private Transfer mapRowToTransfer(SqlRowSet row){
        Transfer transfer = new Transfer();
        transfer.setTransferId(row.getInt("transfer_id"));
        transfer.setTransferType(row.getString("transfer_type_desc"));
        transfer.setStatus(row.getString("transfer_status_desc"));
        transfer.setAccountFrom(row.getInt("account_from"));
        transfer.setAccountTo(row.getInt("account_to"));
        transfer.setAmount(row.getBigDecimal("amount"));
        return transfer;
    }
}
