package com.techelevator.tenmo.services;

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

    public void sendMoney(Transfer transfer){
        try{
            restTemplate.exchange(API_BASE_URL + "send", HttpMethod.POST,makeTransferEntity(transfer), Transfer.class);
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




    /**
     * 2.) Send money to another user
     * 3.) Request Money from another user
     * 4.) View all of your transfers
     * 5.) View detail of single transfer by ID
     * 6.) View pending requests
     */


    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<>(transfer, headers);
    }

    private HttpEntity<Integer> makeUserIdEntity(int userId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(userId, headers);
    }

    private HttpEntity<Void> makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }
}
