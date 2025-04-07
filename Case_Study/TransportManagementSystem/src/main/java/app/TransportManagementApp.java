package app;

import dao.TransportManagementService;
import dao.TransportManagementServiceImpl;
import entity.Vehicle;
import myexceptions.BookingNotFoundException;
import myexceptions.VehicleNotFoundException;

import java.util.Scanner;

public class TransportManagementApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TransportManagementService service = new TransportManagementServiceImpl();

        while (true) {
            System.out.println("\n===== Transport Management System =====");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Update Vehicle");
            System.out.println("3. Delete Vehicle");
            System.out.println("4. Schedule Trip");
            System.out.println("5. Cancel Trip");
            System.out.println("6. Book Trip");
            System.out.println("7. Cancel Booking");
            System.out.println("8. Allocate Driver");
            System.out.println("9. Deallocate Driver");
            System.out.println("10. Get Bookings by Passenger");
            System.out.println("11. Get Bookings by Trip");
            System.out.println("12. Get Available Drivers");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter model: ");
                        String model = sc.next();
                        System.out.print("Enter capacity: ");
                        double capacity = sc.nextDouble();
                        System.out.print("Enter type: ");
                        String type = sc.next();
                        System.out.print("Enter status: ");
                        String status = sc.next();
                        Vehicle v = new Vehicle(0, model, capacity, type, status);
                        System.out.println(service.addVehicle(v) ? "Vehicle added." : "Failed to add.");
                        break;
                    case 2:
                        System.out.print("Enter vehicle ID: ");
                        int vid = sc.nextInt();
                        System.out.print("Enter model: ");
                        model = sc.next();
                        System.out.print("Enter capacity: ");
                        capacity = sc.nextDouble();
                        System.out.print("Enter type: ");
                        type = sc.next();
                        System.out.print("Enter status: ");
                        status = sc.next();
                        v = new Vehicle(vid, model, capacity, type, status);
                        System.out.println(service.updateVehicle(v) ? "Updated." : "Failed to update.");
                        break;
                    case 3:
                        System.out.print("Enter vehicle ID: ");
                        vid = sc.nextInt();
                        System.out.println(service.deleteVehicle(vid) ? "Deleted." : "Not found.");
                        break;
                    case 4:
                        System.out.print("Vehicle ID: ");
                        vid = sc.nextInt();
                        System.out.print("Route ID: ");
                        int rid = sc.nextInt();
                        System.out.print("Departure Date (YYYY-MM-DD): ");
                        String dep = sc.next();
                        System.out.print("Arrival Date (YYYY-MM-DD): ");
                        String arr = sc.next();
                        System.out.println(service.scheduleTrip(vid, rid, dep, arr) ? "Trip scheduled." : "Failed.");
                        break;
                    case 5:
                        System.out.print("Trip ID: ");
                        int tid = sc.nextInt();
                        System.out.println(service.cancelTrip(tid) ? "Cancelled." : "Failed.");
                        break;
                    case 6:
                        System.out.print("Trip ID: ");
                        tid = sc.nextInt();
                        System.out.print("Passenger ID: ");
                        int pid = sc.nextInt();
                        System.out.print("Booking Date (YYYY-MM-DD): ");
                        String bdate = sc.next();
                        System.out.println(service.bookTrip(tid, pid, bdate) ? "Booked." : "Failed.");
                        break;
                    case 7:
                        System.out.print("Booking ID: ");
                        int bid = sc.nextInt();
                        System.out.println(service.cancelBooking(bid) ? "Cancelled." : "Failed.");
                        break;
                    case 8:
                        System.out.print("Trip ID: ");
                        tid = sc.nextInt();
                        System.out.print("Driver ID: ");
                        int did = sc.nextInt();
                        System.out.println(service.allocateDriver(tid, did) ? "Allocated." : "Failed.");
                        break;
                    case 9:
                        System.out.print("Trip ID: ");
                        tid = sc.nextInt();
                        System.out.println(service.deallocateDriver(tid) ? "Deallocated." : "Failed.");
                        break;
                    case 10:
                        System.out.print("Passenger ID: ");
                        pid = sc.nextInt();
                        service.getBookingsByPassenger(pid).forEach(System.out::println);
                        break;
                    case 11:
                        System.out.print("Trip ID: ");
                        tid = sc.nextInt();
                        service.getBookingsByTrip(tid).forEach(System.out::println);
                        break;
                    case 12:
                        service.getAvailableDrivers().forEach(System.out::println);
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (VehicleNotFoundException | BookingNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }
}
