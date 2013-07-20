package tools.java.pats.string.utils;

import tools.java.pats.nodes.Node;

import java.io.Serializable;
import java.security.InvalidParameterException;

public class FindIndexesForStringWithinParens implements Serializable {

    public FindIndexesForStringWithinParens() {

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