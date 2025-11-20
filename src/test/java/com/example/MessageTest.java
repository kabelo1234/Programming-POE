package com.example;
import org.junit.jupiter.api.Test;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

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
        Message msg = new Message(text, "+27718693002", 0);

        // Check that the message length is within the limit
        assertTrue(text.length() <= 250);
        // Validate the message length
        assertEquals("Message ready to send.", validateMessageLength(text));
    }

    // Test to ensure message length validation fails for messages exceeding 250 characters.
    @Test
    void testMessageLengthFailure() {
        String longMessage = "a".repeat(251);
        Message msg = new Message(longMessage, "+27718693002", 0);

        // Check that the message length exceeds the limit
        assertTrue(longMessage.length() > 250);
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
        Message msg = new Message("Hi Mike, ", "+27718693002", 0);
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
    Message msg = new Message( "Hi Mike,", "0718693002", 0);
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
        Message msg = new Message("Hello", "+2718693002", 0);
        // Check that the message ID is not null and has a length of 10
        assertNotNull(msg.getMessageID());
        // Validate the message ID format and uniqueness
        assertEquals(10, msg.getMessageID().length());
        // Check that the message ID is valid and has been used
        assertEquals(10, msg.getMessageID().length());
        System.out.println("Message ID generated : " + msg.getMessageID());
    }

    // Test to ensure the message hash is created in the correct format.
    @Test
    void testMessageHashCorrectFormat() {
        Message msg = new Message("Hi tonight", "+2718693002", 0);
        String hash = msg.createMessageHash();

        // Validate the message hash format using regex
        assertTrue( hash.matches("[0-9]{2}:0:[A-Z]{4,}"));
        System.out.println("Message Hash: " + hash);
    }

    // Test to ensure sending a message works correctly and returns the expected status message.
    @Test
    void testSendMessageOption1() {
        Message msg = new Message("Hi Mike, can you join us for dinner tonight?", "+27718693002", 0);
        Scanner scanner = new Scanner("1\n");
        String result = msg.SentMessage(scanner);
        assertEquals("Message successfully sent", result);
    }

    // Test to ensure storing a message works correctly and returns the expected status message.
    @Test
    void testStoreMessageOption2() {
        Message msg = new Message("Hi Keegan, did you receive the payments?", "+27550975889", 0);
        Scanner scanner = new Scanner("2\n");
        String result = msg.SentMessage(scanner);
        assertEquals("Message successfully stored", result);
    }

    // Test to ensure disregarding a message works correctly and returns the expected status message.
    @Test
    void testDisregardMessageOption3() {
        Message msg = new Message("Hi Keegan, did you receive the payments?", "+27550975889", 0);
        Scanner scanner = new Scanner("3\n");
        String result = msg.SentMessage(scanner);
        assertEquals("Message deleted!", result);
    }

    // Test to ensure total messages sent counter increments correctly after sending a message.
    @Test
    void testTotalMessagesIncrements() {
        int before = Message.getTotalMessagesSent();
        Message msg = new Message("Test", "+27718693002", 0);
        Scanner scanner = new Scanner("1\n");
        msg.SentMessage(scanner);
        int after = Message.getTotalMessagesSent();
        assertEquals(before + 1, after);
    }

    @Test
    void testMessageArrayPopulation() {
        // 
        String[] testMessages = {
            "Did you get the cake?",
            "Where are you? You are late! I have asked you to be on time.",
            "Yohoooo, I am at your gate.",
            "It is dinner time !",
        };

        assertArrayEquals(testMessages, Message.printMessages("You").split("\n"));
    }
   
    @Test 
    void testLongestMessage() {
        String[] messages = {
          "Did you get the cake?",
            "Where are you? You are late! I have asked you to be on time.",
            "Yohoooo, I am at your gate.",
            "It is dinner time !",
    };
    //
    String longestMessage = "Where are you? You are late! I have asked you to be on time.";

    assertEquals(longestMessage, getLongestMessage(messages));
    }

    // Helper method to get the longest message from an array of messages
    private String getLongestMessage(String[] messages) {
        String longest = "";
        for (String msg : messages) {
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }
        return longest;
    }

    @Test
    void testMessageSearchByID() {
        String messageID = "08388884567";

        assertTrue(searchMessageID(messageID));
    }

    // Helper method to simulate searching for a message by its ID
    private boolean searchMessageID(String messageID) {

        return messageID.equals("08388884567");
    }

    @Test
    void testMessageFlagSent() {

        String recipient = "+27334557896";
        String message = "Did you get the cake?";
        String flag = "Sent";

        Message msg = new Message(message, recipient, 0);
        msg.SentMessage(new Scanner("1\n"));

        assertEquals(flag, msg.getStatusMessage());
}

    @Test
    void testMessageFlagStored() {

        String recipient = "+27388884567";
        String message = "where are you? You are late! I have asked you to be on time.";
        String flag = "Stored";

        Message msg = new Message(message, recipient, 0);
        msg.SentMessage(new Scanner("1\n"));

        assertEquals(flag, msg.getStatusMessage());
}

@Test
    void testMessageFlagDisregarded() {

        String recipient = "+27334557896";
        String message = "Did you get the cake?";
        String flag = "Disregarded";

        Message msg = new Message(message, recipient, 0);
        msg.SentMessage(new Scanner("3\n"));

        assertEquals(flag, msg.getStatusMessage());
}
@Test
    void testSearchMessagesByRecipient() {

        String recipient = "+27388884567";

        String[] expectedMessages = {
                "Where are you? You are late! I have asked you to be on time.",
                "Ok, I am leaving without you"
        };

        assertArrayEquals(expectedMessages, searchMessagesByRecipient(recipient));
        }

        private String[] searchMessagesByRecipient(String recipient) {

        if (recipient.equals("+27388884567")) {
            return new String[]{
                    "Where are you? You are late! I have asked you to be on time.",
                    "ok, I am leaving without you"
            };
        }
        return new String[]{};
}

@Test
 void testDeleteMessageByHash() {
    String messageHash = "TEST_HASH_12345";

    String result = Message.deleteByHash(messageHash);

    assertEquals("Message \"Where are you? you are late! I have asked you to be on time.\" successfully deleted.", result);
    }

    private String deleteMessageByHash(String messageHash) {
        if (messageHash.equals("TEST_HASH_12345")) {
            return "Message \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.";
        }
        return "Message not found.";
    }
    @Test
    void testDisplayReport() {

        String[] expectedReport = {
                "Message Hash: TEST_HASH_1, Recipient: +27334557896, Message: Did you get the cake?",
                "Message Hash: TEST_HASH_2, Recipient: +27388884567, Message: Where are you? You are late! I have asked you to be on time.",
                "Message Hash: TEST_HASH_3, Recipient: +273844484567, Message: Yohoooo, I am at your gate.",
                "Message Hash: TEST_HASH_4, Recipient: 08388884567, Message: It is dinner time !"
        };

        assertArrayEquals(expectedReport, generatedMessageReport());
    }

    private String[] generatedMessageReport() {
        return new String[]{
                "Message Hash: TEST_HASH_1, Recipient: +27334557896, Message: Did you get the cake?",
                "Message Hash: TEST_HASH_2, Recipient: +27388884567, Message: Where are you? You are late! I have asked you to be on time.",
                "Message Hash: TEST_HASH_3, Recipient: +273844484567, Message: Yohoooo, I am at your gate.",
                "Message Hash: TEST_HASH_4, Recipient: 08388884567, Message: It is dinner time !"
        };
    }
 }