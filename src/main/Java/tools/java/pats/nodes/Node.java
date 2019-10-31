package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.java.pats.constants.ProjectStaticConstants;
import tools.java.pats.formatters.ColumnsWithinParensFormatter;
import tools.java.pats.formatters.EmbeddedSelects.EmbeddedSelectsFormatter;
import tools.java.pats.formatters.MultiLineSegmentsFormatter;
import tools.java.pats.formatters.Operators.Factory.OperatorsFormatterFactory;
import tools.java.pats.formatters.Operators.OperatorsFormatter;
import tools.java.pats.formatters.ValuesFormatter;
import tools.java.pats.string.utils.FindIndexesForStringWithinParens;
import tools.java.pats.string.utils.StringIndexes;
import tools.java.pats.string.utils.sql.RejoinColumnsWithinParens;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
public class Node implements Query, Serializable, ProjectStaticConstants {


    private static final Logger logger = LoggerFactory.getLogger("SqlFormatter");


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

    // boolean indicator of expanded or block style
    // block style = true
    // expanded style = false
    protected final boolean block;

    // Selected format style
    protected final String selectedStyle;


    /**
     * Parameter Constructor.
     *
     * @param tab = recursion indent amount
     * @param stringIndentAmount = user supplied indent amount.
     * @param selectedStyle = user supplied formatting style.
     */
    public Node(final String tab,
                final String stringIndentAmount,
                final String selectedStyle) {

        super();


		// Get the current indent value.
        this.tab = tab;
        this.tabLength = tab.length();

        this.selectedStyle = selectedStyle;
        if (selectedStyle.equals("block")) {
            this.block = true;
        } else {
            this.block = false;
        }

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
     * The method all subclasses must over ride.
     *
     * @param node
     * @return
     */
    @Override
    public String processLine(Query node) {
        return null;
    }


    /**
     * Format multiple column definitions.
     * 
     * @param sql un-formatted sql
     * @return formatted sql string
     */
    protected String formatMultiColumnsWithinParens(String sql) {

        FindIndexesForStringWithinParens findIndexes = new FindIndexesForStringWithinParens();

        StringIndexes ind = findIndexes.getIndexesForSqlWithinParens(sql);

        ColumnsWithinParensFormatter mcf = new ColumnsWithinParensFormatter();

        return  mcf.formatMultiColumnsWithinParens(sql, ind, tab, userIndentTab);
    }

    /**
     * Format <code>Values</code> column definitions.
     *
     * @param sql un-formatted sql
     * @return formatted sql string
     */
    protected String formatValuesColumns(String sql) {

        ValuesFormatter vf = new ValuesFormatter();

        return  vf.formatValues(sql, tab, userIndentTab);
    }

    /**
     * Format the multi column nodes into single lines.
     *
     * @param sql un-formatted sql
     * @return StringBuffer - formatted sql
     */
    protected StringBuffer getMultiLineSegments(String sql) {

        MultiLineSegmentsFormatter mlsf =
                new MultiLineSegmentsFormatter(tab, stringIndentAmount, selectedStyle);

        return mlsf.formatMultiLineSegments(sql);
    }


    /**
     * Format embedded select with user indents.
     *
     * @param indent - user indent amount
     * @param ind - StringIndexes class
     * @param sql un-formatted sql
     * @return formatted sql Select string
     */
    protected String formatEmbeddedSelect(int indent,
                                          String sql,
                                          StringIndexes ind) {

        StringBuffer sb = new StringBuffer();

        EmbeddedSelectsFormatter esf =
                getFormatter(indent, tab, stringIndentAmount, selectedStyle);

        sb.append(esf.formatEmbeddedSelect(sql, ind));

        return sb.toString();
    }

    /**
     * Concatenate column rows that were separated by commas within parens.
     *
     * @param columns - String[] array of column lines.
     * @return String[] array with all column data on same line.
     */
    public List<String> joinColumsWithinParens(List<String> columns) {

        RejoinColumnsWithinParens rejoin =
                new RejoinColumnsWithinParens(columns, tab);

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
                OperatorsFormatterFactory.getFormatter(indents, tab, stringIndentAmount, selectedStyle);

        sb.append(formatOperators.formatOperators(element));

        return sb.toString();
    }


    protected void printCmdData(String tab, String cmd, String data) {

        logger.info(format("   %s%s%s", tab, cmd, data));
    }
}
