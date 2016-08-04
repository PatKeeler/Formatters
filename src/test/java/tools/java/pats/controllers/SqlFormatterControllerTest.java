package tools.java.pats.controllers;

import org.springframework.mock.web.MockHttpServletRequest;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import tools.java.pats.models.SqlFormatter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Pat on 7/11/2016.
 */
public class SqlFormatterControllerTest {

    @Test
    public void testFormatter() throws ParserConfigurationException, TransformerException, SAXException, IOException, ServletException {

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

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("indentAmount", "2");
        request.addParameter("inputSQL", "inputSql");
        request.addParameter("selectedStyle", "block");

        SqlFormatterController formatter = new SqlFormatterController();
        Assert.assertTrue("Sql is not formatted correctly!", expectedSql, formatter.doPost(request, null));
    }

}
