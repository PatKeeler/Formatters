package tools.java.pats.formatters;

import tools.java.pats.constants.ProjectStaticConstants;
import tools.java.pats.formatters.EmbeddedSelects.EmbeddedSelectsFormatter;
import tools.java.pats.formatters.Operators.Factory.OperatorsFormatterFactory;
import tools.java.pats.formatters.Operators.OperatorsFormatter;
import tools.java.pats.string.utils.GetStringWithinParens;
import tools.java.pats.string.utils.StringIndexes;
import tools.java.pats.string.utils.sql.CheckForEmbeddedSelect;
import tools.java.pats.string.utils.sql.RejoinComumnsWithinParens;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static tools.java.pats.formatters.EmbeddedSelects.Factory.EmbeddedSelectsFormatterFactory.getFormatter;

/**
 * Created with IntelliJ IDEA.
 * User: Pat
 * Date: 3/23/13
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class MultiLineSegmentsFormatter implements Serializable, ProjectStaticConstants {

    private static final long serialVersionUID = 1951L;

    /** Pattern to identify single line comments. */
    protected static final Pattern OVER_CLAUSE = Pattern.compile(" OVER\\s+\\(");

    /** Format variables */
    protected final String tab;
    protected final String stringIndentAmount;
    protected final String selectedStyle;

    protected final String userIndentTab;

    protected final int userIndentAmount;


    /**
     * Final Argument Constructor.
     *
     * @param recursionTab
     * @param stringIndentAmount
     * @param selectedStyle
     */
    public MultiLineSegmentsFormatter(final String recursionTab,
                                      final String stringIndentAmount,
                                      final String selectedStyle) {

        this.tab = recursionTab;
        this.stringIndentAmount = stringIndentAmount;
        this.selectedStyle = selectedStyle;

        AtomicReference<Integer> amount = new AtomicReference<Integer>(null);
              try {
                  amount.set(Integer.valueOf(this.stringIndentAmount));
              }
      		catch (NumberFormatException e) {
                  amount.set(0);
      		}
            this.userIndentAmount = amount.get().intValue();
      		this.userIndentTab = SPACES.substring(0, amount.get());

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
        RejoinComumnsWithinParens rejoin =
                new RejoinComumnsWithinParens(columns, tab);

        columns = rejoin.rejoinColumns();

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
            CheckForEmbeddedSelect cfs = new CheckForEmbeddedSelect();
            if(cfs.isEmbeddedSelect(s)) {

                GetStringWithinParens getString = new GetStringWithinParens();
                StringIndexes ind = getString.getIndexesForSqlWithinParens(s);

                String newSql = s.substring(ind.getStart(), ind.getEnd());

                if (! newSql.isEmpty()) {
                    EmbeddedSelectsFormatter esf =
                            getFormatter(TWO_INDENTS, tab, stringIndentAmount, selectedStyle);
                    sb.append(esf.formatEmbeddedSelect(s, ind));

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
            //Get the original max property length.
            int origPropLen = asLines.getMaxPropLength();

            //Get the last line in buffer
            String[] tempStr = {sb.substring(tempIndex)};

            //Re-load asLines with new string array.
            asLines = new AsLinesFormatter(tempStr);

            //Get the original max property length.
            int currentPropLen = asLines.getMaxPropLength();

            //If original max property length > current, reset to
            // to original.
            if (origPropLen > currentPropLen) {
                //Remove userIndent if required and spaces exists
                if (origPropLen - spaces > currentPropLen) {
                    asLines.setMaxPropLength(origPropLen - spaces);
                } else {
                    asLines.setMaxPropLength(origPropLen);
                }
            }

            //Format last line with AS.
            String newStr = asLines.formatNode().toString();

            //Replace sb last line with new formatted line.
            newStr = newStr.substring(1, newStr.length() - 1);

            newStr = "\n" + tabs + newStr;
            sb.replace(tempIndex, sb.length(), newStr);
        }
    }
}
