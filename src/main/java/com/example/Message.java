package com.example;
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
    public Message( String messageText, String recipientCell) {
        this.messageID = generateMessageID();
        this.messageText = messageText;
        this.recipientCell = recipientCell;
    }

    private static String generateMessageID() {
        String id;
        do {
            long number = (long) (Math.random() * 1_000_000_0000L);
            id = String.format("%010d", number);
        } while (usedMessageIDs.contains(id));
        usedMessageIDs.add(id);
        return id;
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10 && usedMessageIDs.contains(messageID);
    }

    public String getMessageID() {
        return messageID;
    }

    public int checkRecipientCell() {
        if (recipientCell != null && recipientCell.matches("\\+27\\d{9}")) {
            return 1; // Valid
        }
            return 0; // Invalid
    }

    public String createMessageHash(int messageNumber) {
        String idPart = messageID.length() >= 2 ? messageID.substring(0, 2) : messageID;

        String numberPart = String.valueOf(messageNumber);

        String textPart;
        if (messageText.length() >= 3) {
            textPart = messageText.substring(0, 2) + messageText.substring(messageText.length() - 2);
        } else {
            textPart = messageText;
        }

        return (idPart + numberPart + textPart).toUpperCase();
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



