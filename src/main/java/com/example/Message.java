package com.example;

import java.io.*;
import java.util.Scanner;

/**
 * Message class to handle message creation, validation, and storage.
 */
public class Message {
    private static int idCounter = 0;

    // Parallel arrays
    public static String[] sentMessages = new String[100];
    public static String[] disregardedMessages = new String[100];
    public static String[] storedMessages = new String[100];
    public static String[] messageHashes = new String[100];
    public static String[] messageIDs = new String[100];
    public static String[] recipients = new String[100];

    // for tracking the number of messages
    private static int totalMessagesSent = 0;
    private static int totalDisregarded = 0;
    private static int totalStored = 0;

    // variables for messages
    private final String messageID;
    private final String messageText;
    private final String recipientCell;
    private String statusMessage;

    // constructors for creating a new message
    public Message(String messageText, String recipientCell, int loopIndex) {
        this.messageID = generateMessageID(loopIndex);
        this.messageText = messageText;
        this.recipientCell = recipientCell;

        //Prints the generated message ID
    System.out.println("Message ID generated : " + messageID);
    }

    // generates a 10 digit message ID using loop counter and loop index
    private static String generateMessageID(int loopIndex) {
        idCounter++;
        String base = String.valueOf(loopIndex) + String.valueOf(idCounter);
        return String.format("%010d", Long.parseLong(base));
    }

    // validates cell phone number
    public int checkRecipientCell() {
        return (recipientCell != null && recipientCell.matches("\\+27\\d{9}")) ? 1 : 0;
    }

    
    public String createMessageHash() {
        String text = messageText.trim();
        int firstSpace = text.indexOf(" ");
        int lastSpace = text.lastIndexOf(" ");
        String firstWord = (firstSpace == -1) ? text : text.substring(0, firstSpace);
        String lastWord = (lastSpace == -1) ? text : text.substring(lastSpace + 1);
        String hash = messageID.substring(0, 2) + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    // Send, store, or disregard
    public String SentMessage(Scanner scanner) {
        String choiceStr = javax.swing.JOptionPane.showInputDialog(
                null,
                "Choose an option:\n1. Send\n2. Store\n3. Disregard",
                "Message Options",
                javax.swing.JOptionPane.QUESTION_MESSAGE
        );

        int choice;
        try {
            choice = Integer.parseInt(choiceStr);
        } catch (Exception e) {
            statusMessage = "Invalid option!";
            return statusMessage;
        }

          // Process user choice
            if (choice == 1) {
             sentMessage.add("Sent: " + messageText);
                totalMessagesSent++;
                statusMessage = "Message successfully sent";
            } else if (choice == 2) {
                sentMessage.add("Stored: " + messageText);
                statusMessage = "Message successfully stored";
            } else if (choice == 3) {
             statusMessage = "Message deleted!";
            } else {
                statusMessage = "Invalid option!";
            }
        return statusMessage;
    }

            // Prints all sent messages
     public String printMessages() {
                if (sentMessage.isEmpty()) {
                    return "No messages to display.";
                }
                StringBuilder sb = new StringBuilder("Messages:\n");
                for (String msg : sentMessage) {
                    sb.append(msg).append("\n");
                }
                return sb.toString();
            }

            // Getter for total messages sent
            public static int getTotalMessagesSent() {
                return totalMessagesSent;
            }

    // Stores messages in JSON format
            public String storeMessages() {
        if (sentMessage.isEmpty()) {
            return "[]";
        }

        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < sentMessage.size(); i++) {
            String msg = sentMessage.get(i)
                    .replace("\\", "\\\\");

            json.append(" {\"message\": \"").append(msg).append("\" }");
            if (i < sentMessage.size() - 1) {
                json.append(",");
            }
            json.append("\n");
            }
            json.append("]");

        return json.toString();
        }
    }




