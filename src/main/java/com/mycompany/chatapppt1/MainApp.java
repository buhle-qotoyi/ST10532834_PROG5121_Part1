/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// An update for part 2
/**
 *
 * @author Buhle
 */
package com.mycompany.chatapppt1;


import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        
        //allows for the users input for their information
        Scanner input = new Scanner(System.in);
        
        //creating an object for the login class so we cn call its methods
        Login login = new Login();
   
       
// REGISTRATION SECTION
    System.out.println("===USER REGISTRATION===");
    
    System.out.print("Enter a username: ");
    String username = input.nextLine();
    
    System.out.print("Enter a password: ");
    String password = input.nextLine();
    
    System.out.print("Enter your Suth African phone number (+27...): ");
    String phone = input.nextLine();
    
    //Call the registerUser method and store the message it returns
    String response = login.registerUser(username, password, phone);
    
    //Show th registration message
    System.out.println(response);
    
    //LOGIN SECTION
    System.out.println("=== USER LOGIN ===");
    
    System.out.print("Enter a username: ");
     String loginUsername = input.nextLine();
     
     System.out.print("Enter your password: ");
     String loginPassword = input.nextLine();
     
     // Call loginUser to check if details match the stored ones
     boolean loggedIn = login.loginUser(loginUsername, loginPassword);
     
     // Print out the correct login message
     String loginMessage = login.returnLoginStatus(loggedIn);
     System.out.println(loginMessage);
     
     
     // Updated section for Messages
        if (loggedIn) {
    System.out.println("Welcome to ChatApp.");

    boolean running = true;

    while (running) {
        System.out.println("\n1) Send Messages");
        System.out.println("2) Show recently sent messages");
        System.out.println("3) Quit");
        System.out.print("Enter your choice: ");
        int choice = input.nextInt();

        switch (choice) {
            case 1:
                System.out.print("How many messages would you like to send? ");
                int numMessages = input.nextInt();
                input.nextLine(); // clear buffer

                for (int i = 0; i < numMessages; i++) {
                    int messageNumber = i + 1;
                    System.out.println("--- Message " + messageNumber + " ---");

                    // validate recipient
                    String recipient = "";
                    while (true) {
                        System.out.print("Enter recipient number (+27...): ");
                        recipient = input.nextLine();
                        Message tempMsg = new Message(messageNumber, recipient, "temp");
                        String recipientCheck = tempMsg.checkRecipientCell(recipient);
                        System.out.println(recipientCheck);
                        if (recipientCheck.contains("successfully")) break;
                    }

                    // validate message text
                    String messageText = "";
                    while (true) {
                        System.out.print("Enter your message (max 250 chars): ");
                        messageText = input.nextLine();
                        Message tempMsg = new Message(messageNumber, recipient, messageText);
                        String textCheck = tempMsg.validateMessageText(messageText);
                        System.out.println(textCheck);
                        if (textCheck.equals("Message ready to send.")) break;
                    }

                    // the message object
                    Message msg = new Message(messageNumber, recipient, messageText);

                    // Send,Store, Disregard option
                    System.out.println("\n1) Send Message");
                    System.out.println("2) Disregard Message");
                    System.out.println("3) Store Message to send later");
                    System.out.print("Choose an option: ");
                    int sendChoice = input.nextInt();
                    input.nextLine();

                    String sendResult = msg.sentMessage(sendChoice);
                    System.out.println(sendResult);

                    //message details in correct order
                    msg.printMessages();
                }
                break;

            case 2:
                System.out.println("Coming Soon.");
                break;

            case 3:
                running = false;
                System.out.println("Goodbye!");
                break;

            default:
                System.out.println("Invalid option. Please choose 1, 2, or 3.");
        }
    }
} else {
    System.out.println("Access denied. Please check your username and password.");
}
    
}
}
