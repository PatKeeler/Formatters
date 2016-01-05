declare @f int = 1644 ,@s int = 1052414 ,@dt datetime = '20140501' select c.FILID, c.CHEQUEID, c.CREATED, 
c.STORETIME, c.SUMCHEQ, c.SUMREG, c.SUMDISCOUNT from Cheques.dbo.Cheques as c with (nolock) where c.FILID = @f 
and c.CHEQUEID = @s and c.CREATED >= @dt; select cl.FILID, cl.CHEQUEID, cl.CHEQUELINEID, cl.CREATED, cl.LAGERID,
cl.KOLVO, cl.PRICEOUT from Cheques.dbo.ChequeLines as cl with (nolock) where cl.FILID = @f and cl.CHEQUEID = @s 
and cl.createdDate >= @dt go;