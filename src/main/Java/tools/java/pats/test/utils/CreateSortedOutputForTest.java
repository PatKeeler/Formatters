package tools.java.pats.test.utils;

import static java.lang.String.format;


/**
 * Created by IntelliJ IDEA.
 * User: Pat
 * Date: 11/25/11
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class CreateSortedOutputForTest {

    /**
     * input = list of all entries in SqlNodes.java to be formatted and
     * inserted into SortCommandsByLengthTest.java.
     *
     */
    private static String input = "CREATE UNIQUE INDEX, RIGHT OUTER JOIN, " +
            "CREATE DATABASE, LEFT OUTER JOIN, SELECT DISTINCT, TRUNCATE TABLE, " +
            "DROP DATABASE, RENAME COLUMN, ALTER COLUMN, CREATE INDEX, " +
            "CREATE TABLE, ALTER TABLE, CREATE VIEW, DELETE FROM, DROP COLUMN, " +
            "INSERT INTO, DROP INDEX, INNER JOIN, RIGHT JOIN, SELECT TOP, " +
            "LEFT JOIN, RENAME TO, UNION ALL, GROUP BY, ORDER BY, BETWEEN, DELETE, " +
            "HAVING, MODIFY, SELECT, UPDATE, VALUES, UNION, WHERE, DROP, FROM, " +
            "INTO, JOIN, ADD, SET";

    private static String start = "expected.add(\"";
    private static String end = "\");";

    public static void main(String[] args) {

        for (String s : input.split(",")) {
            System.out.println(format("%s%s%s", start, s.trim(), end));
        }
    }
}
