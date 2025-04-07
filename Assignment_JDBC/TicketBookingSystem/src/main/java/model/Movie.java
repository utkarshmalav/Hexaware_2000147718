package model;

public class Movie extends Event {
    private String genre;
    private String actorName;
    private String actressName;

    public Movie(String name, String date, String time, Venue venue, int seats,
                 double price, String genre, String actor, String actress) {
        super(name, date, time, venue, seats, price, "Movie");
        this.genre = genre;
        this.actorName = actor;
        this.actressName = actress;
    }

    @Override
    public void displayEventDetails() {
        System.out.println("Movie: " + eventName + ", Genre: " + genre +
                ", Actor: " + actorName + ", Actress: " + actressName);
    }
}
