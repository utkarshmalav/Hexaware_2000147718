// Package: bean

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

// Venue class
class Venue {
    private String venueName;
    private String address;

    public Venue() {}
    public Venue(String venueName, String address) {
        this.venueName = venueName;
        this.address = address;
    }

    public void displayVenueDetails() {
        System.out.println("Venue: " + venueName + ", Address: " + address);
    }

    public String getVenueName() { return venueName; }
    public String getAddress() { return address; }
}

// Abstract Event class
abstract class Event {
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
        if (availableSeats >= numTickets) availableSeats -= numTickets;
        else System.out.println("Not enough tickets available.");
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

    public abstract void displayEventDetails();
}

// Subclasses: Movie, Concert, Sport
class Movie extends Event {
    private String genre, actorName, actressName;

    public Movie(String name, String date, String time, Venue venue, int seats,
                 double price, String genre, String actor, String actress) {
        super(name, date, time, venue, seats, price, "Movie");
        this.genre = genre;
        this.actorName = actor;
        this.actressName = actress;
    }

    @Override
    public void displayEventDetails() {
        System.out.println("Movie: " + eventName + ", Genre: " + genre + ", Actor: " + actorName + ", Actress: " + actressName);
    }
}

class Concert extends Event {
    private String artist, type;

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

class Sport extends Event {
    private String sportName, teamsName;

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

// Customer class
class Customer {
    private String customerName, email, phoneNumber;

    public Customer(String name, String email, String phone) {
        this.customerName = name;
        this.email = email;
        this.phoneNumber = phone;
    }

    public void displayCustomerDetails() {
        System.out.println("Customer: " + customerName);
    }

    public String getName() { return customerName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
}

// Booking class
class Booking {
    private static int idCounter = 1;
    private int bookingId;
    private List<Customer> customers;
    private Event event;
    private int numTickets;
    private double totalCost;
    private Date bookingDate;

    public Booking(List<Customer> customers, Event event, int numTickets) {
        this.bookingId = idCounter++;
        this.customers = customers;
        this.event = event;
        this.numTickets = numTickets;
        this.totalCost = event.ticketPrice * numTickets;
        this.bookingDate = new Date();
    }

    public void displayBookingDetails() {
        System.out.println("Booking ID: " + bookingId + ", Event: " + event.eventName + ", Tickets: " + numTickets + ", Total Cost: Rs." + totalCost);
        for (Customer c : customers) {
            c.displayCustomerDetails();
        }
    }

    public int getBookingId() { return bookingId; }
    public double getTotalCost() { return totalCost; }
    public Date getBookingDate() { return bookingDate; }
    public List<Customer> getCustomers() { return customers; }
    public Event getEvent() { return event; }
    public int getNumTickets() { return numTickets; }
}

// DBUtil
class DBUtil {
    public static Connection getDBConn() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/TicketBookingSystem", "root", "password");
    }
}

// Main class
public class TicketBookingSystemApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Event> eventList = new ArrayList<>();
        List<Booking> bookingList = new ArrayList<>();

