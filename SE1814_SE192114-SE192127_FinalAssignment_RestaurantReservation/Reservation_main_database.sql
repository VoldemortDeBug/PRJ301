-- Create the database
CREATE DATABASE PRJ301_ASSIGNMENT;
GO

-- Use the created database
USE PRJ301_ASSIGNMENT;
GO

-- Create Users table
CREATE TABLE Users (
    UserID INT PRIMARY KEY,
    Name VARCHAR(100),
    Username VARCHAR(50) UNIQUE,
    Email VARCHAR(100) UNIQUE,
    Phone VARCHAR(15),
    Password VARCHAR(100),
    Coins INT DEFAULT 0,
    Profile_Picture VARCHAR(255),
	Type VARCHAR(5)
);
GO

-- Create Restaurants table
CREATE TABLE Restaurants (
    RestaurantID INT PRIMARY KEY,
    Name VARCHAR(100),
    Location VARCHAR(255),
    OwnerID INT,
    MainPhoto VARCHAR(255),
	TotalProfit INT,
    FOREIGN KEY (OwnerID) REFERENCES Users(UserID)
);
GO

-- Create RestaurantPhoto table
CREATE TABLE RestaurantPhoto (
    PhotoID INT PRIMARY KEY,
    RestaurantID INT,
    Photo VARCHAR(255),
    FOREIGN KEY (RestaurantID) REFERENCES Restaurants(RestaurantID)
);
GO

-- Create ReservedEntities table
CREATE TABLE ReservedEntities (
    EntityID INT PRIMARY KEY,
    RestaurantID INT,
    Type VARCHAR(50),
    ActiveTill DATE,
    SeatCap INT,
    ForwardLim INT,
    Daily INT,
    Weekly INT,
    ReservationFee INT,
	TotalProfit INT,
    FOREIGN KEY (RestaurantID) REFERENCES Restaurants(RestaurantID)
);
GO

-- Create Reservations table
CREATE TABLE Reservations (
    ReserveID INT PRIMARY KEY,
    UserID INT,
    EntityID INT,
    Hours INT,
    Date DATE,
    Seats INT,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (EntityID) REFERENCES ReservedEntities(EntityID)
);
GO