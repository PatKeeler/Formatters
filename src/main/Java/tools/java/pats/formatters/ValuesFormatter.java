package tools.java.pats.formatters;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.constants.ProjectStaticConstants;
import tools.java.pats.string.utils.sql.RejoinComumnsWithinParens;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Pat
 * Date: 12/28/11
 * Time: 9:52 AM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class ValuesFormatter implements Serializable, ProjectStaticConstants {

    private static final long serialVersionUID = 1951L;

    /**
     * Format single or multiple Values columns.
     * <p/>
     * Format any set of comma delimited values into proper
     * indents, each on a single line.
     *
     * @param sql        sql string
     * @param tab        recursion indent amount
     * @param userIndent user selected indent amount
     * @return String formatted line.
     */
    public String formatValues(String sql,
                               String tab,
                               String userIndent) {

        StringBuffer sb = new StringBuffer();

        boolean semi_colon = false;

        if (!sql.isEmpty()) {

            //trim the input.
            sql = sql.trim();

            //check for semi_colon on input
            if (sql.endsWith(SEMI_COLON)) {
                semi_colon = true;
                sql = sql.substring(0, sql.length() - 1);
            }

            //Add open paren.
            sb.append(String.format("\n%s%s%s", tab, userIndent, "("));

            //get columns (one or more).
            String[] columns = splitAndCleanColumns(sql, tab);

            for (String s : columns) {
                sb.append(String.format("\n%s%s%s%s%s", tab, userIndent,
                        userIndent, s.trim(), ","));
            }
            //Remove last comma
            if (sb.charAt(sb.length() - 1) == ',') {
                sb.deleteCharAt(sb.length() - 1);
            }
            //Add closing paren.
            sb.append(String.format("\n%s%s%s", tab, userIndent, ")"));

            if (semi_colon) {
                sb.append(SEMI_COLON);
            }
        }

        return sb.toString();
    }

    /**
     * Split the values columns by "),(" variations.
     * Then clean the columns up by adding in "(" and ")"
     * as needed.
     *
     * @param sql
     * @param tab
     * @return String array of value columnts
     */
    private String[] splitAndCleanColumns(String sql, String tab) {

        List<String> columnsOut = new ArrayList<String>();

        String[] columnsIn = sql.split("\\)\\s?,\\s?\\(");

        //TODO if columnsIn count = 1 split on comma and rejoin values within parens.
        if (columnsIn.length == 1) {

            if (columnsIn[0].startsWith("(")) {
                columnsIn[0] = columnsIn[0].substring(1, columnsIn[0].length());
            }

            if (columnsIn[0].endsWith(")")) {
                columnsIn[0] = columnsIn[0].substring(0, columnsIn[0].length() - 1);
            }

            if (columnsIn[0].length() > 30) {

                columnsIn = columnsIn[0].split(",");

                RejoinComumnsWithinParens rejoin =
                        new RejoinComumnsWithinParens(columnsIn, tab);

                columnsIn = rejoin.rejoinColumns();
            }
        } else {
            //Add parens back that were split on.
            for (int i = 0; i < columnsIn.length; i++) {

                //add open paren if needed
                if (!columnsIn[i].startsWith(OPEN_PAREN_STRING)) {
                    columnsIn[i] = OPEN_PAREN_STRING + columnsIn[i];
                }

                //add closing paren if needed
                if (!columnsIn[i].endsWith(CLOSING_PAREN_STRING)) {
                    //could be a semi-colon
                    if (!columnsIn[i].endsWith(SEMI_COLON)) {
                        columnsIn[i] += CLOSING_PAREN_STRING;
                    }
                }
            }
        }

        //Add all columns to output
        for (String s : columnsIn) {

            columnsOut.add(s);
        }

        return columnsOut.toArray(new String[columnsOut.size()]);
    }
}
