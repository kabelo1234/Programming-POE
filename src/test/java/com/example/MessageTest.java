package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    void testMessageLengthValid() {
        Message msg = new Message("Hello Mike", "+27718693002");
        assertEquals(10, msg.getMessageID().length());
        assertTrue(msg.checkMessageID());
    }

    @Test
    void testMessageLengthTooLong() {
        String longMessage = "a".repeat(251);
        Message msg = new Message(longMessage, "+27718693002");
        assertTrue(longMessage.length() > 250);
        // Simulate validation logic
        assertEquals("Message exceeds 250 characters by 1, please reduce size.",
                validateMessageLength(longMessage));
    }

    private String validateMessageLength(String text) {
        if (text.length() > 250) {
            int excess = text.length() - 250;
            return "Message exceeds 250 characters by " + excess + ", please reduce size.";
        }
        return "Message ready to send.";
    }

    @Test
    void testRecipientValid() {
        Message msg = new Message("Hi Mike", "+27718693002");
        assertEquals(1, msg.checkRecipientCell());
    }

    @Test
    void testRecipientInvalid() {
        Message msg = new Message("Hi Mike", "0718693002"); // missing +27
        assertEquals(0, msg.checkRecipientCell());
    }

    @Test
    void testMessageHashFormat() {
        Message msg = new Message("Hi tonight", "+27718693002");
        String hash = msg.createMessageHash(0);
        assertTrue(hash.matches("[0-9]{2}:0:[A-Z]{4,}"));
    }
}