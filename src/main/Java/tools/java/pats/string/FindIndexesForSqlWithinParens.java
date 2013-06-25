package tools.java.pats.string;

import tools.java.pats.nodes.Node;
import tools.java.pats.string.utils.FindIndexOfClosingParen;
import tools.java.pats.string.utils.StringIndexes;

import java.io.Serializable;
import java.security.InvalidParameterException;

public class FindIndexesForSqlWithinParens implements Serializable {

    public FindIndexesForSqlWithinParens() {

    }

    /**
     * Get the start and end indexes of data within
     * parenthesis.
     *
     * @param sql
     * @return
     */
    public StringIndexes getIndexesForSqlWithinParens(String sql) {

        int start = -1;
        int end = 0;

        StringIndexes indexes = new StringIndexes();

        FindIndexOfClosingParen findClosingParen = new FindIndexOfClosingParen();

        for (int n = 0; n < sql.length(); n++) {

            if (sql.charAt(n) == Node.OPEN_PAREN_BYTE) {
                start = n;
                end = findClosingParen.findClosingIndex(start, sql);
                if (end > 0) {
                    indexes.setStart(start + 1);
                    indexes.setEnd(end);
                    return indexes;
                }
            }
        }

        throw new InvalidParameterException(String.format(
                "Incorrect number of parenthesis at: %s", sql));
    }
}