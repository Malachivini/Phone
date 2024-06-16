import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//this class deals with managing all the media in the phone

public class PhonesMedia {
    private ArrayList<Media> mediaList; //keeps all media (videos and music)

    // c'tor
    public PhonesMedia() {
        mediaList = new ArrayList<>();
    }

    public void addMedia(Media media) {
        mediaList.add(media); 
    }

    public void playByName(String name) {
        for (Media media : mediaList) {
            if (media.getName().equals(name)) {
                System.out.println(media.playMedia());
                return;
            }
        }
     //if there is not a media with the wanted name:
     System.out.println("this media does not exist\n");
    }

    public void playAllMedia() {
        for (Media media : mediaList) {
            System.out.println(media.playMedia());
        }
    }


        // Method that save all the messages in a txt file
    public void saveToFile(String fileName) throws IOException {
        // Try statement with write_buff as the resource
        try (BufferedWriter write_buff = new BufferedWriter(new FileWriter(fileName))) {
            // Iterate over all messages in the collection and write them to the file
            for (Media media : mediaList) {
                write_buff.write(media.getName() + "," + media.getLength()+"," + media.getClass());
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
                String[] parts = line.split(",", 3);
                // If there are exactly two parts, create a new Message and add it to the list
                if (parts.length == 3) {
                    if(parts[2]=="class Music"){ 
                        mediaList.add(new Music(parts[0], parts[1]));
                    }
                    else
                    {
                        mediaList.add(new Video(parts[0], parts[1]));
                    }
                }
            }
        }
    }


    //this method  shows the user all the options he can do
    public void displayMenu() {
        // Create a new scanner object in order to get input from the user
        Scanner scanner = new Scanner(System.in);
        // Print the menu and perform operations
        while (true) {
            try {
                System.out.println("1. Add a Media");
                System.out.println("2. Play Media By Name");
                System.out.println("3. Play all Media");
                System.out.println("4. Exit");

                int action = scanner.nextInt();
                scanner.nextLine(); // Consume buffer and go past all chars in buffer
                switch (action) {
                    // 1. Add a Music
                    case 1:
                        System.out.println("What would you like to add? press '1' for Music, '2' for Video");
                        String choice = scanner.nextLine();
                        while (!(choice.equals("1")||choice.equals("2"))) { 
                            System.out.println("Enter Valid Choice!");
                            choice = scanner.nextLine();
                        }
                        System.out.println("Enter Media name: ");
                        String name = scanner.nextLine();
                        System.out.println("Enter length of Media in the format: (minutes):(secondes):");
                        String length = scanner.nextLine();

                        //make sure the input is valid
                         Pattern pattern = Pattern.compile("^([0-9]+):([0-5][0-9])$");
                         Matcher matcher = pattern.matcher(length);

                        while(!matcher.matches()){
                            System.out.println("Invalid input format, please enter new length:");
                            length = scanner.nextLine();
                            matcher = pattern.matcher(length);
                        }

                        //create a new media with the valid input
                        
                        if(choice.equals("1")){
                            Media newMusic = new Music(name, length);
                            addMedia(newMusic);
                        }
                        else{
                            Media newVideo = new Video(name, length);
                            addMedia(newVideo);
                        }
                        
                        break;
                    // 2. Play Media By Name
                    case 2:
                        
                        System.out.print("Enter Media name: ");
                        String mediaName = scanner.nextLine();
                        
                        playByName(mediaName);
                        break;

                    // 3. Play all Media
                    case 3:
                        playAllMedia();
                        break;

                    // 4. Exit
                    case 4:
                        return;

                    default:
                        System.out.println("Pick a valid number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid integer.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }

    

 
}
