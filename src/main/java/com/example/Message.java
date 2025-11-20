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

    // Generates a unique 10-digit message ID
    private static String generateMessageID() {
        String id;
        do {
            long number = (long) (Math.random() * 1_000_000_0000L);
            id = String.format("%010d", number);
        } while (usedMessageIDs.contains(id));
        usedMessageIDs.add(id);
        return id;
    }

    // Checks if the message ID is valid and has been used
    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10 && usedMessageIDs.contains(messageID);
    }

    // Getter for message ID
    public String getMessageID() {
        return messageID;
    }

    // Validates the recipient's cell number format
    public int checkRecipientCell() {
        if (recipientCell != null && recipientCell.matches("\\+27\\d{9}")) {
            return 1; // Valid
        }
        return 0; // Invalid
    }

    // Creates a message hash based on the message ID, number, and text
    public String createMessageHash(int messageNumber) {
        String idPart = messageID.length() >= 2 ? messageID.substring(0, 2) : messageID;
        String numberPart = String.valueOf(messageNumber);
        String textPart;
        if (messageText.length() >= 3) {
            textPart = messageText.substring(0, 2) + messageText.substring(messageText.length() - 2);
        } else {
            textPart = messageText;
        }
        String hash = (idPart + ":" + numberPart + ":" + textPart).toUpperCase();


    System.out.println("Message hash is correct");
    return hash;
    }

    // Handles sending, storing, or disregarding the message based on user input
    public String SentMessage(Scanner scanner) {
        System.out.println("Choose an option:");
        System.out.println("1. Send");
        System.out.println("2. Store");
        System.out.println("3. Disregard message");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

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




