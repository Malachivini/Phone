public class Music extends Media {
    public Music(String name, String length) {
        super(name, length);
    }

    @Override
    public String toString() {
        return "Music - " + super.toString();
    }
}
