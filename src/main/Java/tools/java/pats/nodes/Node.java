package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.constants.ProjectStaticConstants;
import tools.java.pats.formatters.*;
import tools.java.pats.formatters.EmbeddedSelects.EmbeddedSelectsFormatter;
import tools.java.pats.formatters.Operators.Factory.OperatorsFormatterFactory;
import tools.java.pats.formatters.Operators.OperatorsFormatter;
import tools.java.pats.string.utils.FindIndexOfClosingParen;
import tools.java.pats.string.utils.StringIndexes;
import tools.java.pats.string.utils.sql.RejoinComumnsWithinParens;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static tools.java.pats.formatters.EmbeddedSelects.Factory.EmbeddedSelectsFormatterFactory.getFormatter;

/**
 * This class is a super class with common methods and variables
 * used by all subclasses.
 *
 * Created by IntelliJ IDEA.
 * User: Pat Keeler
 * Date: 10/8/11
 * Time: 9:32 PM
 */
@ThreadSafe
public abstract class Node implements Serializable, ProjectStaticConstants {

    /** Required serialization parameter */
    private static final long serialVersionUID = 1951L;

    // Recursive indent amount
    protected final int tabLength;
    // Spaces = to recursive indent amount
	protected final String tab;

    // int value of user indent amount
	protected final int userIndentAmount;
    // String value of user indent amount received from client
	protected final String stringIndentAmount;
    // Spaces string of user indent amount
    protected final String userIndentTab;


    /**
     * Parameter Constructor.
     *
     * @param tab = recursion indent amount
     * @param stringIndentAmount = user supplied indent amount.
     */
    public Node(final String tab, final String stringIndentAmount) {
        super();


		// Get the current indent value.
        this.tab = tab;
        this.tabLength = tab.length();

		//Get user supplied indent amount
		//save this value for recursion.
		this.stringIndentAmount = stringIndentAmount;
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
     * This method will be overridden by all Node subclasses.
     */
    public String processLine(Node node) {

        return null;
    }



    /**
     * Format multiple column definitions.
     * 
     * @param sql
     * @return String formatted.
     */
    protected String formatMultiColumnsWithinParens(String sql) {

        StringIndexes ind = getIndexesForSqlWithinParens(sql);

        ColumnsWithinParensFormatter mcf = new ColumnsWithinParensFormatter();

        return  mcf.formatMultiColumnsWithinParens(sql, ind, tab, userIndentTab);
    }

    /**
     * Format <code>Values</code> column definitions.
     *
     * @param sql
     * @return String formatted.
     */
    protected String formatValuesColumns(String sql) {

        StringIndexes ind = getIndexesForSqlWithinParens(sql);

        ValuesFormatter vf = new ValuesFormatter();

        return  vf.formatValues(sql, ind, tab, userIndentTab);
    }

    /**
     * Format the multi column nodes into single lines.
     *
     * @param sql string of column elements.
     * @return StringBuffer with formatted action statements.
     */
    protected StringBuffer getMultiLineSegments(String sql) {

        StringBuffer sb = new StringBuffer();

        MultiLineSegmentsFormatter mlsf =
                new MultiLineSegmentsFormatter(tab, stringIndentAmount);

        return mlsf.formatMultiLineSegments(sql);
    }

    /**
     * Get the last line from buffer, if it has an AS, format it.
     * 
     * @param tempInd
     * @param sb
     * @param asLines
     * @param spaces
     * @return
     */
    protected String formatLastAsLine(int tempInd, StringBuffer sb,
                                    AsLinesFormatter asLines, int spaces) {

        //Get the original max property length.
        int origPropLen = asLines.getMaxPropLength();

        //Get the last line in buffer
        String[] tempStr = {sb.substring(tempInd)};

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

        return newStr;
    }


    /**
     * Format embedded select with user indents.
     *
     * @param indent - user indent amount
     * @param sql
     * @param ind
     * @return
     */
    protected String formatEmbeddedSelect(int indent,
                                          String sql,
                                          StringIndexes ind) {

        StringBuffer sb = new StringBuffer();

        EmbeddedSelectsFormatter esf =
                getFormatter(indent, tab, stringIndentAmount);

        sb.append(esf.formatEmbeddedSelect(sql, ind));

        return sb.toString();
    }



    protected void formatMultiColumnsInINFourUserIndents(StringBuffer sb,
                                                         String sql,
                                                         StringIndexes ind) {

        String tempParenTab = SPACES.substring(0, tabLength + userIndentAmount * 3);
        String tempDataTab = SPACES.substring(0, tabLength + userIndentAmount * 4);

        //IF sql not too long just append it and return
        if (sql.length() < 40) {
            sb.append(format("%s", sql.substring(0, ind.getEnd() + 1).trim()));
            return;
        }

        //get the embedded sql to be formatted
        String newSql = sql.substring(ind.getStart(), ind.getEnd());

        sb.append(format("\n%s%s", tempParenTab, sql.substring(0,ind.getStart()).trim()));

        String[] columns = newSql.split(",");

        if (columns.length > 1) {
            for (String s : columns) {
                sb.append(format("\n%s%s,", tempDataTab, s.trim()));
            }
        } else {
            sb.append(format("\n%s%s", tempDataTab, newSql.trim()));
        }
        // Remove the last comma
        sb.replace(sb.length() - 1, sb.length(), "");

		sb.append(format("\n%s%s", tempParenTab, sql.substring(ind.getEnd(), ind.getEnd() + 1).trim()));
    }

    /**
     * boolean method to indicate if string has embedded select statement.
     *
     * @param sql
     * @return boolean true or false
     */
    protected boolean isEmbeddedSelect(String sql) {

        Pattern select = Pattern.compile("^([\\( | \\s]*SELECT)");
        Matcher m = select.matcher(sql);
        if (m.find()) {
            return true;
        }

        return false;
    }


    /**
     * Get the start and end indexes of data within
     * parenthesis.
     *
     * @param sql
     * @return
     */
    protected StringIndexes getIndexesForSqlWithinParens(String sql) {

        int start = -1;
        int end = 0;

        StringIndexes indexes = new StringIndexes();

        FindIndexOfClosingParen findClosingParen = new FindIndexOfClosingParen();

        for (int n = 0; n < sql.length(); n++) {

            if (sql.charAt(n) == OPEN_PAREN_BYTE) {
                start = n;
                end = findClosingParen.findClosingIndex(start, sql);
                if (end > 0) {
                    indexes.setStart(start + 1);
                    indexes.setEnd(end);
                    return indexes;
                }
            }
        }

        throw new InvalidParameterException(format(
                "Incorrect number of parenthesis at: %s", sql));
    }

    /**
     * Concatenate column rows that were separated by commas within parens.
     *
     * @param columns
     *            String[] array of column lines.
     * @return String[] array with all column data on same line.
     */
    protected String[] joinColumsWithinParens(String[] columns) {

        RejoinComumnsWithinParens rejoin =
                new RejoinComumnsWithinParens(columns, tab);

        return rejoin.rejoinColumns();
    }


    /**
     * Method to call the operator formatter.
     *
     * @param element - string to be modified.
     * @return String  - formatted element
     */
    protected String formatForAndOrInString(String element) {

        StringBuffer sb = new StringBuffer();
        element = element.trim();
        int indents = 2;

        OperatorsFormatter formatOperators =
                OperatorsFormatterFactory.getFormatter(indents, tab, stringIndentAmount);

        sb.append(formatOperators.formatOperators(element));

        return sb.toString();
    }

}
