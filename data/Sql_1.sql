SELECT ServiceAccount.name AS account, sid.name AS sid, IF (slc.id IS NULL OR slc.id = 0, 
nls.name, slc.name) AS legacy_circuit_id, ServiceServiceOrderAction.name AS service_order_status, 
IF(n.hasChild = 1, 'Y', 'N')  AS has_child, BusOrg.name AS account_id, 
CASE n.protection WHEN 0 THEN 'N' WHEN 1 THEN 'Y' WHEN 2 THEN 'L' ELSE 'N' END AS protection, 
n.id AS ciit_id, null AS search_p_circuit_id, 'Circuit ID' AS search_type, criteria.value AS 
search_criteria FROM (SELECT 'SNLOCAMO/SNVACAID/DWDM/10599' AS value) criteria    JOIN Circuit target ON 
(criteria.value = target.name)    JOIN CircuitSearch ON (target.id = CircuitSearch.circuitId)    
LEFT OUTER JOIN NetworkData n ON (CircuitSearch.networkId = n.id        AND n.networkSourceId IN 
('2','3','4','5','7','8','9'))   LEFT OUTER JOIN ServiceData s ON (CircuitSearch.serviceId = s.id        
AND s.serviceSourceSysNmId IN ('1','10','11','12','2','3','4','5','6','7','8','9'))   LEFT OUTER JOIN
Circuit circuitId ON (n.circuitId = circuitId.id) 