CREATE PROCEDURE MakeReservationAndPay
    @ReserveID INT,
    @UserID INT,
    @EntityID INT,
    @Hours INT,
    @Date DATE,
    @Seats INT,
    @CoinsToDeduct INT,
    @OwnerID INT -- New parameter for the restaurant owner's ID
AS
BEGIN
    SET NOCOUNT ON; -- Improves performance by preventing extra result sets

    BEGIN TRANSACTION; -- Start a transaction to ensure atomicity

    -- Insert reservation
    INSERT INTO Reservations(ReserveID, UserID, EntityID, Hours, Date, Seats) 
    VALUES (@ReserveID, @UserID, @EntityID, @Hours, @Date, @Seats);

    -- Deduct coins from the user
    UPDATE Users
    SET Coins = Coins - @CoinsToDeduct
    WHERE UserID = @UserID;

    -- Check if coins went negative (optional rollback to prevent overdraft)
    IF (SELECT Coins FROM Users WHERE UserID = @UserID) < 0
    BEGIN
        ROLLBACK TRANSACTION;
        PRINT 'Transaction failed: Insufficient coins!';
        RETURN;
    END

    -- Add coins to the restaurant owner
    UPDATE Users
    SET Coins = Coins + @CoinsToDeduct
    WHERE UserID = @OwnerID;

	 UPDATE ReservedEntities
    SET TotalProfit = ISNULL(TotalProfit, 0) + @CoinsToDeduct
    WHERE EntityID = @EntityID;

    -- Update TotalProfit for the Restaurants
    DECLARE @RestaurantID INT;

    -- Get the RestaurantID associated with the EntityID
    SELECT @RestaurantID = RestaurantID
    FROM ReservedEntities
    WHERE EntityID = @EntityID;

    UPDATE Restaurants
    SET TotalProfit = ISNULL(TotalProfit, 0) + @CoinsToDeduct
    WHERE RestaurantID = @RestaurantID;

    COMMIT TRANSACTION; -- Commit the transaction if everything is successful
END;