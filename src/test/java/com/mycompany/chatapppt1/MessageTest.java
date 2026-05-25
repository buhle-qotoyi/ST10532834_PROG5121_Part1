/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapppt1; 
/**
 *
 * @author Buhle
 */
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    // Message length valid (under 250 chars)
    @Test
    public void testMessageLengthValid() {
        Message msg = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", msg.validateMessageText("Hi Mike, can you join us for dinner tonight?"));
    }

    // Message length invalid (over 250 chars)
    @Test
    public void testMessageLengthInvalid() {
        Message msg = new Message(1, "+27718693002", "Hi");
        String longText = "A".repeat(260);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", msg.validateMessageText(longText));
    }

    //Recipient number valid
    @Test
    public void testRecipientValid() {
        Message msg = new Message(1, "+27718693002", "Hi");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell("+27718693002"));
    }

    // Recipient number invalid (no international code)
    @Test
    public void testRecipientInvalid() {
        Message msg = new Message(1, "+27718693002", "Hi");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                msg.checkRecipientCell("08575975889"));
    }

    //Message hash correct using POE test data
    @Test
    public void testMessageHash() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        // Hash format: first2OfID:messageNumber:FirstWordLastWord
        // We only check the end part since ID is random
        assertTrue(msg.createMessageHash().endsWith(":HITONIGHT"));
    }

    //Message ID is exactly 10 digits
    @Test
    public void testMessageID() {
        Message msg = new Message(1, "+27718693002", "Hi");
        assertTrue(msg.checkMessageID());
    }

    //sentMessage - Send chosen
    @Test
    public void testSentMessageSend() {
        Message msg = new Message(1, "+27718693002", "Hi");
        assertEquals("Message successfully sent.", msg.sentMessage(1));
    }

    //sentMessage - Disregard chosen
    @Test
    public void testSentMessageDisregard() {
        Message msg = new Message(1, "+27718693002", "Hi");
       assertEquals("Press 0 to delete the message.", msg.sentMessage(3));

    }

    //sentMessage - Store chosen
    @Test
    public void testSentMessageStore() {
        Message msg = new Message(1, "+27718693002", "Hi");
        assertEquals("Message successfully stored.", msg.sentMessage(2));
    }
}
