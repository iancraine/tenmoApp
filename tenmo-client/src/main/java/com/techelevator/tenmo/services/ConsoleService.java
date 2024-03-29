package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.Scanner;

public class ConsoleService {
    private final TransferService transferService = new TransferService();

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void ListUsers(User[] users, int currentUserId) {
        System.out.println("-------------------------------------------");
        System.out.println("Users");
        System.out.printf("%-4s    %-10s\n", "ID", "Name");
        System.out.println("-------------------------------------------");
        for (User user : users) {
            if(user.getId() == currentUserId){
                continue;
            }
            System.out.printf("%-4s    %-10s\n", user.getId(), user.getUsername());
        }
        System.out.println("----------");
    }

    public void sendRequestStuff(Transfer transfer, String requestOrSend) {

    }

    public int printPendingTransfers(Transfer[] pendingTransfers) {
        System.out.println("-------------------------------------------");
        System.out.println("Pending Transfer");
        System.out.printf("%-4s    %-20s    %-10s\n", "ID", "To/From", "Amount");
        System.out.println("-------------------------------------------");
        for (Transfer transfer : pendingTransfers) {
            if (transfer.getTransferType().equalsIgnoreCase("Request")) {
                System.out.printf("%-4s    %-20s    %10s\n", transfer.getTransferId(), "Request From: " + transfer.getToUser(), "$ " + transfer.getAmount());
            } else {
                System.out.printf("%-4s    %-20s    %10s\n", transfer.getTransferId(), "Sent By: " + transfer.getFromUser(), "$ " + transfer.getAmount());
            }
        }
        System.out.println("Enter transfer ID to approve/reject (0 to cancel): ");
        int transferId = Integer.parseInt(scanner.nextLine());
        return transferId;
    }

    public int approvedOrRejectedMethod(){
        System.out.println("1: Approve");
        System.out.println("2: Reject");
        System.out.println("0: Don't approve or reject");
        System.out.println("---------");
        int approvedOrRejected = promptForInt("Please choose and option: ");
        return approvedOrRejected;
    }

    public int printAllTransfers(Transfer[] transfers) {
        System.out.println("-------------------------------------------");
        System.out.println("Transfer");
        System.out.printf("%-4s    %-20s    %-10s\n", "ID", "From/To", "Amount");
        System.out.println("-------------------------------------------");
        for (Transfer transfer : transfers) {
            if (transfer.getTransferType().equals("Send")) {
                System.out.printf("%-4s    %-20s    %10s\n", transfer.getTransferId(), "Sent From: " + transfer.getFromUser(), "$ " + transfer.getAmount());
            } else {
                System.out.printf("%-4s    %-20s    %10s\n", transfer.getTransferId(), "Requested By: " + transfer.getToUser(), "$ " + transfer.getAmount());
            }
        }
        System.out.println("-------------------------------------------");
        int transferId = Integer.parseInt(promptForString("Enter the transfer ID to view details (0 to cancel): "));
        return transferId;
    }

    public void printTransferById(Transfer transfer) {
            System.out.println("-------------------------------------------");
            System.out.println("Transfer Details ");
            System.out.println("-------------------------------------------");
            System.out.println("Id: " + transfer.getTransferId());
            System.out.println("From: " + transfer.getFromUser());
            System.out.println("To: " + transfer.getToUser());
            System.out.println("Type: " + transfer.getTransferType());
            System.out.println("Status: " + transfer.getStatus());
            System.out.println("Amount: " + transfer.getAmount());
    }

        public void printErrorMessage() {
            System.out.println("An error occurred. Check the log for details.");
        }

    }
