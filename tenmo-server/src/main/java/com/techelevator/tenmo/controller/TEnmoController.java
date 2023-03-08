package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TEnmoController {
    private TransferDao transferDao;
    public TEnmoController(TransferDao transferDao){
        this.transferDao = transferDao;
    }

    @RequestMapping(path = "send", method = RequestMethod.POST)
    public void sendMoney(@RequestBody Transfer transfer){
        transferDao.sendMoney(transfer);

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
