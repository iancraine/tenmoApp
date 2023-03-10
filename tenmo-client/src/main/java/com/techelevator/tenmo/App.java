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
        System.out.println("-------------------------------------------");
        System.out.println("Transfer");
        System.out.printf("%-4s    %-20s    %-10s\n","ID", "From/To", "Amount");
        System.out.println("-------------------------------------------");
//        if(transfers.length > 0){
            for (Transfer transfer: transfers){
                if(transfer.getTransferType().equalsIgnoreCase("Send")) {
                    System.out.printf("%-4s    %-20s    %10s\n", transfer.getTransferId(), "From: " + transfer.getAccountFrom(), "$ " + transfer.getAmount());
                }else {
                    System.out.printf("%-4s    %-20s    %10s\n", transfer.getTransferId(), "To: " + transfer.getAccountTo(), "$ " + transfer.getAmount());

                }
            }
//        }
        System.out.println("-------------------------------------------");
        int transferId = Integer.parseInt(consoleService.promptForString("Enter the transfer ID to view details (0 to cancel): "));
        if(transferId != 0){
            Transfer transfer = transferService.viewTransferById(transferId);
            System.out.println("-------------------------------------------");
            System.out.println("Transfer Details ");
            System.out.println("-------------------------------------------");
            System.out.println("Id: " + transfer.getTransferId());
            System.out.println("From: " + transfer.getAccountFrom());
            System.out.println("To: " + transfer.getAccountTo());
            System.out.println("Type: " + transfer.getTransferType());
            System.out.println("Status: " + transfer.getStatus());
            System.out.println("Amount: " + transfer.getAmount());
        }
	}

	private void viewPendingRequests() {
        Transfer[] pendingTransfers = transferService.listPendingTransfers(currentUser.getUser().getId());
        consoleService.printPendingTransfers(pendingTransfers);

		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
        consoleService.ListUsers(transferService.listUsers());
        int userId = consoleService.promptForInt("Enter ID of user you are send to (0 to cancel): ");
        BigDecimal sendingAmount = null;
        if(userId !=0){
            sendingAmount = consoleService.promptForBigDecimal("Enter amount: ");
        }
        Transfer transfer = new Transfer("Send", "Pending", currentUser.getUser().getId(), userId, sendingAmount);
        transferService.sendMoney(transfer);
		// TODO Auto-generated method stub
		
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
