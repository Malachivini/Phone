import java.util.Date;


public class Event extends Window {
    private String description;

    public Event(Date startTime, int duration, String description) {
        super(startTime, duration);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Event: " + super.toString() + " description='" + description + "'";
    }
}