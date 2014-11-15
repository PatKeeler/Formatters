WITH simple_sum AS (SELECT trunc(calendar_date, 'mm') calendar_month_date,
claim_id, sum(amount_paid) amount_paid FROM temp_claim_history GROUP BY
trunc(calendar_date, 'mm'), claim_id), end_month AS ( SELECT trunc(sysdate, 'mm')
calendar_month_date FROM dual), itd_sum AS (SELECT simple_sum.calendar_month_date,
simple_sum.claim_id, simple_sum.amount_paid, sum(simple_sum.amount_paid) OVER (
partition by simple_sum.claim_id ORDER BY simple_sum.calendar_month_date) itd_amount_paid,
nvl ( lead(simple_sum.calendar_month_date) OVER (partition by simple_sum.claim_id
ORDER BY simple_sum.calendar_month_date ), add_months(end_month.calendar_month_date, 1))
next_calendar_month_date FROM simple_sum, end_month), month_list AS (SELECT
add_months( trunc( to_date('2010' , 'rrrr' ) , 'rrrr' ) , -rownum+1 ) some_month_date FROM
dual connect by level <= 240), dense_itd_summary AS (SELECT itd_sum.*,
month_list.some_month_date filled_calendar_month_date FROM itd_sum, month_list WHERE
month_list.some_month_date >= itd_sum.calendar_month_date AND month_list.some_month_date <
itd_sum.next_calendar_month_date);

WITH sum_sales AS ( select /*+ materialize */ sum(quantity) all_sales from stores ),
number_stores AS (select /*+ materialize */ count(*) nbr_stores from stores ),
sales_by_store AS ( select /*+ materialize */ store_name, sum(quantity) store_sales
from store natural join sales ) SELECT store_name FROM store, sum_sales, number_stores,
sales_by_store where store_sales > (all_sales / nbr_stores);