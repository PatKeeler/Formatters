"insert into NWX_CHARGE_DEF (  "    + 
"CHARGE_DEF_ID,"   + 
"CHARGE_SET_ID, "    + 
"TYPE, "    + 
"PRICE, "    + 
"AG_FEE, "    + 
"UNITS, "    + 
"IS_ACTIVE, "    + 
"DESCRIPTION, "    + 
"EFFECTIVE_DATE, "    + 
"TERM_DATE, "    + 
"REPLACE_DATE, "    + 
"LAST_UPDATED_BY, "    + 
"LAST_UPDATED_TIMESTAMP ) "    + 
"values( ?, ?, ?, ?, ?, ?, ?, ?, "    + 
"TO_DATE( ?, 'mm/dd/yyyy'), "    + 
"TO_DATE(?, 'mm/dd/yyyy'), "    + 
"TO_DATE(?, 'mm/dd/yyyy'), "    + 
"?, "    + 
"SYSDATE );"