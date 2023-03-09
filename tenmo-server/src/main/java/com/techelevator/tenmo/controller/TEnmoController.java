package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class TEnmoController {
    private TransferDao transferDao;
    private AccountDao accountDao;
    public TEnmoController(TransferDao transferDao, AccountDao accountDao){
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "/send", method = RequestMethod.POST)
    public void sendMoney(@RequestBody Transfer transfer){
        transferDao.sendMoney(transfer);
    }

    @RequestMapping(path = "/request", method = RequestMethod.POST)
    public void requestMoney(@RequestBody Transfer transfer){
        transferDao.requestMoney(transfer);
    }

    @RequestMapping(path = "/transfer/{id}", method = RequestMethod.GET)
    public Transfer viewTransferById(@PathVariable int id){
        return transferDao.viewTransferById(id);
    }

    @RequestMapping(path = "/transfer/pending/{id}", method = RequestMethod.GET)
    public List<Transfer> getPendingTransfers(@PathVariable int id){
        return transferDao.getPending(id);
    }

    @RequestMapping(path = "/balance/{id}", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable int id){
        return accountDao.getBalance(id);
    }

    @RequestMapping(path = "/transfers/{id}", method = RequestMethod.GET)
    public Transfer[] viewAllTransfers(@PathVariable int id){
        return transferDao.viewTransfersByUser(id);
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public User[]




    /**
     * Get Account Balance
     */

    /**
     * Transfer Money
     */


    //TODO: Create Transfer DAO (handles if transfer is allowed rules)
    //TODO: Create Account DAO (Holds account balance + userId)



}
