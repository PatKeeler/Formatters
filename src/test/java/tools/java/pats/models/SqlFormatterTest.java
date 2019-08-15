package tools.java.pats.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

import static java.lang.String.format;

/**
 * Created by Pat on 7/11/2016.
 */
public class SqlFormatterTest {

    private static final Logger logger = LoggerFactory.getLogger("SqlFormatter");

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

        String formattedSql = formatter.formatSql(inputSql, "", "2", "block");

        assertEquals("Sql is not formatted correctly!", expectedSql, formattedSql);

        logger.info("Formatted Sql: ");
        List<String> myList = Arrays.asList(formattedSql.split("\\n"));
        for (String s : myList) {
            logger.info(format("   %s", s));
        }
        logger.info("");
    }

}
