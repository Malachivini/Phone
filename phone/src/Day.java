import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Day {
    private Date date;
    private List<Window> windows;

    public Day(Date date) {
        this.date = date;
        this.windows = new ArrayList<>();
    }

    public Date getDate() {
        return date;
    }

    public List<Window> getWindows() {
        return windows;
    }

    public void addWindow(Window window) {
        windows.add(window);
        Collections.sort(windows, Comparator.comparing(Window::getStartTime));
    }

    public void removeWindow(int index) {
        if (index >= 0 && index < windows.size()) {
            windows.remove(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid window index.");
        }
    }

    @Override
    public String toString() {
        return "Day{date=" + date + ", windows=" + windows + '}';
    }
}
