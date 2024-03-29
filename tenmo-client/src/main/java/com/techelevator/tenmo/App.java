package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;

import java.math.BigDecimal;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final TransferService transferService = new TransferService();

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        } else {
            transferService.setToken(currentUser.getToken());
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
        System.out.println("Your current balance is: $" + transferService.viewCurrentBalance(currentUser.getUser().getId()));
		// TODO Auto-generated method stub
		
	}

	private void viewTransferHistory() {
        Transfer[] transfers = transferService.viewAllTransfers(currentUser.getUser().getId());
        int transferId = consoleService.printAllTransfers(transfers);
        Transfer transferDetails = null;
        if (transferId != 0){
           transferDetails = transferService.viewTransferById(transferId);
           consoleService.printTransferById(transferDetails);
        }

	}

	private void viewPendingRequests() {
        Transfer[] pendingTransfers = transferService.listPendingTransfers(currentUser.getUser().getId());
        int transferIdForPending = consoleService.printPendingTransfers(pendingTransfers);
        if(transferIdForPending != 0){
            int aOrR = consoleService.approvedOrRejectedMethod();
            if (aOrR == 1){
                Transfer approvedTransfer = null;
                for (int i = 0; i < pendingTransfers.length; i++){
                    if (pendingTransfers[i].getTransferId() == transferIdForPending){
                        approvedTransfer = pendingTransfers[i];
                    }
                }
                transferService.approveTransfer(approvedTransfer, currentUser.getUser().getId());
            }else if (aOrR == 2){
                transferService.rejectTransfer(transferIdForPending);
            }
        }
	}

	private void sendBucks() {
        consoleService.ListUsers(transferService.listUsers(), currentUser.getUser().getId());
        int userId = consoleService.promptForInt("Enter ID of user you are send to (0 to cancel): ");
        BigDecimal sendingAmount = null;
        if(userId !=0){
            sendingAmount = consoleService.promptForBigDecimal("Enter amount: ");
            if(sendingAmount.compareTo(new BigDecimal(0)) == -1 || sendingAmount.compareTo(new BigDecimal(0)) == 0){
                System.out.println("Invalid Transfer Amount");
            }else{
                Transfer transfer = new Transfer("Send", "Pending", currentUser.getUser().getId(), userId, sendingAmount);
                transferService.sendMoney(transfer, currentUser);
                System.out.println("Transfer Sent!");
            }
        }
	}

	private void requestBucks() {
        consoleService.ListUsers(transferService.listUsers(), currentUser.getUser().getId());
        int userId = consoleService.promptForInt("Enter ID of user you are requesting from (0 to cancel): ");
        BigDecimal requestingAmount = null;
        if(userId !=0){
            requestingAmount = consoleService.promptForBigDecimal("Enter amount: ");
            if(requestingAmount.compareTo(new BigDecimal(0)) == -1 || requestingAmount.compareTo(new BigDecimal(0)) == 0){
                System.out.println("Invalid Transfer Amount");
            }else {
                Transfer transfer = new Transfer("Request", "Pending", userId, currentUser.getUser().getId(), requestingAmount);
                transferService.requestMoney(transfer);
                System.out.println("Request Sent!");
            }
        }

	}

}
