
public abstract class Media {
    private String name;
    private String length;
    //c'tor
    public Media(String name, String length) {
        this.name = name;
        this.length = length;
    }

    //geters
    public String getName() {
        return name;
    }

    public String getLength() {
        return length;
    }

    //seters
    public void setName(String name) {
        this.name = name;
    }

    public void setLength(String length) {
        this.length = length;
    }

    //this method plays the media (returns information about the playing)
    public String playMedia() {
        return "\"" + getName() + "\" is now playing for " + getLength() + " minutes";
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Length: " + length;
    }
}
