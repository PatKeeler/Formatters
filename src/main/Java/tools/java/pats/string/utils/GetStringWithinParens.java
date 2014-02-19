package tools.java.pats.string.utils;

import tools.java.pats.constants.ProjectStaticConstants;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 *
 * Created with IntelliJ IDEA.
 * User: Pat
 * Date: 6/21/13
 * Time: 10:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetStringWithinParens implements Serializable, ProjectStaticConstants{

    private static final long serialVersionUID = 1951L;

    
    /**
     * Default Consrtructor
     */
    public GetStringWithinParens() {

        super();
    }


    /**
     * Find the string within open and closing parens.
     *
     * @param sql input
     * @return indexes
     */
    public StringIndexes getIndexesForSqlWithinParens(String sql) {

        int start;
        int end;

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

}
