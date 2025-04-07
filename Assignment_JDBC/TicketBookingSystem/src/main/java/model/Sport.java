package model;

public class Sport extends Event {
    private String sportName;
    private String teamsName;

    public Sport(String name, String date, String time, Venue venue, int seats,
                 double price, String sportName, String teamsName) {
        super(name, date, time, venue, seats, price, "Sport");
        this.sportName = sportName;
        this.teamsName = teamsName;
    }

    @Override
    public void displayEventDetails() {
        System.out.println("Sport: " + sportName + ", Match: " + teamsName);
    }
}
