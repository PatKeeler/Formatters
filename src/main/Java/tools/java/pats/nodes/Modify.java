package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.string.utils.FindIndexOfClosingParen;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * User: Pat Keeler
 * Date: 11/6/11
 * Time: 7:11 AM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class Modify extends Node implements Query, Serializable {

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
    public Modify(final String cmd,
                  final String data,
                  final String recursionTab,
                  final String userIndentAmount,
                  final String selectedStyle) {

        super(recursionTab, userIndentAmount, selectedStyle);

        this.cmd = cmd;
        this.data = data;

        if (cmd.isEmpty()) {
            throw new InvalidParameterException(INVALID_MODIFY_CMD);
        }

        if (data.isEmpty()) {
            throw new InvalidParameterException(INVALID_MODIFY_DATA);
        }
    }


    /**
     * May need to handle multiple columns.
     *
     * @param node - Class type
     * @return formatted sql string
     */
    public String processLine(Query node) {

        StringBuffer sb = new StringBuffer();

        //Append the command.
        sb.append(format("\n%s%s", tab, cmd.trim()));

        /** See if it's single or multi definitions.  If first
         * character of data is open paren it's multi definitions,
         * else it's a single definition.
         */

        String myData = data.trim();

        if (myData.startsWith("(")) {
            // ALTER TABLE supplier
            // MODIFY ( supplier_name varchar2(100) not null,
            //          city          varchar2(75)   );
            FindIndexOfClosingParen findClosingParen = new FindIndexOfClosingParen();
            int endIndex = findClosingParen.findClosingIndex(0, myData);
            sb.append(formatMultiColumnsWithinParens(myData.substring(0, endIndex)));
            //Add semi-colon, or any other trailing data.
            sb.append(myData.substring(endIndex + 1));
        } else {
            // ALTER TABLE supplier
            // MODIFY supplier_name   varchar2(100)     not null;

            // Data goes on next line.
            sb.append(format("\n%s%s%s", tab, userIndentTab, myData));
        }

        return sb.toString();
    }

}
