import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Calendar {
    private Day[] days;
    private PhoneBook phoneBook;
    private Date startDate;

    public Calendar(PhoneBook phoneBook) {
        this.phoneBook = phoneBook;
        this.days = new Day[30];
        try {
            this.startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2024-07-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(startDate);
        for (int i = 0; i < 30; i++) {
            days[i] = new Day(cal.getTime());
            cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
        }
    }

    public Day getDay(int index) {
        if (index >= 1 && index <= 30) {
            return days[index - 1];
        } else {
            throw new IndexOutOfBoundsException("Invalid day index. It should be between 1 and 30.");
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public PhoneBook getPhoneBook() {
        return phoneBook;
    }

    public void addWindowToDay(int dayIndex, Window window) {
        if (dayIndex >= 0 && dayIndex < 30) {
            days[dayIndex].addWindow(window);
        } else {
            throw new IndexOutOfBoundsException("Invalid day index. It should be between 0 and 29.");
        }
    }

    public void printMenu() {
        System.out.println("Calendar Menu:");
        System.out.println("1. Add a window");
        System.out.println("2. Delete a window");
        System.out.println("3. Print events on a date");
        System.out.println("4. Print meetings with a contact");
        System.out.println("5. Check and remove overlaps");
        System.out.println("6. Print all events");
        System.out.println("7. Exit");
    }

    public void handleMenuOption(int option, Scanner scanner) {
        switch (option) {
            case 1:
                addWindow(scanner);
                break;
            case 2:
                deleteWindow(scanner);
                break;
            case 3:
                printEventsOnDate(scanner);
                break;
            case 4:
                printMeetingsWithContact(scanner);
                break;
            case 5:
                checkAndRemoveOverlaps();
                break;
            case 6:
                printAllEvents();
                break;
            case 7:
                System.out.println("Exiting application.");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private void addWindow(Scanner scanner) {
        System.out.print("Enter day index (1-30): ");
        int dayIndex = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter type (Meeting/Event): ");
        String type = scanner.nextLine();
        System.out.print("Enter start time (HH:mm): ");
        String startTimeStr = scanner.nextLine();
        Date startTime = parseTime(startTimeStr);
        System.out.print("Enter duration (1-60 minutes): ");
        int duration = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (type.equalsIgnoreCase("Meeting")) {
            System.out.print("Enter contact name: ");
            String contactName = scanner.nextLine();
            Contact contact = phoneBook.findContact(contactName);
            if (contact != null) {
                getDay(dayIndex).addWindow(new Meeting(startTime, duration, contact));
                System.out.println("Meeting added.");
            } else {
                System.out.println("Contact not found.");
            }
        } else if (type.equalsIgnoreCase("Event")) {
            System.out.print("Enter description: ");
            String description = scanner.nextLine();
            getDay(dayIndex).addWindow(new Event(startTime, duration, description));
            System.out.println("Event added.");
        } else {
            System.out.println("Invalid type.");
        }
    }


    private void deleteWindow(Scanner scanner) {
        System.out.print("Enter day index (1-30): ");
        int dayIndex = scanner.nextInt();
        scanner.nextLine(); // consume newline
        if (dayIndex >= 1 && dayIndex <= 30) {
            Day day = getDay(dayIndex);
            List<Window> windows = day.getWindows();
            if (windows.isEmpty()) {
                System.out.println("No windows to delete.");
                return;
            }
            for (int i = 0; i < windows.size(); i++) {
                System.out.println((i + 1) + ". " + windows.get(i));
            }
            System.out.print("Enter window index to delete: ");
            int windowIndex = scanner.nextInt() - 1; // Convert to 0-based index
            if (windowIndex >= 0 && windowIndex < windows.size()) {
                day.removeWindow(windowIndex);
                System.out.println("Window deleted.");
            } else {
                System.out.println("Invalid window index.");
            }
        } else {
            System.out.println("Invalid day index.");
        }
    }


    private void printEventsOnDate(Scanner scanner) {
        System.out.print("Enter day index (1-30): ");
        int dayIndex = scanner.nextInt();
        scanner.nextLine(); // consume newline
        if (dayIndex >= 1 && dayIndex <= 30) {
            Day day = getDay(dayIndex);
            day.getWindows().forEach(System.out::println);
        } else {
            System.out.println("Invalid day index.");
        }
    }

    private void checkAndRemoveOverlaps() {
        for (Day day : days) {
            List<Window> windows = day.getWindows();
            windows.sort(Comparator.comparing(Window::getStartTime));
            for (int i = 0; i < windows.size() - 1; i++) {
                Window current = windows.get(i);
                Window next = windows.get(i + 1);
                if (current.overlapsWith(next)) {
                    windows.remove(next);
                    i--; // recheck the current index
                    System.out.println("Removed overlapping window: " + next);
                }
            }
        }
    }

    private void printMeetingsWithContact(Scanner scanner) {
        System.out.print("Enter contact name: ");
        String contactName = scanner.nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < days.length; i++) {
            Day day = days[i];
            for (Window window : day.getWindows()) {
                if (window instanceof Meeting && ((Meeting) window).getContact().getName().equals(contactName)) {
                    System.out.println(sdf.format(day.getDate()) + " " + window);
                }
            }
        }
    }

    private void printAllEvents() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Day day : days) {
            System.out.println("On " + sdf.format(day.getDate()) + ":");
            day.getWindows().forEach(System.out::println);
        }
    }

    private Date parseTime(String timeStr) {
        try {
            return new SimpleDateFormat("HH:mm").parse(timeStr);
        } catch (ParseException e) {
            System.out.println("Invalid time format. Please use HH:mm.");
            return null;
        }
    }

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Calendar calendar = new Calendar(phoneBook);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            calendar.printMenu();
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline
            calendar.handleMenuOption(option, scanner);
        }
    }
}
