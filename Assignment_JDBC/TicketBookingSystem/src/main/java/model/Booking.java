package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Booking {
    private static int idCounter = 1;
    private int bookingId;
    private List<Customer> customers;
    private Event event;
    private int numTickets;
    private double totalCost;
    private Date bookingDate;

    public Booking(List<Customer> customers, Event event, int numTickets) {
        this.bookingId = idCounter++;
        this.customers = new ArrayList<>(customers);
        this.event = event;
        this.numTickets = numTickets;
        this.totalCost = event.getTicketPrice() * numTickets;
        this.bookingDate = new Date();
    }

    public void displayBookingDetails() {
        System.out.println("Booking ID: " + bookingId + ", Event: " + event.getEventName() + ", Tickets: " + numTickets + ", Total Cost: Rs." + totalCost);
        for (Customer c : customers) {
            c.displayCustomerDetails();
        }
    }

    public int getBookingId() {
        return bookingId;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public Event getEvent() {
        return event;
    }

    public int getNumTickets() {
        return numTickets;
    }
}
