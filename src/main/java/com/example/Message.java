package com.example;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Message {
    private static final HashSet<String> usedMessageIDs = new HashSet<>();
    private static final List<String> sentMessage = new ArrayList<>();
    private static int totalMessagesSent = 0;
    private final String messageID;
    private final String messageText; 
    private final String recipientCell;
    private String statusMessage;

    // Constructor
    public Message(String messageID, String messageText, String recipientCell) {
        this.messageID = messageID;
        this.messageText = messageText;
        this.recipientCell = recipientCell;
    }

    public boolean checkMessageID() {
        if (messageID == null || usedMessageIDs.contains(messageID)) {
            return false;
        }
        usedMessageIDs.add(messageID);
        return true;
    }

    public int checkRecipientCell() {
        if (recipientCell != null && recipientCell.matches("\\+27\\d{9}")) {
            return 1; // Valid
        }
            return 0; // Invalid
    }

    public String createMessageHash() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(messageText.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
        
        return sb.toString(); 
    } catch (NoSuchAlgorithmException e) {
            return "Error generating hash";
        }
    }

    public String SentMessage(Scanner scanner) {
        System.out.println("Choose an option:");
        System.out.println("1. Send");
        System.out.println("2. Store");
        System.out.println("3. Delete");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

            switch (choice) {
             case 1:
             sentMessage.add("Sent: " + messageText);
                totalMessagesSent++;
                statusMessage = "Message sent successfully!";
                break;
            case 2:
                sentMessage.add("Stored: " + messageText);
                statusMessage = "Message stored successfully!";
                break;
                
            case 3:
             statusMessage = "Message deleted!";
                break;
            default:
                statusMessage = "Invalid option!";
                break;
            }
        return statusMessage;
    }

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

            public static int getTotalMessagesSent() {
                return totalMessagesSent;
            }

            public String storeMessages() {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                return gson.toJson(sentMessage);
            }
            }



