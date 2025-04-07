package dao;

import entity.Booking;
import entity.Driver;
import entity.Vehicle;
import myexceptions.BookingNotFoundException;
import myexceptions.VehicleNotFoundException;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransportManagementServiceImpl implements TransportManagementService {
    @Override
    public boolean addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO Vehicles (Model, Capacity, Type, Status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vehicle.getModel());
            ps.setDouble(2, vehicle.getCapacity());
            ps.setString(3, vehicle.getType());
            ps.setString(4, vehicle.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateVehicle(Vehicle vehicle) {
        String sql = "UPDATE Vehicles SET Model=?, Capacity=?, Type=?, Status=? WHERE VehicleID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vehicle.getModel());
            ps.setDouble(2, vehicle.getCapacity());
            ps.setString(3, vehicle.getType());
            ps.setString(4, vehicle.getStatus());
            ps.setInt(5, vehicle.getVehicleId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteVehicle(int vehicleId) throws VehicleNotFoundException {
        String sql = "DELETE FROM Vehicles WHERE VehicleID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, vehicleId);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new VehicleNotFoundException("Vehicle not found");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean scheduleTrip(int vehicleId, int routeId, String departureDate, String arrivalDate) {
        String sql = "INSERT INTO Trips (VehicleID, RouteID, DepartureDate, ArrivalDate, Status, TripType, MaxPassengers) VALUES (?, ?, ?, ?, 'Scheduled', 'Freight', 40)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, vehicleId);
            ps.setInt(2, routeId);
            ps.setString(3, departureDate + " 00:00:00");
            ps.setString(4, arrivalDate + " 00:00:00");
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean cancelTrip(int tripId) {
        String sql = "UPDATE Trips SET Status='Cancelled' WHERE TripID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tripId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean bookTrip(int tripId, int passengerId, String bookingDate) {
        String sql = "INSERT INTO Bookings (TripID, PassengerID, BookingDate, Status) VALUES (?, ?, ?, 'Confirmed')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tripId);
            ps.setInt(2, passengerId);
            ps.setString(3, bookingDate + " 00:00:00");
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean cancelBooking(int bookingId) throws BookingNotFoundException {
        String sql = "UPDATE Bookings SET Status='Cancelled' WHERE BookingID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new BookingNotFoundException("Booking not found");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean allocateDriver(int tripId, int driverId) {
        String sql = "UPDATE Trips SET DriverID=? WHERE TripID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, driverId);
            ps.setInt(2, tripId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deallocateDriver(int tripId) {
        String sql = "UPDATE Trips SET DriverID=NULL WHERE TripID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tripId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Booking> getBookingsByPassenger(int passengerId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM Bookings WHERE PassengerID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, passengerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Booking b = new Booking();
                b.setBookingId(rs.getInt("BookingID"));
                b.setTripId(rs.getInt("TripID"));
                b.setPassengerId(rs.getInt("PassengerID"));
                b.setBookingDate(rs.getTimestamp("BookingDate").toLocalDateTime());
                b.setStatus(rs.getString("Status"));
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Booking> getBookingsByTrip(int tripId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM Bookings WHERE TripID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tripId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Booking b = new Booking();
                b.setBookingId(rs.getInt("BookingID"));
                b.setTripId(rs.getInt("TripID"));
                b.setPassengerId(rs.getInt("PassengerID"));
                b.setBookingDate(rs.getTimestamp("BookingDate").toLocalDateTime());
                b.setStatus(rs.getString("Status"));
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Driver> getAvailableDrivers() {
        List<Driver> list = new ArrayList<>();
        String sql = "SELECT * FROM Drivers WHERE Status='Available'";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Driver d = new Driver();
                d.setDriverId(rs.getInt("DriverID"));
                d.setName(rs.getString("Name"));

                d.setStatus(rs.getString("Status"));
                list.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
