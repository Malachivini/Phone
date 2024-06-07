import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Window {
    private Date startTime;
    private int duration;

    public Window(Date startTime, int duration) {
        if (duration < 1 || duration > 60) {
            throw new IllegalArgumentException("Duration must be between 1 and 60 minutes.");
        }
        this.startTime = startTime;
        this.duration = duration;
    }

    public Date getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
    }

    public abstract String getDescription();

    public boolean overlapsWith(Window other) {
        long thisStart = startTime.getTime();
        long thisEnd = thisStart + duration * 60 * 1000;
        long otherStart = other.getStartTime().getTime();
        long otherEnd = otherStart + other.getDuration() * 60 * 1000;
        return (thisStart < otherEnd && thisEnd > otherStart);
    }

    protected String formatTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }

    public Date addMinutesToTime(Date startTime, int minutes) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(java.util.Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    @Override
    public String toString() {
        return formatTime(startTime) + " - " + formatTime(addMinutesToTime(startTime,duration)) + ':';
    }
}