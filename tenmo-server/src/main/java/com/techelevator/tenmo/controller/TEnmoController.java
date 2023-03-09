package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TEnmoController {
    private TransferDao transferDao;
    private AccountDao accountDao;
    private UserDao userDao;
    public TEnmoController(TransferDao transferDao, AccountDao accountDao, UserDao userDao){
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/send", method = RequestMethod.POST)
    public void sendMoney(@Valid @RequestBody Transfer transfer){
        transferDao.sendMoney(transfer);
    }

    @RequestMapping(path = "/request", method = RequestMethod.POST)
    public void requestMoney(@Valid @RequestBody Transfer transfer){
        transferDao.requestMoney(transfer);
    }

    @RequestMapping(path = "/transfer/{id}", method = RequestMethod.GET)
    public Transfer viewTransferById(@Valid @PathVariable int id){
        return transferDao.viewTransferById(id);
    }

    @RequestMapping(path = "/transfer/pending/{id}", method = RequestMethod.GET)
    public List<Transfer> getPendingTransfers(@Valid @PathVariable int id){
        return transferDao.getPending(id);
    }

    @RequestMapping(path = "/balance/{id}", method = RequestMethod.GET)
    public BigDecimal getBalance(@Valid @PathVariable int id){
        return accountDao.getBalance(id);
    }

    @RequestMapping(path = "/transfers/{id}", method = RequestMethod.GET)
    public List<Transfer> viewAllTransfers(@Valid @PathVariable int id){
        return transferDao.viewTransfersByUser(id);
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userDao.findAll();
    }




    /**
     * Get Account Balance
     */

    /**
     * Transfer Money
     */


    //TODO: Create Transfer DAO (handles if transfer is allowed rules)
    //TODO: Create Account DAO (Holds account balance + userId)



}
