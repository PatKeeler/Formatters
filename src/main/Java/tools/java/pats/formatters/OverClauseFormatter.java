package tools.java.pats.formatters;

import tools.java.pats.constants.ProjectStaticConstants;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.String.format;

/**
 *
 * Created with IntelliJ IDEA.
 * User: Pat
 * Date: 5/12/13
 * Time: 6:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class OverClauseFormatter implements Serializable, ProjectStaticConstants {

    private static final long serialVersionUID = 1951L;

    /** Format variables */
    protected final String tab;
    protected final String stringIndentAmount;
    protected final String selectedStyle;
    // Spaces string of user indent amount
    protected final String userIndentTab;
    // int value of user indent amount
	protected final int userIndentAmount;


    /**
     * Final argument constructor for super
     *
     * @param recursionTab - spaces based on recursion
     * @param stringIndentAmount - user indent amount
     * @param selectedStyle - block or expanded
     */
    public OverClauseFormatter (final String recursionTab,
                                final String stringIndentAmount,
                                final String selectedStyle) {

        this.tab = recursionTab;
        this.selectedStyle = selectedStyle;


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
     * Format Over Clause method
     *
     * @param s un-formatted sql
     * @return formatted s
     */
    public String formatOverClause(String s) {

        StringBuffer sb = new StringBuffer();

        int index = s.indexOf("OVER");
        //append property through OVER.
        String tempOver = s;
        String temp = tempOver.substring(0, index + 4);
        sb.append(format("\n%s%s%s", tab, userIndentTab, temp));
        //append open paren on new line
        sb.append(format("\n%s%s%s%s", tab,userIndentTab, userIndentTab, "("));
        //bump past OVER
        tempOver = tempOver.substring(index + 4);
        //find open paren - may be multiple spaces
        index = tempOver.indexOf("(");
        tempOver = tempOver.substring(index + 1);
        //find Order By
        String[] overCmds = tempOver.split(" ORDER BY ");
        if (overCmds.length == 2) {
            //append PARTITION BY on new line
            sb.append(format("\n%s%s%s%s%s", tab,userIndentTab, userIndentTab,
                    userIndentTab, overCmds[0].trim()));
            //append ORDER BY on new line
            //first, find closing paren
            //TODO - there can be a comma and more properties after OVER, handle them here.
            //TODO - would have to find closing paren on over clause and format accordingly
            index =overCmds[1].lastIndexOf(")");
            sb.append(format("\n%s%s%s%s%s%s", tab,userIndentTab, userIndentTab,
                    userIndentTab, "ORDER BY ", overCmds[1].substring(0,index)));
            //append close paren on new line
            sb.append(format("\n%s%s%s%s,", tab,userIndentTab, userIndentTab,
                    overCmds[1].substring(index)));
        } else if (overCmds.length == 1) {
            //append PARTITION BY on new line
            index =overCmds[0].lastIndexOf(")");
            sb.append(format("\n%s%s%s%s%s", tab,userIndentTab, userIndentTab,
                    userIndentTab, overCmds[0].substring(0, index)));
            //append close paren on new line
            sb.append(format("\n%s%s%s%s,", tab,userIndentTab, userIndentTab,
                    overCmds[0].substring(index)));
        } else {
            throw new InvalidParameterException(
                    "OVER command not properly formatted: " + s);
        }

        return sb.toString();
    }

}
