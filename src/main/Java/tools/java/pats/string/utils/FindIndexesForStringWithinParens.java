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
     * @param string to index within parens
     * @return StringIndexes class
     */
    public StringIndexes getIndexesForSqlWithinParens(String string) {

        int start;
        int end;

        StringIndexes indexes = new StringIndexes();

        FindIndexOfClosingParen findClosingParen = new FindIndexOfClosingParen();

        for (int n = 0; n < string.length(); n++) {
            if (string.charAt(n) == Node.OPEN_PAREN_BYTE) {
                start = n;
                end = findClosingParen.findClosingIndex(start, string);
                if (end > 0) {
                    indexes.setStart(start + 1);
                    indexes.setEnd(end);
                    return indexes;
                }
            }
        }

        throw new InvalidParameterException(String.format(
                "Incorrect number of parenthesis at: %s", string));
    }
}