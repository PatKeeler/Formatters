insert into ned.make (AsfLastModified, AsfStatus, Description, Name)
values (now(), 1, null, "Westronics"); INSERT INTO phonebook(phone, firstname, lastname,
address) VALUES('+1 123 456 7890', 'John', 'Doe', 'North America'); insert into tablename
(col1, col2) values('data1', 'data2' ); INSERT INTO table_name (column1, column2, column3)
VALUES (value1, value2, value3),(value4, value5, value6); UPDATE table_name SET column1 =
value, column2 = value2 WHERE some_column = some_value; DELETE FROM Persons WHERE
LastName='Tjessem' AND FirstName='Jakob'; SELECT column1 as one, column2 as two FROM table1
WHERE column1 BETWEEN value1 AND value2; CREATE DATABASE my_db;
CREATE TABLE Persons ( P_Id int, LastName varchar(255), FirstName varchar(255), Address
varchar(255), City varchar(255) ); CREATE TABLE departments ( department_id number(10) not null,
department_name varchar2(50) not null, CONSTRAINT departments_pk PRIMARY KEY (department_id) );
CREATE INDEX PIndex ON Persons (LastName, FirstName); DROP INDEX table_name.index_name; DROP
DATABASE database_name; create view view_name as select column_one, column_two from table_name
where condition = true