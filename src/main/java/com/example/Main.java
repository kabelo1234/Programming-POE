package com.example;
import com.example.login;
import com.example.Message;
import javax.swing.JOptionPane;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
   
        // Get user details for registration
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your surname: ");
        String surname = scanner.nextLine();
        
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your phone number: ");
        String phoneNumber = scanner.nextLine();

        // Create a login object
        login user = new login(name, surname, username, password, phoneNumber);
        
        // Registering the user
        String registrationMessage = user.registerUser(name, password, phoneNumber, username, surname);
        System.out.println(registrationMessage);

        // If registration is successful, proceed to login
        if (!registrationMessage.equals("Registration successful!")) {
            return;
        }


        System.out.print("Enter your username to login: ");
        String loginUsername = scanner.nextLine();
        System.out.print("Enter your password to login: ");
        String loginPassword = scanner.nextLine();

        // check login status and prints message
        boolean loginStatus = user.loginUser(loginUsername, loginPassword, phoneNumber);
        System.out.println(user.returnLoginStatus(loginStatus));
        
        // Message sending functionality
        System.out.println("\nWelcome to QuickChat.");
        System.out.print("How many messages would you like to send? ");
        int messageLimit = scanner.nextInt();

        int messagesSent = 0;
        while (true) {
            System.out.print("\nChoose an option:");
            System.out.println("1) Send message");
            System.out.println("2) Show sent messages");
            System.out.println("3) Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
            case 1:
                if (messagesSent >= messageLimit) {
                    System.out.println("Message limit reached. You cannot send more messages.");
                    break;
                }

                System.out.print("Enter message ID: ");
                String messageID = scanner.nextLine();
                System.out.print("Enter recipient's cell number: ");
                String recipientCell = scanner.nextLine();
                System.out.print("Enter message content: ");
                String messageText = scanner.nextLine();

                Message msg = new Message(messageID, messageText, recipientCell);
                
                if (!msg.checkMessageID()) {
                    System.out.println("Message ID already used or invalid. Please use a unique ID.");
                    break;
                }

                if (msg.checkRecipientCell() == 0) {
                    System.out.println("Invalid recipient cell number. Please include the country code.");
                    break;
                }

                String hash = msg.createMessageHash();
                System.out.println("Message Hash: " + hash);

                String result = msg.SentMessage(scanner);
                System.out.println(result);

                messagesSent++;
                break;

            case 2:
                System.out.println("Coming soon.");
                break;
            case 3:
                System.out.println("Exiting QuickChat. Goodbye!");
                scanner.close();
                return;

            default:
                System.out.println("Invalid option. Please try again.");
                break;
            }
        }

        System.out.println("Total messages sent: " + Message.getTotalMessagesSent());
        JOptionPane.showMessageDialog(null, "Total messages sent: " + Message.getTotalMessagesSent(), "Summary", JOptionPane.INFORMATION_MESSAGE);
        scanner.close();
 
}
}
                
