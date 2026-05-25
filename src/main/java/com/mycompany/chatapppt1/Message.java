/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapppt1;

import java.util.Random;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Message.java
 * Stores and handles all message data and operations for ChatApp.
 * @author Buhle
 */
public class Message {

    //FIELDS
    private String messageID;       // Random 10-digit auto-generated ID
    private int messageNumber;      // Message number from the loop counter
    private String recipient;       // Validated SA cell number (+27...)
    private String messageText;     // The message content (max 250 chars)
    private String messageHash;     // Auto-generated hash for the message
    private String sendStatus;      // Tracks if message was Sent, Stored, or Disregarded


    //CONSTRUCTOR
    // Initialises all fields when a new Message object is created
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();        // Auto-generate ID on creation
        this.messageHash = createMessageHash();      // Auto-generate hash on creation
        this.sendStatus = "";                        // Empty until user chooses Send/Store/Disregard
    }



    //MESSAGE ID

    /**
     * Randomly generates a 10-digit message ID using string manipulation.
     * @return 10-digit ID as a String
     */
    private String generateMessageID() {
        Random random = new Random();
        long id = (long) (random.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(id);
    }

    /**
     * Validates that the message ID is exactly 10 digits long.
     * @return true if valid, false if not
     */
    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10;
    }


    //RECIPIENT VALIDATION

    /**
     * Validates the recipient cell number.
     * Must contain an international code and be max 10 characters after the code.
     * @param number the cell number to validate
     * @return success or failure message
     */
    public String checkRecipientCell(String number) {
        // Check it starts with + (international code) and the part after is max 10 digits
        if (number.startsWith("+") && number.substring(1).length() <= 10) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain " +
                   "an international code. Please correct the number and try again.";
        }
    }


    //MESSAGE TEXT VALIDATION

    /**
     * Validates that the message does not exceed 250 characters.
     * @param text the message text to validate
     * @return success or failure message showing how many chars over the limit
     */
    public String validateMessageText(String text) {
        if (text.length() <= 250) {
            return "Message ready to send.";
        } else {
            int over = text.length() - 250;
            return "Message exceeds 250 characters by " + over + "; please reduce the size.";
        }
    }


    //MESSAGE HASH

    /**
     * Creates the message hash in the format:
     * First2DigitsOfID:MessageNumber:FirstWordLastWord
     * Example: 00:0:HITONIGHT
     * @return the generated hash as a String
     */
    public String createMessageHash() {
        // Step 1: Get first 2 digits of the message ID
        String first2 = messageID.substring(0, 2);

        // Step 2: Split the message text into words
        String[] words = messageText.trim().split(" ");

        // Step 3: Get the first and last word, convert to uppercase
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();

        // Step 4: Combine everything into the hash format
        return first2 + ":" + messageNumber + ":" + firstWord + lastWord;
    }


    // SEND,STORE, DISREGARD

  
    public String sentMessage(int choice) {
    switch (choice) {
        case 1:
            sendStatus = "Sent";
            return "Message successfully sent.";
        case 2:
            sendStatus = "Disregarded";          // 2 = Disregarded
            return "Press 0 to delete the message.";
        case 3:
            sendStatus = "Stored";               // 3 = Stored
            storeMessage();
            return "Message successfully stored.";
        default:
            return "Invalid option. Please choose 1, 2, or 3.";
    }
}


    //DISPLAY MESSAGES

    
    public void printMessages() {
        System.out.println("MESSAGE DETAILS");
        System.out.println("Message ID     : " + messageID);
        System.out.println("Message Hash   : " + messageHash);
        System.out.println("Recipient      : " + recipient);
        System.out.println("Message        : " + messageText);
        System.out.println("Status         : " + sendStatus);
    }


    //TOTAL MESSAGES

    public int returnTotalMessages() {
        return messageNumber;
    }


    // GETTERS
    // Allowsother classes to safely read the private fields

    public String getMessageID()     { return messageID; }
    public int getMessageNumber()    { return messageNumber; }
    public String getRecipient()     { return recipient; }
    public String getMessageText()   { return messageText; }
    public String getMessageHash()   { return messageHash; }
    public String getSendStatus()    { return sendStatus; }


    //SETTERS 
    // Allow other classes to safely update fields after validation

    public void setRecipient(String recipient) {
        if (checkRecipientCell(recipient).contains("successfully")) {
            this.recipient = recipient;
        } else {
            System.out.println(checkRecipientCell(recipient));
        }
    }

    public void setMessageText(String messageText) {
        if (messageText.length() <= 250) {
            this.messageText = messageText;//
            this.messageHash = createMessageHash(); 
        } else {
            System.out.println(validateMessageText(messageText));
        }
        
    }
 
public void storeMessage() {
    JSONObject obj = new JSONObject();
    obj.put("messageID", this.messageID);
    obj.put("messageNumber", this.messageNumber);
    obj.put("recipient", this.recipient);
    obj.put("message", this.messageText);
    obj.put("messageHash", this.messageHash);

    try (FileWriter fw = new FileWriter("messages.json", true)) {
        fw.write(obj.toString() + "\n");
        System.out.println("Message saved to messages.json");
    } catch (IOException e) {
        System.out.println("Error saving message: " + e.getMessage());
    }
}
    
}