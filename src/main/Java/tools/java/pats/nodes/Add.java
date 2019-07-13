package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.string.utils.FindIndexOfClosingParen;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 *
 * Created by IntelliJ IDEA.
 * User: Pat Keeler
 * Date: 11/2/11
 * Time: 8:11 PM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class Add extends Node implements Query, Serializable {

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
    public Add(final String cmd,
               final String data,
               final String recursionTab,
               final String userIndentAmount,
               final String selectedStyle) {

        super(recursionTab, userIndentAmount, selectedStyle);

        this.cmd = cmd;
        this.data = data;

        if (cmd.isEmpty()) {
            throw new InvalidParameterException(INVALID_ADD_CMD);
        }

        if (data.isEmpty()) {
            throw new InvalidParameterException(INVALID_ADD_DATA);
        }
    }


    /**
     * May need to handle multiple columns.
     *
     * @param node - Class type
     * @return formatted sql string
     */
    public String processLine(Query node) {

        StringBuilder sb = new StringBuilder();

        sb.append(format("\n%s%s", tab, cmd.trim()));

        //Look for open paren in data (it's optional and present when multiple columns).
        if (data.trim().startsWith("(")) {
            int index = data.indexOf(OPEN_PAREN_BYTE);
            if (index > -1) {
                //Yes, there are multi columns.
            	//Get index of closing paren
            	FindIndexOfClosingParen fcp = new FindIndexOfClosingParen();
            	int endIndex = fcp.findClosingIndex(index, data);
            	//format columns
                sb.append(formatMultiColumnsWithinParens(data.substring(index, endIndex + 1)));
                //Add remaining (probably semi-colon)
                sb.append(data.substring(endIndex + 1));
            }
        } else {
            sb.append(format("\n%s%s%s", tab, userIndentTab, data.trim()));
        }

        return sb.toString();
    }

}
