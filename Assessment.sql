CREATE DATABASE TicketBookingSystem;

USE TicketBookingSystem;

CREATE TABLE Venue (
    venue_id INT PRIMARY KEY AUTO_INCREMENT,
    venue_name VARCHAR(100),
    address VARCHAR(255)
);

CREATE TABLE Event (
    event_id INT PRIMARY KEY AUTO_INCREMENT,
    event_name VARCHAR(100),
    event_date DATE,
    event_time TIME,
    venue_id INT,
    total_seats INT,
    available_seats INT,
    ticket_price DECIMAL(10,2),
    event_type ENUM('Movie', 'Sports', 'Concert'),
    booking_id INT,
    FOREIGN KEY (venue_id) REFERENCES Venue(venue_id)
);

CREATE TABLE Customer (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(100),
    email VARCHAR(100),
    phone_number VARCHAR(15),
    booking_id INT
);

CREATE TABLE Booking (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    event_id INT,
    num_tickets INT,
    total_cost DECIMAL(10,2),
    booking_date DATE,
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
    FOREIGN KEY (event_id) REFERENCES Event(event_id)
);


-- 1. Insert at least 10 sample records into each table

INSERT INTO Venue (venue_name, address) VALUES 
('Shivaji Stadium', 'Shivaji Nagar, Pune'),
('Balgandharva Rangmandir', 'JM Road, Pune'),
('Mahalaxmi Ground', 'Kolhapur'),
('Nehru Auditorium', 'Tirumala, Nashik'),
('Aurangabad Sports Complex', 'CIDCO, Aurangabad'),
('Thane Kala Bhavan', 'Ghodbunder Road, Thane'),
('Nagpur Exhibition Center', 'Sitabuldi, Nagpur'),
('Solapur Town Hall', 'Siddheshwar Temple Road, Solapur'),
('Amravati Indoor Stadium', 'Rajapeth, Amravati'),
('Mumbai Grand Arena', 'Worli, Mumbai');


INSERT INTO Event (event_name, event_date, event_time, venue_id, total_seats, available_seats, ticket_price, event_type) VALUES 
('Shankar Mahadevan Live', '2025-05-15', '19:00:00', 1, 5000, 4200, 1800.00, 'Concert'),
('ISL Final - Mumbai City FC vs FC Goa', '2025-06-10', '18:30:00', 2, 15000, 10000, 2500.00, 'Sports'),
('Sitar & Tabla Night', '2025-04-22', '20:00:00', 3, 1000, 600, 1000.00, 'Concert'),
('Marathi Movie Premiere', '2025-03-20', '16:00:00', 4, 800, 500, 600.00, 'Movie'),
('Maharashtra Premier League Final', '2025-07-01', '17:00:00', 5, 20000, 17000, 2200.00, 'Sports'),
('Lata Mangeshkar Tribute Show', '2025-08-10', '18:00:00', 6, 3000, 2400, 1500.00, 'Concert'),
('Kabaddi League Match', '2025-09-12', '18:15:00', 7, 7000, 5500, 1300.00, 'Sports'),
('Natak: Sahi Re Sahi', '2025-10-25', '14:00:00', 8, 1000, 750, 800.00, 'Movie'),
('Comedy Night with Zakir Khan', '2025-11-18', '19:30:00', 9, 2000, 1500, 1200.00, 'Movie'),
('Sunburn Mumbai Festival', '2025-12-20', '17:00:00', 10, 10000, 8500, 3500.00, 'Concert');


INSERT INTO Customer (customer_name, email, phone_number) VALUES 
('Rajesh Patil', 'rajesh.patil@gmail.com', '9823456780'),
('Sneha Kulkarni', 'sneha.kulkarni@yahoo.com', '9172638490'),
('Amit Deshmukh', 'amit.deshmukh@rediffmail.com', '9887766554'),
('Priya Jadhav', 'priya.jadhav@gmail.com', '9090909090'),
('Sachin More', 'sachin.more@gmail.com', '9988776655'),
('Anjali Shinde', 'anjali.shinde@yahoo.com', '9134567890'),
('Nikhil Pawar', 'nikhil.pawar@gmail.com', '9023456789'),
('Pooja Naik', 'pooja.naik@hotmail.com', '9321654780'),
('Sameer Bhosale', 'sameer.bhosale@gmail.com', '9845123456'),
('Neha Khot', 'neha.khot@gmail.com', '9765432109');

INSERT INTO Booking (customer_id, event_id, num_tickets, total_cost, booking_date) VALUES 
(1, 1, 2, 3600.00, '2025-03-01'),
(2, 3, 3, 3000.00, '2025-03-02'),
(3, 5, 4, 8800.00, '2025-03-03'),
(4, 2, 2, 5000.00, '2025-03-04'),
(5, 4, 1, 600.00, '2025-03-05'),
(6, 6, 2, 3000.00, '2025-03-06'),
(7, 7, 3, 3900.00, '2025-03-07'),
(8, 8, 2, 1600.00, '2025-03-08'),
(9, 9, 4, 4800.00, '2025-03-09'),
(10, 10, 2, 7000.00, '2025-03-10');


-- 2. List all events.
SELECT * FROM Event;

-- 3. Select events with available tickets.
SELECT event_name FROM Event WHERE available_seats > 0;

-- 4. Select events with names partially matching 'cup'.
SELECT event_name FROM Event WHERE event_name LIKE '%cup%';

-- 5. Select events with ticket price between 1000 and 2500.
SELECT event_name, ticket_price FROM Event WHERE ticket_price BETWEEN 1000 AND 2500;

-- 6. Retrieve events with dates falling within a specific range.
SELECT event_name, event_date FROM Event WHERE event_date BETWEEN '2025-05-01' AND '2025-07-01';

-- 7. Retrieve events with available tickets that also have "Concert" in their name.
SELECT event_name FROM Event WHERE available_seats > 0 AND event_type = 'Concert';

-- 8. Retrieve users in batches of 5, starting from the 6th user.
SELECT * FROM Customer LIMIT 5 OFFSET 5;

-- 9. Retrieve booking details where the number of tickets booked is more than 4.
SELECT * FROM Booking WHERE num_tickets > 4;

-- 10. Retrieve customer information whose phone number ends with '000'.
SELECT * FROM Customer WHERE phone_number LIKE '%000';

-- 11. Retrieve the events in order where seat capacity is more than 15000.
SELECT event_name, total_seats FROM Event WHERE total_seats > 15000 ORDER BY total_seats DESC;

-- 12. Select events whose name does not start with 'x' or 'y'.
SELECT event_name FROM Event WHERE event_name NOT LIKE 'x%' AND event_name NOT LIKE 'y%';
