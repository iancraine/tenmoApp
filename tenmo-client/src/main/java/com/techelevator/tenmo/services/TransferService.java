package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

public class TransferService {

    public static final String API_BASE_URL = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();

    private List<Transfer> transfers;

    private String token = null;

    public void setToken(String token) {this.token = token;}

    public BigDecimal viewCurrentBalance(int userId){
        BigDecimal balance = null;
        try{
            ResponseEntity<BigDecimal> response = restTemplate.exchange(API_BASE_URL + "balance/" + userId,
                    HttpMethod.GET, makeAuthEntity(), BigDecimal.class);
            balance = response.getBody();
        }catch (RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }

    public Transfer[] viewAllTransfers(int userid){
        Transfer[] transfers = null;
        try{
            ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL + "transfers/" + userid,
                    HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transfers = response.getBody();
        }catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    public Transfer viewTransferById(int transferId) {
        Transfer transfer = null;
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + "transfer/" + transferId,
                    HttpMethod.GET, makeAuthEntity(), Transfer.class);
            transfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }

    public void sendMoney(Transfer transfer, AuthenticatedUser user){
        if((viewCurrentBalance(user.getUser().getId()).compareTo(transfer.getAmount()) == -1 )){
            System.out.println("Invalid Transfer!");
        } else {
            try {
                restTemplate.exchange(API_BASE_URL + "send", HttpMethod.POST, makeTransferEntity(transfer), Transfer.class);
            } catch (RestClientResponseException | ResourceAccessException e) {
                BasicLogger.log(e.getMessage());
            }
        }
    }

    public void requestMoney(Transfer transfer){
        try{
            restTemplate.exchange(API_BASE_URL + "request", HttpMethod.POST, makeTransferEntity(transfer), Transfer.class);
        }catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }

    public User[] listUsers(){
        User[] users = null;
        try{
            ResponseEntity<User[]> response = restTemplate.exchange(API_BASE_URL + "users", HttpMethod.GET, makeAuthEntity(), User[].class);
            users = response.getBody();
        }catch (RestClientResponseException | ResourceAccessException e) {
        BasicLogger.log(e.getMessage());
        }
        return users;
    }

    public Transfer[] listPendingTransfers(int userId){
        Transfer[] pendingTransfers = null;
        try{
            ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL + "transfer/pending/" + userId, HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            pendingTransfers = response.getBody();
        }catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return pendingTransfers;
    }

    public void rejectTransfer (int transferId){
        try {
            restTemplate.exchange(API_BASE_URL + "reject/" + transferId, HttpMethod.DELETE, makeAuthEntity(), Void.class);

        }catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }

    public void approveTransfer(Transfer approvedTransfer, int userId){
        boolean isAllowed = false;
        try {
           ResponseEntity<Boolean> response = restTemplate.exchange(API_BASE_URL + "approve/" +
                   userId, HttpMethod.PUT, makeTransferEntity(approvedTransfer), Boolean.class);
           isAllowed = response.getBody();
        }catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        if(isAllowed == false){
            System.out.println("You do not have enough money to complete this transaction.");
        } else {
            BigDecimal balance = viewCurrentBalance(userId);
            System.out.println("Transfer Approved.  Your new balance is: $" + balance);
        }

    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<>(transfer, headers);
    }

    private HttpEntity<Void> makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }
}
