package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TEnmoController {
    @Autowired
    private TransferDao transferDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private UserDao userDao;

    @RequestMapping(path = "/send", method = RequestMethod.POST)
    public void sendMoney(@Valid @RequestBody Transfer transfer){transferDao.sendMoney(transfer);}

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

    /**
     * I Changed the @PathVariable to an @RequestParam and sent the userID in an entity w/ the token
     * did not work
     * @return
     */


    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userDao.findAll();
    }




 //TODO: create method that registers pending transaction has been accepted or refused and makes changes accordingly




}
