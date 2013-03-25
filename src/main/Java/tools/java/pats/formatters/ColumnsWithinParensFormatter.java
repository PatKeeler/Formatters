package tools.java.pats.formatters;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.string.utils.StringIndexes;

import java.io.Serializable;


/**
 * Format multiple column definitions that live within parens.
 *
 * Format any set of comma delimited values into proper
 * indents, each on a single line.
 */
@ThreadSafe
public class ColumnsWithinParensFormatter implements Serializable {

    private static final long serialVersionUID = 1951L;

    /**
     * Format.
     *
     * @param sql sql string
     * @param ind StringIndexes
     * @param tab recursion indent amount
     * @param sql user selected indent amount
     * 
     * @return String formatted line.
     */
    public String formatMultiColumnsWithinParens(String sql,
                                                 StringIndexes ind,
                                                 String tab,
                                                 String userIndent) {

        StringBuffer sb = new StringBuffer();

        String newSql = sql.substring(ind.getStart(), ind.getEnd());

        if (!newSql.isEmpty()) {
            //Add open paren.
            sb.append(String.format("\n%s%s%s", tab, userIndent, "("));

            //Add definitions.
            String[] columns = newSql.split(",");
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
        }

        return sb.toString();
    }
}