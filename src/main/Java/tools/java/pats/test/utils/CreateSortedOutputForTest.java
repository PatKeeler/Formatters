package tools.java.pats.test.utils;

import tools.java.pats.string.utils.sql.SortCommandsByLength;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;


/**
 *
 * Created by IntelliJ IDEA.
 * User: Pat
 * Date: 11/25/11
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class CreateSortedOutputForTest {

    public static void main(String[] args) {

        /**
         * input = list of all entries in SqlNodes.java to be formatted and
         * inserted into SortCommandsByLengthTest.java.
         *
         */
        String input = "ADD, ALTER COLUMN, ALTER FUNCTION, ALTER TABLE, BETWEEN, " +
                "COMMENT, CREATE DATABASE, CREATE INDEX, CREATE UNIQUE INDEX, " +
                "CREATE TABLE, CREATE VIEW, CROSS JOIN, DECLARE, DELETE, DELETE FROM, " +
                "DROP, DROP COLUMN, DROP DATABASE, DROP INDEX, FROM, FULL JOIN, " +
                "FULL OUTER JOIN, GROUP BY, HAVING, INSERT INTO, INNER JOIN, INTO, " +
                "JOIN, LEFT JOIN, LEFT OUTER JOIN, MODIFY, NATURAL JOIN, ORDER BY, " +
                "RENAME COLUMN, RENAME TO, RIGHT JOIN, RIGHT OUTER JOIN, SELECT, " +
                "SELECT DISTINCT, SELECT TOP, SET, TRUNCATE TABLE, UNION, UNION ALL, " +
                "UPDATE, VALUES, WHERE, WITH";

        String start = "expected.add(\"";
        String end = "\");";
        int count = 0;

        //Get List of commands sorted by length.
        List<String> sqlCommands = Arrays.asList(input.split(","));
        Collections.sort(sqlCommands, new SortCommandsByLength());


        for (String sql : sqlCommands) {
            System.out.println(format("%s%s%s", start, sql.trim(), end));
            count++;
        }

        System.out.println("count: " + count);
    }
}
