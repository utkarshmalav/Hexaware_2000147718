package dao;

import entity.Booking;
import entity.Driver;
import entity.Vehicle;
import myexceptions.BookingNotFoundException;
import myexceptions.VehicleNotFoundException;

import java.util.List;

public interface TransportManagementService {
    boolean addVehicle(Vehicle vehicle);
    boolean updateVehicle(Vehicle vehicle);
    boolean deleteVehicle(int vehicleId) throws VehicleNotFoundException;

    boolean scheduleTrip(int vehicleId, int routeId, String departureDate, String arrivalDate);
    boolean cancelTrip(int tripId);

    boolean bookTrip(int tripId, int passengerId, String bookingDate);
    boolean cancelBooking(int bookingId) throws BookingNotFoundException;

    boolean allocateDriver(int tripId, int driverId);
    boolean deallocateDriver(int tripId);

    List<Booking> getBookingsByPassenger(int passengerId);
    List<Booking> getBookingsByTrip(int tripId);
    List<Driver> getAvailableDrivers();
}