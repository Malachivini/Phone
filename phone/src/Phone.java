import java.io.*;
import java.util.Scanner;

public class Phone   {
    public PhoneBook phoneBook;
    public Sms sms;
    public Calendar calendar;
    public PhonesMedia phonesMedia;
    // add your collections ///
    public Phone() {
        this.sms = new Sms(this.phoneBook);
        this.phoneBook = new PhoneBook(this.sms);
        this.calendar = new Calendar(this.phoneBook);
        this.phonesMedia = new PhonesMedia();
        //*  add them to the phone constractor*//
        
    }
    public void refreshData(){
        this.sms.phoneBook = phoneBook;
        this.phoneBook.sms=sms;
        this.calendar.phoneBook = this.phoneBook;
        //-refresh their data*//
    }
    // start the phone menu and load data fron files
    public void start() {
        loadDataFromFile(); // Load data from the phone book file when the Phone object is created
        displayMainMenu();
    }
    //Menu method
    public void displayMainMenu() {
       
        while (true) { 
            refreshData();
            System.out.println("Phone Menu:");
            System.out.println("1. Phone Book");
            System.out.println("2. SMS");
            System.out.println("3. Calendar");
            System.out.println("4. Media");    
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = getUserInput();
        
            switch (choice) {
                //1. Phone Book
                case 1:
                    phoneBookMenu();
                    break;
                //2. SMS
                case 2:
                    // Handle SMS menu
                    smsMenu();
                    break;
                //3. Calendar
                case 3:
                    CalendarMenu();
                    break;
                //4. Media
                case 4:
                    PhonesMediaMenu();
                    break;
                //5. Exit
                case 5:
                    exit();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    displayMainMenu();
                }
        }
    }
    // invoke phonebook menu
    private void phoneBookMenu() {
        phoneBook.displayMenu();
    }
    // invoke sms menu
    private void smsMenu() {
        sms.displayMenu();
    }

    private void CalendarMenu() {
        this.calendar.displayMenu();
    }
    private void PhonesMediaMenu() {
        this.phonesMedia.displayMenu();
    }
    // checks user choice an handle the error
    private int getUserInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Please enter a number: ");
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    // load data from all files for all the apps
    private void loadDataFromFile() {
        try {
            phoneBook.loadFromFile("phonebook");
            sms.loadFromFile("sms");
            calendar.loadWindowsFromFile("calendar.txt");
            phonesMedia.loadFromFile("media");
            //System.out.println("Phone book data loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading phone book data: " + e.getMessage());
        }
    }
    // exit procedure save all data into the files
    private void exit() {
        // Save data to the phone book file before exiting
        try {
            phoneBook.saveToFile("phonebook");
            sms.saveToFile("sms");
            phonesMedia.saveToFile("media");
            //*save data for calender*//
            System.out.println("Phone book data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving phone book data: " + e.getMessage());
        }
        System.out.println("Exiting phone application.");
        System.exit(0);
    }


    public static void main(String[] args) {
        Phone phone = new Phone();
        phone.start();
    }
}
