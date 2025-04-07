package model;

public class Concert extends Event {
    private String artist;
    private String type;

    public Concert(String name, String date, String time, Venue venue, int seats,
                   double price, String artist, String type) {
        super(name, date, time, venue, seats, price, "Concert");
        this.artist = artist;
        this.type = type;
    }

    @Override
    public void displayEventDetails() {
        System.out.println("Concert by " + artist + ", Type: " + type);
    }
}