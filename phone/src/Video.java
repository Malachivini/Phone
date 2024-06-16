public class Video extends Media {
    public Video(String name, String length) {
        super(name, length);
    }

    @Override
    public String toString() {
        return "Video - " + super.toString();
    }
}
