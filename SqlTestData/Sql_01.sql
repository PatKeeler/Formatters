select  count(c.Customers) as CustomerCount, min(p.Price) as SmallestOrderPrice,
mid(City,1,4) AS ShortCityName, max(p.Price) as HighestPrice, avg(p.Price) as
PriceAverage, c.Customer as CustomerName, len(c.Address) as LengthOfAddress,
sum(od.Quantity) as TotalItemsOrdered, format(Now(),'YYYY-MM-DD') as PerDate,
count(c.customers) as Customers, first(c.Customers) as FirstCustomer,
last(c.Customers) as LastCustomer, ucase(this) as THIS, lcase(THIS) as this,
round(c.TotalOrders) as CustomerTotalOrders, now() as DateTime1, current_timestamp()
as DateTime2, current_Date() as Date1, current_time() as time1 from Customers c,
Products p, OrderDetails od;