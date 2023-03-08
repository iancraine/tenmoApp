package com.techelevator.tenmo.services;

import org.springframework.web.client.RestTemplate;

public class TransferService {
    public static final String API_BASE_URL = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();

    private String token = null;

    public void setToken(String token) {this.token = token;}

    /**
     * 1.) Get Balance Method
     * 2.) Send money to another user
     * 3.) Request Money from another user
     * 4.) View all of your transfers
     * 5.) View detail of single transfer by ID
     * 6.) View pending requests
     */

    //TODO: makeTransferEntity (creates header with token and new transfer object)
    //TODO: makeAuthToken (creates header with token)
}