        while (true) {
            System.out.println("\n1. Create Event\n2. Book Tickets\n3. View Bookings\n4. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    try (Connection conn = DBUtil.getDBConn()) {
                        System.out.print("Enter event type (Movie/Concert/Sport): ");
                        String type = sc.nextLine();
                        System.out.print("Enter name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter date (YYYY-MM-DD): ");
                        String date = sc.nextLine();
                        System.out.print("Enter time (HH:MM:SS): ");
                        String time = sc.nextLine();
                        System.out.print("Enter total seats: ");
                        int seats = sc.nextInt();
                        System.out.print("Enter ticket price: ");
                        double price = sc.nextDouble();
                        sc.nextLine();
                        System.out.print("Enter venue name: ");
                        String venueName = sc.nextLine();
                        System.out.print("Enter address: ");
                        String address = sc.nextLine();

                        PreparedStatement venueStmt = conn.prepareStatement("INSERT INTO Venue(venue_name, address) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
                        venueStmt.setString(1, venueName);
                        venueStmt.setString(2, address);
                        venueStmt.executeUpdate();
                        ResultSet venueRs = venueStmt.getGeneratedKeys();
                        int venueId = 0;
                        if (venueRs.next()) venueId = venueRs.getInt(1);
                        Venue venue = new Venue(venueName, address);

                        Event event = null;
                        String insertEventSQL = "INSERT INTO Event(event_name, event_date, event_time, venue_id, total_seats, available_seats, ticket_price, event_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement eventStmt = conn.prepareStatement(insertEventSQL);
                        eventStmt.setString(1, name);
                        eventStmt.setString(2, date);
                        eventStmt.setString(3, time);
                        eventStmt.setInt(4, venueId);
                        eventStmt.setInt(5, seats);
                        eventStmt.setInt(6, seats);
                        eventStmt.setDouble(7, price);
                        eventStmt.setString(8, type);
                        eventStmt.executeUpdate();

                        if (type.equalsIgnoreCase("Movie")) {
                            System.out.print("Enter genre: ");
                            String genre = sc.nextLine();
                            System.out.print("Enter actor: ");
                            String actor = sc.nextLine();
                            System.out.print("Enter actress: ");
                            String actress = sc.nextLine();
                            event = new Movie(name, date, time, venue, seats, price, genre, actor, actress);
                        } else if (type.equalsIgnoreCase("Concert")) {
                            System.out.print("Enter artist: ");
                            String artist = sc.nextLine();
                            System.out.print("Enter type: ");
                            String concertType = sc.nextLine();
                            event = new Concert(name, date, time, venue, seats, price, artist, concertType);
                        } else if (type.equalsIgnoreCase("Sport")) {
                            System.out.print("Enter sport name: ");
                            String sportName = sc.nextLine();
                            System.out.print("Enter teams: ");
                            String teams = sc.nextLine();
                            event = new Sport(name, date, time, venue, seats, price, sportName, teams);
                        }

                        if (event != null) {
                            eventList.add(event);
                            System.out.println("Event created successfully!");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    if (eventList.isEmpty()) {
                        System.out.println("No events available.");
                        break;
                    }
                    for (int i = 0; i < eventList.size(); i++) {
                        System.out.print((i + 1) + ". ");
                        eventList.get(i).displayEventDetails();
                    }
                    System.out.print("Choose event number: ");
                    int eventNo = sc.nextInt();
                    Event selectedEvent = eventList.get(eventNo - 1);

                    System.out.print("Enter number of tickets: ");
                    int numTickets = sc.nextInt();
                    sc.nextLine();
                    List<Customer> customers = new ArrayList<>();
                    for (int i = 0; i < numTickets; i++) {
                        System.out.print("Enter customer name: ");
                        String cname = sc.nextLine();
                        System.out.print("Enter email: ");
                        String email = sc.nextLine();
                        System.out.print("Enter phone: ");
                        String phone = sc.nextLine();
                        customers.add(new Customer(cname, email, phone));
                    }
                    Booking booking = new Booking(customers, selectedEvent, numTickets);
                    selectedEvent.bookTickets(numTickets);
                    bookingList.add(booking);
                    System.out.println("Booking successful!");

                    try (Connection conn = DBUtil.getDBConn()) {
                        String insertBookingSQL = "INSERT INTO Booking(customer_id, event_id, num_tickets, total_cost, booking_date) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement bookingStmt = conn.prepareStatement(insertBookingSQL, Statement.RETURN_GENERATED_KEYS);

                        for (Customer c : customers) {
                            PreparedStatement customerStmt = conn.prepareStatement("INSERT INTO Customer(customer_name, email, phone_number) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                            customerStmt.setString(1, c.getName());
                            customerStmt.setString(2, c.getEmail());
                            customerStmt.setString(3, c.getPhoneNumber());
                            customerStmt.executeUpdate();
                            ResultSet custKeys = customerStmt.getGeneratedKeys();
                            int custId = 0;
                            if (custKeys.next()) custId = custKeys.getInt(1);

                            bookingStmt.setInt(1, custId);
                            bookingStmt.setInt(2, eventNo); // index as ID placeholder
                            bookingStmt.setInt(3, booking.getNumTickets());
                            bookingStmt.setDouble(4, booking.getTotalCost());
                            bookingStmt.setDate(5, new java.sql.Date(booking.getBookingDate().getTime()));
                            bookingStmt.executeUpdate();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    if (bookingList.isEmpty()) {
                        System.out.println("No bookings made yet.");
                    } else {
                        for (Booking b : bookingList) {
                            b.displayBookingDetails();
                        }
                    }
                    break;

                case 4:
                    System.out.println("Thank you for using the Ticket Booking System.");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
