package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * User: Pat Keeler
 * Date: 10/23/11
 * Time: 8:14 AM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class Union extends Node implements Query, Serializable {

    private static final long serialVersionUID = 1951L;

    private final String cmd;



    /**
     * Final Argument Constructor.
     *
     * @param cmd - sql command name
     * @param data - sql arguments for command
     * @param recursionTab - number of user indents
     * @param userIndentAmount - length of user supplied indents
     * @param selectedStyle - block or expanded
     */
    public Union(final String cmd,
                 final String data,
                 final String recursionTab,
                 final String userIndentAmount,
                 final String selectedStyle) {

      super(recursionTab, userIndentAmount, selectedStyle);

        this.cmd = cmd;

        if (cmd.isEmpty()) {
            throw new InvalidParameterException(INVALID_UNION_CMD);
        }

        /** Data is not used in the class. */
     }


    /**
     * This method adds the union command surrounded by blank lines.
     *
     * @param node - Class type
     * @return formatted sql string
     */
    public String processLine(Query node) {

        StringBuffer sb = new StringBuffer();

        sb.append(format("\n\n%s%s\n", tab, cmd.trim()));

        return sb.toString();
    }

}
