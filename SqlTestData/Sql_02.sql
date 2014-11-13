-- raw values 
SELECT DISTINCT sid, source_sys_nm, bandwidth_code, CASE WHEN x.description LIKE '%|%' THEN substring(x.description,
1,  charindex('|',  x.description) -1) ELSE x.description end as product_family FROM service_lookup_mvw AS s
WITH (NOLOCK) LEFT JOIN table_x_wfm_new_cfg x ON (x.field_name = s.product AND x.grouping = 'PRODUCT_SUBTYPE_MAPPING')
WHERE sid = ucid AND (isbill = 'Y' OR  (isbill IN  (null, 'NF') AND uss IN  ('ACTIVE', NULL)))  AND s.account_id
 NOT IN ( SELECT DISTINCT t.value FROM table_x_wfm_new_cfg t WHERE t.value = s.account_id AND t.grouping =
 'TS_INTERNAL_BUS_ORG_LIST' AND t.value IS NOT NULL ); UNION ALL SELECT CASE WHEN x.description LIKE '%|%' THEN
 substring(x.description,  1,  charindex('|',  x.description) -1) ELSE x.description END AS product_family,
  count(distinct sid) AS count FROM service_lookup_mvw AS s WITH (NOLOCK) LEFT JOIN table_x_wfm_new_cfg x ON
  (x.field_name = s.product AND x.grouping = 'PRODUCT_SUBTYPE_MAPPING') WHERE sid = ucid AND (isbill = 'Y'
  OR  (isbill IN  (null, 'NF') AND uss IN  ('ACTIVE', NULL))) AND s.account_id NOT IN  (SELECT DISTINCT t.value
 FROM table_x_wfm_new_cfg t WHERE t.value = s.account_id AND t.grouping = 'TS_INTERNAL_BUS_ORG_LIST' AND t.value
 IS NOT NULL ) GROUP BY CASE WHEN x.description LIKE '%|%' THEN substring(x.description,  1,  charindex('|',
 x.description) -1) ELSE x.description END ;
