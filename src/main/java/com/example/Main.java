package com.example;

import javax.swing.JOptionPane;
import java.util.Scanner;

import static java.lang.System.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(in);

        // Registration section
        String name = JOptionPane.showInputDialog("Enter your name:");
        String surname = JOptionPane.showInputDialog("Enter your surname:");
        String username = JOptionPane.showInputDialog("Enter your username:");
        String password = JOptionPane.showInputDialog("Enter your password:");
        String phoneNumber = JOptionPane.showInputDialog("Enter your phone number:");

        login user = new login(name, surname, username, password, phoneNumber);
        String registrationMessage = user.registerUser(name, password, phoneNumber, username, surname);
        JOptionPane.showMessageDialog(null, registrationMessage);

        if (!registrationMessage.equals("Registration successful!")) return;

        // Login section
        String loginUsername = JOptionPane.showInputDialog("Enter your username to login:");
        String loginPassword = JOptionPane.showInputDialog("Enter your password to login:");
        boolean loginStatus = user.loginUser(loginUsername, loginPassword, phoneNumber);
        JOptionPane.showMessageDialog(null, user.returnLoginStatus(loginStatus));
        if (!loginStatus) {
            JOptionPane.showMessageDialog(null, "Incorrect login details. Exiting program.");
            return;
        }

        // Message limit
        int messageLimit;
        try {
            messageLimit = Integer.parseInt(JOptionPane.showInputDialog("How many messages would you like to send?"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid number. Defaulting to 1 message.");
            messageLimit = 1;
        }

        int messagesSent = 0;

        while (true) {
            String menu = JOptionPane.showInputDialog(
                    null,
                    "Choose an option:\n" +
                            "1) Send message\n" +
                            "2) Show sent messages (Coming soon)\n" +
                            "3) Display sender & recipient of all sent messages\n" +
                            "4) Display longest sent message\n" +
                            "5) Search for a message ID\n" +
                            "6) Search messages by recipient\n" +
                            "7) Delete a message by hash\n" +
                            "8) Display full message report\n" +
                            "9) Exit",
                    "QuickChat Menu",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (menu == null) break;

            int choice;
            try {
                choice = Integer.parseInt(menu);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number from 1-9.");
                continue;
            }

            if (choice == 1) {
                if (messagesSent >= messageLimit) {
                    JOptionPane.showMessageDialog(null, "Message limit reached.");
                    continue;
                }

                String recipientCell = JOptionPane.showInputDialog("Enter recipient's cell number (+27...)");
                String messageText = JOptionPane.showInputDialog("Enter message text:");

                if (recipientCell == null || messageText == null) continue;

                Message msg = new Message(messageText, recipientCell, messagesSent);

                if (messageText.length() > 250) {
                    JOptionPane.showMessageDialog(null, "Message too long (max 250 characters).");
                    continue;
                } else if (msg.checkRecipientCell() == 0) {
                    JOptionPane.showMessageDialog(null, "Invalid cell number format. Include '+27' and 9 digits.");
                    continue;
                }

                String hash = msg.createMessageHash();
                String result = msg.SentMessage(scanner);

                String details = "Message ID: " + msg.getMessageID() + "\n" +
                        "Message Hash: " + hash + "\n" +
                        "Recipient: " + recipientCell + "\n" +
                        "Message: " + messageText + "\n" +
                        "Status: " + result;

                JOptionPane.showMessageDialog(null, details, "Message Details", JOptionPane.INFORMATION_MESSAGE);

                messagesSent++;

            } else if (choice == 2) {
                JOptionPane.showMessageDialog(null, "Coming soon");

            } else if (choice == 3) {
                JOptionPane.showMessageDialog(null,
                        Message.printMessages(name),
                        "Senders & Recipients",
                        JOptionPane.INFORMATION_MESSAGE);

            } else if (choice == 4) {
                JOptionPane.showMessageDialog(null,
                        "Longest sent message: \n" + Message.getLongestSentMessage(),
                        "Longest Message",
                        JOptionPane.INFORMATION_MESSAGE);

            } else if (choice == 5) {
                String searchID = JOptionPane.showInputDialog("Enter message ID to search:");
                JOptionPane.showMessageDialog(null, Message.searchByMessageID(searchID),
                        "Search Result", JOptionPane.INFORMATION_MESSAGE);

            } else if (choice == 6) {
                String recipientSearch = JOptionPane.showInputDialog("Enter recipient cell to search:");
                JOptionPane.showMessageDialog(null, Message.searchByRecipient(recipientSearch),
                        "Search Result", JOptionPane.INFORMATION_MESSAGE);

            } else if (choice == 7) {
                String hashToDelete = JOptionPane.showInputDialog("Enter message hash to delete:");
                JOptionPane.showMessageDialog(null, Message.deleteByHash(hashToDelete),
                        "Delete Result", JOptionPane.INFORMATION_MESSAGE);

            } else if (choice == 8) {
                JOptionPane.showMessageDialog(null,
                        Message.displayMessageReport(name),
                        "Message Report",
                        JOptionPane.INFORMATION_MESSAGE);

            } else if (choice == 9) {
                JOptionPane.showMessageDialog(null, "Exiting QuickChat. Total messages sent: " + Message.getTotalMessagesSent());
                break;

            } else {
                JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        }

        scanner.close();
    }
}