package tools.java.pats.formatters;

import tools.java.pats.formatters.Operators.Factory.OperatorsFormatterFactory;
import tools.java.pats.formatters.Operators.OperatorsFormatter;
import tools.java.pats.formatters.Operators.OverClauseFormatter;
import tools.java.pats.nodes.Node;
import tools.java.pats.string.utils.StringIndexes;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

/**
 * Created with IntelliJ IDEA.
 * User: Pat
 * Date: 3/23/13
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class MultiLineSegmentsFormatter extends Node implements Serializable {

    private static final long serialVersionUID = 1951L;

    /** Pattern to identify single line comments. */
    protected static final Pattern OVER_CLAUSE = Pattern.compile(" OVER\\s+\\(");


    /**
     * Final Argument Constructor.
     * @param recursionTab
     * @param userIndentAmount
     */
    public MultiLineSegmentsFormatter(final String recursionTab,
                                      final String userIndentAmount,
                                      final String selectedStyle) {

        super(recursionTab, userIndentAmount, selectedStyle);
    }

    /**
     * Format.
     *
     * @return String formatted line.
     */
    public StringBuffer formatMultiLineSegments(String sql) {

    StringBuffer sb = new StringBuffer();

    // Get column array.
    String[] columns = sql.split(",");

    // Join elements within parens.
    columns = joinColumsWithinParens(columns);

    //Remove extra spacing in these columns,
    // added for correctly formatting the "With" statement.
    for (int i = 0; i < columns.length; i++) {
        columns[i] = columns[i].replaceAll("(\\s)+", " ");
    }

    // Format AS Statements
    AsLinesFormatter asLines = null;
    try {
        asLines = new AsLinesFormatter(columns);
    }
    catch (Exception e) {
        e.printStackTrace();
    }
    if (asLines != null) {
        columns = asLines.formatNode().toArray(new String[0]);
    }

    // Append the formatted columns to the buffer
    for (int n = 0; n < columns.length; n++) {

        String s = columns[n];

        //Add space between IF and "(", if needed
        if (s.trim().startsWith("IF(")) {
            s = "IF " + s.trim().substring(2);
        }

        //Format CASE statements
        if (s.toUpperCase().trim().startsWith("CASE ")) {
            CaseLinesFormatter caseLines = new CaseLinesFormatter();
            sb.append(caseLines.formatNode(s, tab, userIndentTab));

            // Check for AS in last line
            String tabs = tab + userIndentTab;
            formatLastAsLine(sb, 0, tabs, asLines);

        }
        //Format IF statements
        else if (s.toUpperCase().trim().startsWith("IF ")) {
             // Format IF statements.
             IfLinesFormatter IfLines = new IfLinesFormatter();
             sb.append(IfLines.formatNode(s, tab, userIndentTab));

            // Check for AS in last line
            String tabs = tab + userIndentTab + userIndentTab;
            formatLastAsLine(sb, userIndentAmount, tabs, asLines);
        }
        //Format embedded select statements
        else if (s.toUpperCase().trim().startsWith("(")) {
            if(isEmbeddedSelect(s)) {
                StringIndexes ind = getIndexesForSqlWithinParens(s);

                String newSql = s.substring(ind.getStart(), ind.getEnd());

                if (! newSql.isEmpty()) {
                    sb.append(formatEmbeddedSelect(TWO_INDENTS, s, ind));

                    // Check for AS in last line
                    String tabs = tab + userIndentTab;
                    formatLastAsLine(sb, 0, tabs, asLines);

                    //Process remaining - if any.
                    String remaining = s.substring(ind.getEnd() + 1);
                    if (remaining.length() > 1) {

                        OperatorsFormatter formatOperators =
                                OperatorsFormatterFactory.getFormatter(
                                        ZERO_INDENTS, tab, stringIndentAmount, selectedStyle);

                        sb.append(format(" %s", formatOperators.formatOperators(
                                s.substring(ind.getEnd() + 1))));
                    }

                    //append a comma to the end.
                    if ((sb.charAt(sb.length() -1) == ' ')) {
                        sb.replace(sb.length() - 1, sb.length(), ",");
                    } else {
                        sb.append(",");
                    }
                }
            }

            //Add the column.
            else {
                sb.append(format("\n%s%s%s,", tab, userIndentTab, s.trim()));
            }
        }
        //Format OVER clause
        else {
            Matcher matcher = OVER_CLAUSE.matcher(s);

            if(matcher.find()) {
                OverClauseFormatter ocf = new OverClauseFormatter(
                        tab, stringIndentAmount, selectedStyle);

                sb.append(ocf.formatOverClause(s));

            } else {
                sb.append(format("\n%s%s%s,", tab, userIndentTab, s.trim()));
            }
        }
    }

    // Remove last comma.
    if (sb.length() > 0) {
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }
    } else {
        throw new InvalidParameterException(format(
                "Incorrect number of parenthesis in String: %s", sql));
    }
    return sb;
    }


    /**
     * Format the last AS line
     * @param sb
     * @param asLines
     */
    private void formatLastAsLine(StringBuffer sb, int spaces, String tabs, AsLinesFormatter asLines) {
        int tempIndex = sb.lastIndexOf("\n");
        if (tempIndex > 1) {
            String newStr = formatLastAsLine(tempIndex, sb, asLines, spaces);
            newStr = "\n" + tabs + newStr;
            sb.replace(tempIndex, sb.length(), newStr);
        }
    }
}
