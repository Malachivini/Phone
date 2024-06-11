import java.io.*;
import java.util.*;

public class PhoneBook {
    // List that stores all contacts/create a collection of contacts and the sms that already exist
    public ArrayList<Contact> contacts;
    public Sms sms;

    // Constructor of PhoneBook, creates an empty PhoneBook
    public PhoneBook(Sms s) {
        contacts = new ArrayList<>();
        this.sms = s;
    }

    // Constructor of PhoneBook, creates an empty PhoneBook
    public PhoneBook() {
        contacts = new ArrayList<>();
        contacts.add(new Contact("Yosi","5555"));
    }

    // Add new contact to PhoneBook
    public void addContact(String name, String phone) {
        // add is a method for ArrayList and we used Contact constructor
        if (!this.isNameAlreadyPresent(contacts, name) && (phone.length() == 10)){
            this.contacts.add(new Contact(name, phone));
            //System.out.println("Contact added successfully");
        }
        else
            System.out.println("Contact already exists or you entered " +
                    "a phone number different from 10 digits");
    }

    // Method to remove contact from phonebook via name
    public boolean removeContact(String name) {
        // Create iterator in order to delete an element safely
        Iterator<Contact> iterator = contacts.iterator();
        // Check if there is more
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            if (contact.getName().equals(name)) {
                iterator.remove();
                // remove data from sms
                sms.removeMessages(name);
                return true;
            }
        }
        return false;
    }
     
    //Method that prints all contacts
    public void printPhoneBook() {
        // Iterate over all contacts in the collection
        for (Contact contact : contacts)
            System.out.println(contact.ContactStringInfo());
    }

    //Method that searches for all contacts with the same name that the user selected
    public Contact searchContact(String name) {
        // Create a new ArrayList to store all occurrences of the name
        // Iterate over all contacts in the collection
        for (Contact contact : contacts) {
            // Check for the same name
            if (contact.getName().equals(name)) {
                return contact;
            }
        }
        return null;
    }

        //Method that searches for all contacts with the same name that the user selected
        public Contact findContact(String name) {
            // Iterate over all contacts in the collection
            for (Contact contact : contacts) {
                // Check for the same name
                if (contact.getName().equals(name)) {
                    return contact;
                }
            }
            return null;
        }

    // Sort by name using Collections.sort
    public void sortByName() {
        // Use Collections.sort with a Comparator to sort contacts by name
        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            //Override copare method
            public int compare(Contact c1, Contact c2) {
                return c1.getName().toLowerCase().compareTo(c2.getName().toLowerCase());
            }
        });
    }

    // Sort by phone using Collections.sort
    public void sortByPhone() {
        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            //Override copare method
            public int compare(Contact c1, Contact c2) {
                return c1.getPhone().compareTo(c2.getPhone());
            }
        });
    }

    // Method that checks if a contact is already exist
    public boolean isNameAlreadyPresent(ArrayList<Contact> contacts, String name) {
        // Iterate over all contacts in the provided list
        for (Contact c : contacts) {
            // Check if the contact name matches the provided contact's name
            if (c.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // Method that Reverse the order of the contacts list
    public void reverseOrder() {
        // Reverse the order of the contacts list
        Collections.reverse(contacts);
    }

    // Method that save all the contacts in a txt file
    public void saveToFile(String fileName) throws IOException {
        // Try statement with write_buff as the resource
        try (BufferedWriter write_buff = new BufferedWriter(new FileWriter(fileName))) {
            // Iterate over all contacts in the collection and write them to the file
            for (Contact contact : contacts) {
                write_buff.write(contact.getName() + "," + contact.getPhone());
                write_buff.newLine();
            }
        }
    }

    // Method that load all the contacts from a txt file
    public void loadFromFile(String fileName) throws IOException {
        // Try statement with read_buff as the resource
        try (BufferedReader read_buff = new BufferedReader(new FileReader(fileName))) {
            String line;
            // Read each line from the file
            while ((line = read_buff.readLine()) != null) {
                // Split the line by ',' to get name and phone
                String[] parts = line.split(",");
                // If there is more than one ',' it will ignore this line
                if (parts.length == 2) {
                    addContact(parts[0], parts[1]);
                }
            }
        }
    }

    // Method that shows the user all the options he can do
    public void displayMenu() {
        // Create a new scanner object in order to get input from the user
        Scanner scanner = new Scanner(System.in);
        // Print the menu and perform operations
        while (true) {
            try {
                System.out.println("1. Add Contact");
                System.out.println("2. Remove Contact");
                System.out.println("3. Print Contacts");
                System.out.println("4. Search Contact");
                System.out.println("5. Sort by Name");
                System.out.println("6. Sort by Phone");
                System.out.println("7. Reverse Order");
                System.out.println("8. Exit");

                int action = scanner.nextInt();
                scanner.nextLine(); // Consume buffer and go past all chars in buffer
                switch (action) {
                    // 1. Add Contact
                    case 1:
                        //Asks the user to enter data about the contact he want to add
                        System.out.print("Enter contact name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter phone number: ");
                        String phone = scanner.nextLine();
                        // Call constructor on contact
                        addContact(name, phone);
                        break;
                    // 2. Remove Contact
                    case 2:
                        //Asks the user to enter data about the contact he want to remove
                        System.out.print("Enter contact name to remove: ");
                        name = scanner.nextLine();
                        // Use method removeContact from phoneBook in order to safely delete contact
                        if (removeContact(name)) {
                            System.out.println("Contact removed.");
                        } else {
                            System.out.println("Contact not found.");
                        }
                        break;
                    // 3. Print Contacts
                    case 3:
                        // Use method printPhoneBook from phoneBook that calls method ContactStringInfo for each contact
                        printPhoneBook();
                        break;
                    // 4. Search Contact
                    case 4:
                        ////Asks the user to enter data about the contact he want to search
                        System.out.print("Enter name to search: ");
                        name = scanner.nextLine();
                        // Create an ArrayList of all the occurrences of the name
                        Contact result = searchContact(name);
                        System.out.println(result.ContactStringInfo());

                        break;
                    // 5. Sort by Name
                    case 5:
                        sortByName();
                        break;
                    // 6. Sort by Phone
                    case 6:
                        sortByPhone();
                        break;
                    // 7. Reverse Order
                    case 7:
                        reverseOrder();
                        break;
                    // 8. Exit
                    case 8:
                        return;
                    // User entered an invalid number
                    default:
                        System.out.println("Pick a valid number.");
               
                    }} catch (InputMismatchException e) {
                        System.out.println("Please enter a valid integer.");
                        scanner.nextLine(); // Consume invalid input
                    }
                }
            }
        
    }
    
    class Contact {
        // Arguments for the class contacts
        private String name;
        private String phone;
    
        // Constructor for the class
        public Contact(String name, String phone) {
            this.name = name;
            this.phone = phone;
        }
    
        // Default constructor
        public Contact() {
            this("no one", "0000000000");
        }
    
        // Method for extracting the name
        public String getName() {
            return name;
        }
    
        // Method for extracting the phone number
        public String getPhone() {
            return phone;
        }
    
        // Method to return contact info as a string
        public String ContactStringInfo() {
            return "Contact{name='" + name + "', phone='" + phone + "'}";
        }
    }
    