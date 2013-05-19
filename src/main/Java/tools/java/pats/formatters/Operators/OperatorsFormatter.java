package tools.java.pats.formatters.Operators;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.nodes.Node;
import tools.java.pats.string.utils.StringIndexes;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * User: Pat
 * Date: 4/12/12
 * Time: 9:25 PM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class OperatorsFormatter extends Node implements Serializable {

    private static final long serialVersionUID = 1951L;

    /** User supplied number of indents */
    private final int extraIndent;

    /** Invalid indent parameter message */
    private static String INVALID_INDENT = "Indent amount must be a value between 0 and 9";


    /**
     * Final Argument Constructor.
     *
     * @param recursionTab
     * @param userIndentAmount
     */
    public OperatorsFormatter(final int indents,
                              final String recursionTab,
                              final String userIndentAmount,
                              final String selectedStyle) {

        super(recursionTab, userIndentAmount, selectedStyle);

        //Verify indents input
        try {
            if (indents < Node.ZERO_INDENTS || indents > Node.NINE_INDENTS) {
                throw new InvalidParameterException(INVALID_INDENT);
            }
        } catch (Exception e) {
            throw new InvalidParameterException(e.getLocalizedMessage());
        }
        
        this.extraIndent = indents;
    }


    /**
     * Format the following in this routine:
     *  AND,
     *  OR,
     *  IN,
     *  NOT IN,
     *  ON,
     *  EXISTS,
     *  NOT EXISTS.
     */

    public String formatOperators(String data) {

        StringBuffer sb = new StringBuffer();
        String myData = data.trim();

        String twoTabs = SPACES.substring(0, userIndentAmount * 2);
        String threeTabs = SPACES.substring(0, userIndentAmount * 3);

        String extraTabs = SPACES.substring(0, userIndentAmount * extraIndent);

        int index = 0;

        //TODO  Refactor this if statement
        for (int i = 0; i < myData.length(); i++) {
            if (i + 11 < myData.length()) {
                if (myData.substring(i, i + 5).equals(" AND ")) {
                    if (block) {
                        sb.append(format("\n%s%s%sAND ", tab, userIndentTab, extraTabs));
                        i = i + 4;
                    } else {
                    sb.append(format("\n%s%s%sAND\n%s%s%s", tab, twoTabs, extraTabs,
                            tab, userIndentTab, extraTabs));
                    i = i + 4;
                    }
                } else if (myData.substring(i, i + 4).equals(" OR ")) {
                    if (block) {
                        sb.append(format("\n%s%s%sOR  ", tab, userIndentTab, extraTabs));
                        i = i + 3;
                    } else {
                    sb.append(format("\n%s%s%sOR\n%s%s%s", tab, twoTabs, extraTabs,
                             tab, userIndentTab, extraTabs));
                    i = i + 3;
                    }
                } else if (myData.substring(i, i + 4).equals(" ON ")) {
                    sb.append(format("\n%s%sON ", tab, threeTabs));
                    i = i + 3;
                } else if (myData.substring(i, i + 4).equals(" IN ")) {
                    sb.append(format("\n%s%sIN ", tab, threeTabs));
                    i = i + 3;
                    //should be open paren,
                    // there may be a comment, bump past it.
                    index = myData.indexOf("(", i);
                    if (index > i) {
                        sb.append((format(myData.substring(i, index))));
                        i = index;
                    }
                    String sql = myData.substring(i, myData.length());
                    StringIndexes ind = getIndexesForSqlWithinParens(sql);
                    if (isEmbeddedSelect(myData.substring(i, myData.length()))) {
                        sb.append(formatEmbeddedSelect(FOUR_INDENTS, sql, ind));
                    } else {
                        formatMultiColumnsInINFourUserIndents(sb, sql, ind);
                    }
                    i = i + ind.getEnd();
                } else if (myData.substring(i, i + 8).equals(" NOT IN ")) {
                    sb.append(format("\n%s%sNOT IN ", tab, threeTabs));
                    i = i + 7;
                    //should be open paren,
                    // there may be a comment, bump past it.
                    index = myData.indexOf("(", i);
                    if (index > i) {
                        sb.append((format(myData.substring(i, index))));
                        i = index;
                    }
                    String sql = myData.substring(i, myData.length());
                    StringIndexes ind = getIndexesForSqlWithinParens(sql);
                    if (isEmbeddedSelect(myData.substring(i, myData.length()))) {
                        sb.append(formatEmbeddedSelect(FOUR_INDENTS, sql, ind));
                    } else {
                        formatMultiColumnsInINFourUserIndents(sb, sql, ind);
                    }
                    i = i + ind.getEnd();
                } else if (myData.substring(i, i + 7).equals("EXISTS ")) {
                    if (block) {
                        sb.append("EXISTS");
                    } else {
                        sb.append(format("%sEXISTS", twoTabs));
                    }
                    i = i + 6;
                    //should be open paren,
                    // there may be a comment, bump past it.
                    index = myData.indexOf("(", i);
                    if (index > i) {
                        sb.append((format(myData.substring(i, index))));
                        i = index;
                    }
                    if (isEmbeddedSelect(myData.substring(i, myData.length()))) {
                        String sql = myData.substring(i, myData.length());
                        StringIndexes ind = getIndexesForSqlWithinParens(sql);
                        sb.append(formatEmbeddedSelect(FOUR_INDENTS, sql, ind));
                        i = i + ind.getEnd();
                    }
                } else if (myData.substring(i, i + 11).equals("NOT EXISTS ")) {
                    if (block) {
                        sb.append("NOT EXISTS");
                    } else {
                        sb.append(format("%sNOT EXISTS", twoTabs));
                    }
                    i = i + 10;
                    //should be open paren,
                    // there may be a comment, bump past it.
                    index = myData.indexOf("(", i);
                    if (index > i) {
                        sb.append((format(myData.substring(i, index))));
                        i = index;
                    }
                    if (isEmbeddedSelect(myData.substring(i, myData.length()))) {
                        String sql = myData.substring(i, myData.length());
                        StringIndexes ind = getIndexesForSqlWithinParens(sql);
                        sb.append(formatEmbeddedSelect(FOUR_INDENTS, sql, ind));
                        i = i + ind.getEnd();
                    }
                } else {
                    sb.append(format("%s", myData.substring(i, i + 1)));
                }
            } else {
                sb.append(format("%s", myData.substring(i, i + 1)));
            }
        }

        return sb.toString();

    }
}
