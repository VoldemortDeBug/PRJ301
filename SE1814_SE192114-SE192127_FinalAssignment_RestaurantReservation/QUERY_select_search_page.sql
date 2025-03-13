SELECT * 
FROM (				
	SELECT r.* FROM [Restaurants]  r  
	JOIN ReservedEntities re 
	ON r.RestaurantID = re.RestaurantID 
	WHERE r.Name LIKE '%a%'
	AND re.ActiveTill > GETDATE() 
	GROUP BY r.RestaurantID, r.Name, r.Location, r.OwnerID, r.MainPhoto, r.TotalProfit
	HAVING COUNT(re.EntityID) > 0 
) as r
ORDER BY r.RestaurantID 
OFFSET 0 ROWS 
FETCH NEXT 2 ROWS ONLY