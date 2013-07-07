package tools.java.pats.formatters.Operators;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.constants.ProjectStaticConstants;
import tools.java.pats.string.utils.sql.CheckForEmbeddedSelect;
import tools.java.pats.formatters.EmbeddedSelects.EmbeddedSelectsFormatter;
import tools.java.pats.formatters.EmbeddedSelects.Factory.EmbeddedSelectsFormatterFactory;
import tools.java.pats.nodes.Node;
import tools.java.pats.string.utils.FindIndexesForStringWithinParens;
import tools.java.pats.string.utils.GetStringWithinParens;
import tools.java.pats.string.utils.StringIndexes;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.String.format;
import static tools.java.pats.formatters.EmbeddedSelects.Factory.EmbeddedSelectsFormatterFactory.getFormatter;

/**
 * Created by IntelliJ IDEA.
 * User: Pat
 * Date: 4/12/12
 * Time: 9:25 PM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class OperatorsFormatter implements Serializable, ProjectStaticConstants {

    private static final long serialVersionUID = 1951L;

    /** User supplied number of indents */
    private final int extraIndent;

    /** Invalid indent parameter message */
    private static String INVALID_INDENT = "Indent amount must be a value between 0 and 9";


    /** Format variables */
    protected final String tab;
    protected final int tabLength;
    protected final String stringIndentAmount;
    protected final String selectedStyle;
    protected final boolean block;

    protected final String userIndentTab;

    protected final int userIndentAmount;


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

        this.tab = recursionTab;
        this.tabLength = tab.length();

        this.stringIndentAmount = userIndentAmount;

        this.selectedStyle = selectedStyle;
        if (selectedStyle.equals("block")) {
            this.block = true;
        } else {
            this.block = false;
        }

        //Verify indents input
        try {
            if (indents < Node.ZERO_INDENTS || indents > Node.NINE_INDENTS) {
                throw new InvalidParameterException(INVALID_INDENT);
            }
        } catch (Exception e) {
            throw new InvalidParameterException(e.getLocalizedMessage());
        }
        
        this.extraIndent = indents;

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

        CheckForEmbeddedSelect cfs = new CheckForEmbeddedSelect();

        FindIndexesForStringWithinParens findIndexes = new FindIndexesForStringWithinParens();

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
                    GetStringWithinParens getString = new GetStringWithinParens();
                    StringIndexes ind = getString.getIndexesForSqlWithinParens(sql);
                    if (cfs.isEmbeddedSelect(myData.substring(i, myData.length()))) {
                        EmbeddedSelectsFormatter esf =
                            getFormatter(TWO_INDENTS, tab, stringIndentAmount, selectedStyle);
                        sb.append(esf.formatEmbeddedSelect(sql, ind));
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
                    StringIndexes ind = findIndexes.getIndexesForSqlWithinParens(sql);
                    EmbeddedSelectsFormatter esf =
                            EmbeddedSelectsFormatterFactory.getFormatter(
                                    FOUR_INDENTS, tab,stringIndentAmount,selectedStyle);
                    if (cfs.isEmbeddedSelect(myData.substring(i, myData.length()))) {
                        sb.append(esf.formatEmbeddedSelect(sql, ind));
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
                    if (cfs.isEmbeddedSelect(myData.substring(i, myData.length()))) {
                        String sql = myData.substring(i, myData.length());
                        StringIndexes ind = findIndexes.getIndexesForSqlWithinParens(sql);
                        EmbeddedSelectsFormatter esf =
                                EmbeddedSelectsFormatterFactory.getFormatter(
                                        FOUR_INDENTS, tab,stringIndentAmount,selectedStyle);
                        sb.append(esf.formatEmbeddedSelect(sql, ind));
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
                    if (cfs.isEmbeddedSelect(myData.substring(i, myData.length()))) {
                        String sql = myData.substring(i, myData.length());
                        StringIndexes ind = findIndexes.getIndexesForSqlWithinParens(sql);
                        EmbeddedSelectsFormatter esf =
                                EmbeddedSelectsFormatterFactory.getFormatter(
                                        FOUR_INDENTS, tab,stringIndentAmount,selectedStyle);
                        sb.append(esf.formatEmbeddedSelect(sql, ind));
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



    /**
     *
     * @param sb
     * @param sql
     * @param ind
     */
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

}
