package tools.java.pats.models;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Pat on 7/11/2016.
 */
public class SqlFormatterTest {

    @Test
    public void testFormatter() throws ParserConfigurationException, TransformerException, SAXException, IOException {

        String inputSql = "select sdb.this as dis, odb.that as dat from someDB sdb, otherDB odb where you != me";

        String expectedSql =
                "SELECT\n" +
                "  sdb.this      AS dis,\n" +
                "  odb.that      AS dat\n" +
                "FROM\n" +
                "  someDB sdb,\n" +
                "  otherDB odb\n" +
                "WHERE\n" +
                "  you != me";

        SqlFormatter formatter = new SqlFormatter();

//        String actual = formatter.formatSql(inputSql,"", "2", "block");

//        System.out.println(actual);
        assertEquals("Sql is not formatted correctly!", expectedSql, formatter.formatSql(inputSql,"", "2", "block"));
    }

}
