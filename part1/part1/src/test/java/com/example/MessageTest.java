package com.example;
import org.junit.jupiter.api.Test;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;

/**
 * Test class for the Message class.
 * Tests message length, recipient validation, message ID generation, message hash format,
 * sending, storing, and disregarding messages, and total messages sent tracking.
 */
public class MessageTest {
    /*
        * Test to ensure message length validation works correctly.
     */
    @Test
    void testMessageLengthSuccess() {
        String text = "Hi Mike, can you join us for dinner tonight?";
        Message msg = new Message(text, "+27718693002");

        // Check that the message length is within the limit
        assertEquals(true, text.length() <= 250);
        // Validate the message length
        assertEquals("Message ready to send.", validateMessageLength(text));
    }

    // Test to ensure message length validation fails for messages exceeding 250 characters.
    @Test
    void testMessageLengthFailure() {
        String longMessage = "a".repeat(251);
        Message msg = new Message(longMessage, "+27718693002");

        // Check that the message length exceeds the limit
        assertEquals(true, longMessage.length() > 250);
        // Validate the message length
        assertEquals("Message exceeds 250 characters by 1 character, please reduce size.",
                validateMessageLength(longMessage));
    }

    // Helper method to validate message length
    private String validateMessageLength(String text) {
        if (text.length() > 250) {
            int excess = text.length() -250;
            return "Message exceeds 250 characters by " + excess + " character, please reduce size.";

        }
        return "Message ready to send.";
    }

    // Test to ensure recipient cell number validation works correctly for valid numbers.
    @Test
    void testRecipientValid() {
        Message msg = new Message("Hi Mike, ", "+27718693002");
        // Check that the recipient cell number is valid
        assertEquals(1, msg.checkRecipientCell());
        // Validate the recipient cell number
        assertEquals("Cell phone number successfully captured.",
            msg.checkRecipientCell() == 1 ?
             "Cell phone number successfully captured." :
                "Cell phone number is incorrectly formatted or does not an international code. Please correct the number");
    }

    // Test to ensure recipient cell number validation fails for invalid numbers.
    @Test
    void testRecipientInvalid() {
    Message msg = new Message( "Hi Mike,", "0718693002");
        // Check that the recipient cell number is invalid
        assertEquals(0, msg.checkRecipientCell());
        // Validate the recipient cell number
        assertEquals("Cell phone number is incorrectly formatted or does not an international code. Please correct the number and try again.",
            msg.checkRecipientCell() == 1 ?
             "Cell phone number successfully captured." :
                "Cell phone number is incorrectly formatted or does not an international code. Please correct the number and try again.");
    }

    // Test to ensure a unique 10-digit message ID is generated correctly.
    @Test
    void testMessageIDGenerated() {
        Message msg = new Message("Hello", "+2718693002");
        // Check that the message ID is not null and has a length of 10
        assertEquals(false, msg.getMessageID() == null);
        // Validate the message ID format and uniqueness
        assertEquals(10, msg.getMessageID().length());
        // Check that the message ID is valid and has been used
        assertEquals(true, msg.checkMessageID());
        System.out.println("Message ID generated : " + msg.getMessageID());
    }

    // Test to ensure the message hash is created in the correct format.
    @Test
    void testMessageHashCorrectFormat() {
        Message msg = new Message("Hi tonight", "+2718693002");
        String hash = msg.createMessageHash(0);

        // Validate the message hash format using regex
        assertEquals(true, hash.matches("[0-9]{2}:0:[A-Z]{4,}"));
        System.out.println("Message Hash: " + hash);
    }

    // Test to ensure sending a message works correctly and returns the expected status message.
    @Test
    void testSendMessageOption1() {
        Message msg = new Message("Hi Mike, can you join us for dinner tonight?", "+27718693002");
        Scanner scanner = new Scanner("1\n");
        String result = msg.SentMessage(scanner);
        assertEquals("Message successfully sent", result);
    }

    // Test to ensure storing a message works correctly and returns the expected status message.
    @Test
    void testStoreMessageOption2() {
        Message msg = new Message("Hi Keegan, did you receive the payments?", "+27550975889");
        Scanner scanner = new Scanner("2\n");
        String result = msg.SentMessage(scanner);
        assertEquals("Message successfully stored", result);
    }

    // Test to ensure disregarding a message works correctly and returns the expected status message.
    @Test
    void testDisregardMessageOption3() {
        Message msg = new Message("Hi Keegan, did you receive the payments?", "+27550975889");
        Scanner scanner = new Scanner("3\n");
        String result = msg.SentMessage(scanner);
        assertEquals("Message deleted!", result);
    }

    // Test to ensure total messages sent counter increments correctly after sending a message.
    @Test
    void testTotalMessagesIncrements() {
        int before = Message.getTotalMessagesSent();
        Message msg = new Message("Test", "+27718693002");
        Scanner scanner = new Scanner("1\n");
        msg.SentMessage(scanner);
        int after = Message.getTotalMessagesSent();
        assertEquals(before + 1, after);
    }
}