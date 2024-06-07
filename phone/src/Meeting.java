import java.util.Date;


public class Meeting extends Window {
    private Contact contact;

    public Meeting(Date startTime, int duration, Contact contact) {
        super(startTime, duration);
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

    @Override
    public String getDescription() {
        return "Meeting with " + contact.getName();
    }

    @Override
    public String toString() {
        return "Meeting: " + super.toString() + " With " + contact.getName() + '.';
    }
}