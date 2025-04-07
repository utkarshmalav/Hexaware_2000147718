package org.example;

import db.DBUtil;
import exception.InvalidEventTypeException;
import model.*;

import java.sql.*;
import java.util.*;

public class TicketManagementSystem {
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

                        PreparedStatement venueStmt = conn.prepareStatement(
                                "INSERT INTO Venue(venue_name, address) VALUES (?, ?)",
                                Statement.RETURN_GENERATED_KEYS);
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
                        } else {
                            throw new InvalidEventTypeException("Invalid event type entered.");
                        }

                        if (event != null) {
                            eventList.add(event);
                            System.out.println("Event created successfully!");
                        }
                    } catch (SQLException | InvalidEventTypeException e) {
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
                            bookingStmt.setInt(2, eventNo); // Use proper event ID if fetched from DB
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
