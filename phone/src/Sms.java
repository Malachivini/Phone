import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Sms {
    // List that stores all messages/create a collection of messages and the phoneBook that already exist
    public  ArrayList<Message> messages;
    public  PhoneBook phoneBook;
    
    // Constructor of Sms, creates an empty messages
    public Sms(PhoneBook pb) {
        messages = new ArrayList<>();
        this.phoneBook = pb;
    }
    //print all message for a given contact
    public void printContactMsgs(String name){
        ArrayList<Message> results = new ArrayList<>();
        // search a contact messages in all messages
        for( Message message: messages){
            if (message.getContactName().equals(name))
            {
                results.add(message);
            }
        }
        //In case there are no conversations with the desired contact, we will print an appropriate message
        if (results.isEmpty())
        {
            System.out.println("There is no messages for this contact");
        }
        else{
        // print all messages
        for (Message message : results) {
            System.out.println(message);
        }
    }

    }

    //Method that deletes all conversations with a selected contact
    public void removeMessages(String name){
        // Create a new ArrayList to hold the messages that are not removed.
        ArrayList<Message> results = new ArrayList<>();
        // Any conversation that is not with the selected contact will be saved in an array of results
        for( Message message: messages){
            if (!message.getContactName().equals(name))
            {
                results.add(message);
            }
        }
        messages= results;

    }

    // Method that returns all messages in which a word/sentence that the user selected appears
    public void searchMessage(String msg){
        // Create a new ArrayList to hold the messages that the word/sentence that the user selected appears
        ArrayList<Message> results = new ArrayList<>();
        //Goes through all the messages and checks whether the word/sentence the user selected appears
        for( Message message: messages){
            if (message.getMsg().contains(msg))
            {
                results.add(message);
            }
        }
        //Print all the messages and checks whether the word/sentence the user selected appears
        for (Message message : results) {
            System.out.println(message);
        }

    }

    //Method that sends a message according to the content of the message and the contact
    public void sendMessages(String msg, String contact_name) {
        //Checking if there is a contact who should receive the message and the message have a content
        if (this.phoneBook.isNameAlreadyPresent(phoneBook.contacts, contact_name) && msg.length() != 0) {
            this.messages.add(new Message(msg, contact_name));
            System.out.println("Message sent!");
        } else {
            System.out.println("You didn't enter any message or there is no contact with this name");
        }
    }

    //Method that print all the messages
    public void printAllMessages() {
        //Goes through all the messages and prints them
        for (Message message : messages) {
            System.out.println(message);
        }
    }

    // Method that shows the user all the options he can do
    public void displayMenu() {
        // Create a new scanner object in order to get input from the user
        Scanner scanner = new Scanner(System.in);
        // Print the menu and perform operations
        while (true) {
            try {
                System.out.println("1. Add Message To Contact");
                System.out.println("2. Remove Messages From Contact");
                System.out.println("3. Print Contact Messages");
                System.out.println("4. Search Message(Part of Message)");
                System.out.println("5. Print All Messages");
                System.out.println("6. Exit");

                int action = scanner.nextInt();
                scanner.nextLine(); // Consume buffer and go past all chars in buffer
                switch (action) {
                    // 1. Add Message To Contact
                    case 1:
                        
                        System.out.print("Enter contact name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Message: ");
                        String msg = scanner.nextLine();
                        // Call constructor on contact
                        sendMessages(msg, name);
                        break;
                    // 2. Remove Messages From Contact
                    case 2:
                        System.out.print("Enter contact name to remove his messages: ");
                        name = scanner.nextLine();
                        removeMessages(name);
                        break;
                    // 3. Print Contact Messages
                    case 3:
                        System.out.print("Enter contact name to view his messages: ");
                        name = scanner.nextLine();
                        printContactMsgs(name);
                        break;
                    // 4. Search Message(Part of Message)
                    case 4:
                        System.out.print("Enter Message to search: ");
                        msg = scanner.nextLine();
                        searchMessage(msg);
                        break;
                    // 5. Print All Messages
                    case 5:
                        printAllMessages();
                        break;
                    // 6. Exit
                    case 6:
                        return;
                    // User entered an invalid number
                    default:
                        System.out.println("Pick a valid number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid integer.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }

    // Method that save all the messages in a txt file
    public void saveToFile(String fileName) throws IOException {
        // Try statement with write_buff as the resource
        try (BufferedWriter write_buff = new BufferedWriter(new FileWriter(fileName))) {
            // Iterate over all messages in the collection and write them to the file
            for (Message message : messages) {
                write_buff.write(message.getContactName() + "," + message.getMsg());
                write_buff.newLine();
            }
        }
    }

    // Method that load all the messages from a txt file
    public void loadFromFile(String fileName) throws IOException {
        // Try statement with read_buff as the resource
        try (BufferedReader read_buff = new BufferedReader(new FileReader(fileName))) {
            String line;
            // Read each line from the file
            while ((line = read_buff.readLine()) != null) {
                // Split the line by ',' to get contact_name and msg
                String[] parts = line.split(",", 2);
                // If there are exactly two parts, create a new Message and add it to the list
                if (parts.length == 2) {
                    messages.add(new Message(parts[1], parts[0]));
                }
            }
        }
    }

   
}

class Message {
    // Arguments for the class Message
    private String msg;
    private String contact_name;

    // Constructor for the class
    public Message(String msg, String name) {
        this.msg = msg;
        this.contact_name = name;
    }

    // Method for extracting the content message
    public String getMsg() {
        return this.msg;
    }

    // Method for extracting the contact name
    public String getContactName() {
        return this.contact_name;
    }

    // Method to return message info as a string
    @Override
    public String toString() {
        return " you sent to " + this.contact_name + ": "+ this.msg;
    }
}