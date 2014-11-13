select c.nm as name, a.add as address, (select c.id from c where c.nm = 'pat'), p.ph as phone
from customer c, address a, phone p
where c.id = a.id and c.id = p.id and c.id in (select c.id from c where c.nm = 'pat');
