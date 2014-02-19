package tools.java.pats.formatters.EmbeddedSelects;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.models.SqlFormatter;
import tools.java.pats.nodes.Node;
import tools.java.pats.string.utils.StringIndexes;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 *
 * Created by IntelliJ IDEA.
 * User: Pat
 * Date: 12/19/11
 * Time: 8:15 PM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class EmbeddedSelectsFormatter extends Node implements Serializable {

    private static final long serialVersionUID = 1951L;

    /** User supplied indent amout for embedded selects. */
    private final int indents;

    /** Invalid indent parameter message */
    private static final String INVALID_INDENT = "Indent amount must be a value of 2 or 4";

    /** Invalid sql input message */
    private static final String INVALID_INPUT = "Sql input string cannot be null or empty";

    /** Block or expanded format style */
    private final String selectedStyle;

    /**
     * Constructor.
     *
     * @param indents - Multiple of user supplied indent.
     *
     * Fields required by Node constructor.
     * @param recursionTab - the amount of recursive indents.
     * @param userIndentAmount - the string representation of the indent
     *      amount, example "3".
     */
    public EmbeddedSelectsFormatter(final int indents,
                                    final String recursionTab,
                                    final String userIndentAmount,
                                    final String selectedStyle) {

        super(recursionTab, userIndentAmount, selectedStyle);

        this.selectedStyle = selectedStyle;

        if (indents == 2 || indents == 4) {
            this.indents = indents;
        } else {
            throw new InvalidParameterException(INVALID_INDENT);
        }
     }


    /**
     * Format embedded select with multiple of user indents.
     *
     * @param s - full string
     * @param ind - indexes of the embedded sql within parens
     * @return String - formatted embedded select
     */
    public String formatEmbeddedSelect(String s, StringIndexes ind) {

        StringBuffer sb = new StringBuffer();

		//set tabs
		String tempTab = SPACES.substring(0, tabLength + (userIndentAmount * (indents - 1)));
		String recursionTab = SPACES.substring(0, tabLength + (userIndentAmount * indents));

        //get the sql
        String newSql = s.substring(ind.getStart(), ind.getEnd());
        if (null == newSql || newSql.length() == 0) {
            throw new InvalidParameterException(INVALID_INPUT);
        }

		//Open paren
        sb.append(format("\n%s%s", tempTab, s.substring(0,ind.getStart()).trim()));

        //Format
		SqlFormatter formatter = new SqlFormatter();
		sb.append(format("\n%s", formatter.formatSql(newSql, recursionTab,
                   stringIndentAmount, selectedStyle)));

		//Close paren
		sb.append(format("\n%s%s ", tempTab, s.substring(ind.getEnd(), ind.getEnd() + 1).trim()));

        return sb.toString();
    }

}
