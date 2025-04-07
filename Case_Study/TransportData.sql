-- DROP DATABASE transportdb;

CREATE DATABASE IF NOT EXISTS transportdb;
USE transportdb;
SELECT * FROM Vehicles;
CREATE TABLE Vehicles (
    VehicleID INT AUTO_INCREMENT PRIMARY KEY,
    Model VARCHAR(50),
    Capacity DECIMAL(10,2),
    Type VARCHAR(50),
    Status VARCHAR(50)
);


CREATE TABLE Routes (
    RouteID INT AUTO_INCREMENT PRIMARY KEY,
    StartDestination VARCHAR(100),
    EndDestination VARCHAR(100),
    Distance DECIMAL(10,2)
);

CREATE TABLE Trips (
    TripID INT AUTO_INCREMENT PRIMARY KEY,
    VehicleID INT,
    RouteID INT,
    DepartureDate DATETIME,
    ArrivalDate DATETIME,
    Status VARCHAR(50),
    TripType VARCHAR(50) DEFAULT 'Freight',
    MaxPassengers INT
);

CREATE TABLE Passengers (
    PassengerID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(30),
    gender VARCHAR(10),
    age INT,
    Email VARCHAR(30) UNIQUE,
    PhoneNumber VARCHAR(15)
);

CREATE TABLE Bookings (
    BookingID INT AUTO_INCREMENT PRIMARY KEY,
    TripID INT,
    PassengerID INT,
    BookingDate DATETIME,
    Status VARCHAR(10)

);

-- 🚗 Vehicles
INSERT INTO Vehicles (Model, Capacity, Type, Status) VALUES
('Tata Ace', 750.00, 'Mini Truck', 'Available'),
('Volvo 9700', 50.00, 'Bus', 'Available'),
('Mahindra Bolero', 7.00, 'SUV', 'Maintenance'),
('Ashok Leyland Dost', 1000.00, 'Truck', 'Available'),
('Toyota Innova', 8.00, 'MUV', 'Busy');

-- 🛣️ Routes
INSERT INTO Routes (StartDestination, EndDestination, Distance) VALUES
('Mumbai', 'Pune', 150.00),
('Delhi', 'Agra', 210.00),
('Chennai', 'Bangalore', 350.00),
('Kolkata', 'Durgapur', 170.00),
('Nagpur', 'Hyderabad', 500.00);

-- 🚍 Trips
INSERT INTO Trips (VehicleID, RouteID, DepartureDate, ArrivalDate, Status, TripType, MaxPassengers) VALUES
(1, 1, '2025-04-10 08:00:00', '2025-04-10 11:00:00', 'Scheduled', 'Intercity', 50),
(2, 2, '2025-04-12 09:00:00', '2025-04-12 12:30:00', 'Scheduled', 'Tour', 8),
(3, 3, '2025-04-14 07:00:00', '2025-04-14 12:00:00', 'Delayed', 'Cargo', 1),
(4, 4, '2025-04-15 06:30:00', '2025-04-15 10:00:00', 'Completed', 'Private', 7),
(5, 5, '2025-04-16 05:00:00', '2025-04-16 13:00:00', 'Cancelled', 'Goods', 2);

-- 👤 Passengers
INSERT INTO Passengers (FirstName, gender, age, Email, PhoneNumber) VALUES
('Amit', 'Male', 29, 'amit@example.com', '9123456780'),
('Sneha', 'Female', 24, 'sneha@example.com', '9234567891'),
('Rahul', 'Male', 35, 'rahul@example.com', '9345678912'),
('Priya', 'Female', 28, 'priya@example.com', '9456789123'),
('Ravi', 'Male', 40, 'ravi@example.com', '9567891234');

-- 🎟️ Bookings
INSERT INTO Bookings (TripID, PassengerID, BookingDate, Status) VALUES
(1, 1, '2025-04-05 10:00:00', 'Confirmed'),
(2, 2, '2025-04-06 11:30:00', 'Confirmed'),
(3, 3, '2025-04-07 09:15:00', 'Pending'),
(4, 4, '2025-04-08 13:00:00', 'Cancelled'),
(5, 5, '2025-04-09 14:45:00', 'Confirmed');

