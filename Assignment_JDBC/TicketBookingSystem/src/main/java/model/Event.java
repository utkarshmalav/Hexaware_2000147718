package model;

public abstract class Event {
    protected String eventName;
    protected String eventDate;
    protected String eventTime;
    protected Venue venue;
    protected int totalSeats;
    protected int availableSeats;
    protected double ticketPrice;
    protected String eventType;

    public Event() {}

    public Event(String eventName, String eventDate, String eventTime, Venue venue,
                 int totalSeats, double ticketPrice, String eventType) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.venue = venue;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.ticketPrice = ticketPrice;
        this.eventType = eventType;
    }

    public void bookTickets(int numTickets) {
        if (availableSeats >= numTickets) {
            availableSeats -= numTickets;
        }
    }



    public void cancelBooking(int numTickets) {
        availableSeats += numTickets;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public double calculateTotalRevenue() {
        return (totalSeats - availableSeats) * ticketPrice;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public abstract void displayEventDetails();

    public String getEventName() {
        return eventName;
    }

}
