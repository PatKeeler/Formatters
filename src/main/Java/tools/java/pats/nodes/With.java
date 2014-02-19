package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.formatters.Operators.Factory.OperatorsFormatterFactory;
import tools.java.pats.formatters.Operators.OperatorsFormatter;
import tools.java.pats.string.utils.FindIndexesForStringWithinParens;
import tools.java.pats.string.utils.StringIndexes;
import tools.java.pats.string.utils.sql.CheckForEmbeddedSelect;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

/**
 *
 * Created by IntelliJ IDEA.
 * User: Pat
 * Date: 7/7/12
 * Time: 6:36 AM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class With extends Node implements Query, Serializable {

    private static final long serialVersionUID = 1951L;

    private final String cmd;
    private final String data;


    /**
     * Final Argument Constructor.
     *
     * @param cmd - sql command name
     * @param data - sql arguments for command
     * @param recursionTab - number of user indents
     * @param userIndentAmount - length of user supplied indents
     * @param selectedStyle - block or expanded
     */
    public With(final String cmd,
                final String data,
                final String recursionTab,
                final String userIndentAmount,
                final String selectedStyle) {

        super(recursionTab, userIndentAmount, selectedStyle);

        this.cmd = cmd;
        this.data = data;

        if (cmd.isEmpty()) {
            throw new InvalidParameterException(INVALID_WITH_CMD);
        }

        if (data.isEmpty()) {
            throw new InvalidParameterException(INVALID_WITH_DATA);
        }
    }


    /**
     * Overidden method.
     *
     * @param node - Class type
     * @return formatted sql string
     */
    public String processLine(Query node) {

        StringBuffer sb = new StringBuffer();

        sb.append(format("\n%s%s", tab, cmd.trim()));

        sb.append(formatWithStatements(data.trim()));

        return sb.toString();
    }


    /**
     * Format and return the WITH statements.
     *
     * @param sql - un-formatted sql
     * @return formatted sql string
     */
    public String formatWithStatements(String sql) {

        StringBuffer sb = new StringBuffer();

        // Get column array.
        List<String> columns = Arrays.asList(sql.split(","));

        // Join elements within parens.
        columns = joinColumsWithinParens(columns);

        // Append the formatted columns to the buffer
        for (String s : columns) {
            //Find closing paren and append to it
            int index = (s.indexOf("("));
            if (index == -1) {
                //append PARTITION BY on new line
                sb.append(format("\n%s%s%s%s%s", tab, userIndentTab, userIndentTab,
                        userIndentTab, s.trim()));
            } else {
                sb.append(format("\n%s%s%s", tab, userIndentTab, s.substring(0, index)));
                //bump s to closing paren
                s = s.substring(index);


                //Format embedded select statements
                CheckForEmbeddedSelect cfs = new CheckForEmbeddedSelect();
                if (cfs.isEmbeddedSelect(s)) {
                    FindIndexesForStringWithinParens findIndexes = new FindIndexesForStringWithinParens();
                    StringIndexes ind = findIndexes.getIndexesForSqlWithinParens(s);

                    sb.append(formatEmbeddedSelect(TWO_INDENTS, s, ind));

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
                    if ((sb.charAt(sb.length() - 1) == ' ')) {
                        sb.replace(sb.length() - 1, sb.length(), ",");
                    } else {
                        sb.append(",");
                    }
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
        return sb.toString();
    }

}
