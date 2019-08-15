package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * User: Pat
 * Date: 2/3/12
 * Time: 6:22 PM
 *
 * The comment commands are created in the NodeParser when it finds a comment.
 * The comments type options are:
 *      double dash
 *      double slash
 *      single and multi line slash asterisk combos
 * The command will be COMMENT, the data will be the comment in parens.
 */
@ThreadSafe
public class Comment extends Node implements Query, Serializable {

    private static final long serialVersionUID = 1951L;

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
    public Comment(final String cmd,
                  final String data,
                  final String recursionTab,
                  final String userIndentAmount,
                  final String selectedStyle) {

        super(recursionTab, userIndentAmount, selectedStyle);

        this.data = data;

        if (cmd.isEmpty()) {
            throw new InvalidParameterException(INVALID_COMMA_CMD);
        }

        if (data.isEmpty()) {
            throw new InvalidParameterException(INVALID_COMMA_DATA);
        }
    }


    /**
     * Append the data.
     *
     * If the data starts with \n:
     * append with \n,
     *  else
     * append without a \n.
     *
     * @param node - Class type
     * @return - formatted sql string
     */
    public String processLine(Query node) {

        printCmdData("", this.data);

        StringBuffer sb = new StringBuffer();

        if (data.startsWith("\n")) {
            sb.append(format("\n%s%s%s", tab, userIndentTab, data.trim()));
        } else {
            sb.append(format("%s%s%s", tab, userIndentTab, data.trim()));
        }

        return sb.toString();
    }

}
