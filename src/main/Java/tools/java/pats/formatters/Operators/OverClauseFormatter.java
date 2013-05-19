package tools.java.pats.formatters.Operators;

import tools.java.pats.nodes.Node;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 * Created with IntelliJ IDEA.
 * User: Pat
 * Date: 5/12/13
 * Time: 6:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class OverClauseFormatter extends Node implements Serializable{

    private static final long serialVersionUID = 1951L;


    /**
     * Final argument constructor for super
     *
     * @param recursionTab
     * @param userIndentAmountString
     */
    public OverClauseFormatter (final String recursionTab,
                                final String userIndentAmountString,
                                final String selectedStyle) {

        super(recursionTab, userIndentAmountString, selectedStyle);
    }


    /**
     * Format Over Clause method
     *
     * @param s
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
        String[] overColumns = tempOver.split(",");
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
