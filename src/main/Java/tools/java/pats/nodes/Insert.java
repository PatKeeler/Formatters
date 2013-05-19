package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * User: Pat Keeler
 * Date: 10/24/11
 * Time: 7:37 PM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class Insert extends Node implements Serializable {

    private static final long serialVersionUID = 1951L;

    private static String INVALID_INSERT_CMD = "Insert cmd can not be an empty value";
    private static String INVALID_INSERT_DATA = "Insert data can not be an empty value";

    private final String cmd;
    private final String data;


    /**
     * Final Argument Constructor.
     * @param cmd
     * @param data
     * @param recursionTab
     * @param userIndentAmount
     */
    public Insert(final String cmd,
                  final String data,
                  final String recursionTab,
                  final String userIndentAmount,
                  final String selectedStyle) {

        super(recursionTab, userIndentAmount, selectedStyle);

        this.cmd = cmd;
        this.data = data;

        if (cmd.isEmpty()) {
            throw new InvalidParameterException(INVALID_INSERT_CMD);
        }

        if (data.isEmpty()) {
            throw new InvalidParameterException(INVALID_INSERT_DATA);
        }
    }


    /**
     * This class must collect the table name from the data.
     *
     * There may or may not be multi columns in parens - process accordingly.
     *
     * @param node
     * @return
     */
    @Override
    public String processLine(Node node) {

        StringBuffer sb = new StringBuffer();

        //Look for open paren in data
        int index = data.indexOf(OPEN_PAREN_BYTE);
        if (index > -1) {
            //Yes multi columns, get the table name
            String table = data.substring(0, index).trim();
            sb.append(format("\n%s%s %s", tab, cmd.trim(), table));

            //And get the columns and format them.
            String columns = data.substring(index);
            sb.append(formatMultiColumnsWithinParens(columns));
        } else {
            sb.append(format("\n%s%s %s", tab, cmd.trim(), data.trim()));
        }

        return sb.toString();
    }

}
