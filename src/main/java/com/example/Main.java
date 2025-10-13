package com.example;
import javax.swing.JOptionPane;
import java.util.Scanner;

import static java.lang.System.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(in);
   
        // Get user details for registration
        out.print("Enter your name: ");
        String name = scanner.nextLine();
        out.print("Enter your surname: ");
        String surname = scanner.nextLine();
        
        out.print("Enter your username: ");
        String username = scanner.nextLine();
        out.print("Enter your password: ");
        String password = scanner.nextLine();
        out.print("Enter your phone number: ");
        String phoneNumber = scanner.nextLine();

        // Create a login object
        login user = new login(name, surname, username, password, phoneNumber);
        
        // Registering the user
        String registrationMessage = user.registerUser(name, password, phoneNumber, username, surname);
        out.println(registrationMessage);

        // If registration is successful, proceed to login
        if (!registrationMessage.equals("Registration successful!")) {
            return;
        }


        out.print("Enter your username to login: ");
        String loginUsername = scanner.nextLine();
        out.print("Enter your password to login: ");
        String loginPassword = scanner.nextLine();

        // check login status and prints message
        boolean loginStatus = user.loginUser(loginUsername, loginPassword, phoneNumber);
        out.println(user.returnLoginStatus(loginStatus));

        if (!loginStatus) {
            out.println("Incorrect login details. Ending program.");
            return;
        }
        
        // Message sending functionality
        out.println("\nWelcome to QuickChat.");
        out.print("How many messages would you like to send? ");
        int messageLimit = scanner.nextInt();

        int messagesSent = 0;
        while (true) {
            out.print("\nChoose an option: \n");
            out.println("1) Send message");
            out.println("2) Show sent messages");
            out.println("3) Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
            case 1:
                if (messagesSent >= messageLimit) {
                    out.println("Message limit reached. You cannot send more messages.");
                    break;
                }

                out.print("Enter recipient's cell number: ");
                String recipientCell = scanner.nextLine();
                out.print("Enter message: ");
                String messageText = scanner.nextLine();

                Message msg = new Message(messageText, recipientCell);

                if (messageText.length() > 250) {
                    out.println("Please enter a message of less than 50 characters");
                    break;
                }
                
                if (!msg.checkMessageID()) {
                    out.println("Message ID already used or invalid. Please use a unique ID.");
                    break;
                }

                if (msg.checkRecipientCell() == 0) {
                    out.println("Invalid recipient cell number. Please include the country code.");
                    break;
                }

                String hash = msg.createMessageHash(messagesSent);
                out.println("Message Hash: " + hash);


                String result = msg.SentMessage(scanner);
                out.println(result);

                String messageDetails =
                        "Message ID: " + msg.getMessageID() + "\n" +
                        "Message Hash: " + hash + "\n" +
                        "Recipient: " + recipientCell + "\n" +
                        "Message: " + messageText + "\n\n" +
                        "Status: " + result;

                JOptionPane.showMessageDialog(null, messageDetails, "Message Sent", JOptionPane.INFORMATION_MESSAGE);

                messagesSent++;
                break;

                case 2:
                out.println("Coming soon");
                break;

            case 3:
                out.println("Exiting QuickChat. Goodbye!");
                JOptionPane.showMessageDialog(null, "Total messages sent: " + Message.getTotalMessagesSent(), "Summary", JOptionPane.INFORMATION_MESSAGE);
                scanner.close();
                return;

            default:
                out.println("Invalid option. Please try again.");
                break;
            }
        }
}
}
                
