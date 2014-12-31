SELECT  MIN(p.Price) AS SmallestOrderPrice, MAX(p.Price) AS HighestPrice, AVG(p.Price)
AS PriceAverage, CustomerName, LEN(c.Address) as LengthOfAddress, SUM(od.Quantity)
AS TotalItemsOrdered,  FORMAT(Now(),'YYYY-MM-DD') AS PerDate FROM Customers c,
Products p, OrderDetails od;