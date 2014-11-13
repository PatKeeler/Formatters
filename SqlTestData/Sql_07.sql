declare @f int = 1644 ,@s int = 1052414 ,@dt datetime = '20140501' select c.FILID, c.CHEQUEID, c.CREATED, 
c.STORETIME, c.SUMCHEQ, c.SUMREG, c.SUMDISCOUNT from Cheques.dbo.Cheques as c with (nolock) where c.FILID = @f 
and c.CHEQUEID = @s and c.CREATED >= @dt select cl.FILID, cl.CHEQUEID, cl.CHEQUELINEID, cl.CREATED, cl.LAGERID, 
cl.KOLVO, cl.PRICEOUT from Cheques.dbo.ChequeLines as cl with (nolock) where cl.FILID = @f and cl.CHEQUEID = @s 
and cl.createdDate >= @dt go declare @f int = 1702 ,@s int = 102 ,@dt datetime = '20140501' select n.FILID, 
n.FILDOCID, n.DWHNAKLID, n.DOCTYPEID, n.[STATUS], n.DATE_CREATED, n.DATE_PROVED, n.DATE_STORNED, n.DATE_MODIFIED, 
n.SUMMA, n.SAPCOUNTER from nakl.dbo.tab_nakl as n with (nolock) where n.FILID = @f and n.FILDOCID = @s and 
n.DATE_PROVED >= @dt select nl.DWHNAKLID, nl.LAGERID, nl.KOLVO, nl.NEDOVOZ, nl.PRICEOUT, nl.NDS, nl.LINKID, 
nl.DISCOUNT from nakl.dbo.tab_nakllines as nl with (nolock) where nl.FILID = @f and nl.FILDOCID = @s and nl.DATE_PROVED 
>= @dt go declare @f int = 1702 ,@s int = 102 ,@dt datetime = '20140501' select fm.filID, fm.sourceID, fm.lagerID, 
fm.storeID, fm.operationID, fm.kolvo, fm.operationKolvo, fm.operationDate, fm.operationPriceOut from 
movement.dbo.fact_movement as fm with (nolock) where fm.filID = @f and fm.sourceID = @s and fm.operationDate >= 
@dt select fmf.filID, fmf.sourceID, fmf.lagerID, fmf.storeID, fmf.operationID, fmf.kolvo, fmf.operationKolvo, 
fmf.operationDate, fmf.priceLastIncome from movementF_prod.dbo.fact_movement_finance as fmf with (nolock) where 
fmf.filID = @f and fmf.sourceID = @s and fmf.operationDate >= @dt go