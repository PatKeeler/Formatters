package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * User: Pat Keeler
 * Date: 11/5/11
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class CreateTable extends Node implements Serializable {

    private static final long serialVersionUID = 1951L;

    private static String INVALID_CREATETABLE_CMD = "CreateTable cmd can not be an empty value";
    private static String INVALID_CREATETABLE_DATA = "CreateTable data can not be an empty value";

    private final String cmd;
    private final String data;


    /**
     * Final Argument Constructor.
     * @param cmd
     * @param data
     * @param recursionTab
     * @param userIndentAmount
     */
    public CreateTable(final String cmd,
                  final String data,
                  final String recursionTab,
                  final String userIndentAmount,
                  final String selectedStyle) {

        super(recursionTab, userIndentAmount, selectedStyle);

        this.cmd = cmd;
        this.data = data;

        if (cmd.isEmpty()) {
            throw new InvalidParameterException(INVALID_CREATETABLE_CMD);
        }

        if (data.isEmpty()) {
            throw new InvalidParameterException(INVALID_CREATETABLE_DATA);
        }
    }


    @Override
    public String processLine(Node node) {

        //semi colon marker.
        boolean semi_colon = false;

        //record if semi_colon supplied
        if (data.trim().endsWith(SEMI_COLON)) {
            semi_colon = true;
        }

        StringBuffer sb = new StringBuffer();

        //Look for open paren in data
        int index = data.indexOf(OPEN_PAREN_BYTE);
        if (index == -1) {
            throw new InvalidParameterException("Create Table is missing open and " +
                    "closing parens.");
        }

        //Append the command.
        sb.append(format("\n%s%s", tab, cmd.trim()));

        // Get the table name from data.
        String table = data.substring(0, index - 1).trim();
        sb.append(format(" %s", table.trim()));

        //And get the columns and format them.
        String columns = data.substring(index, data.length() - 1);

        sb.append(formatMultiColumnsWithinParens(columns));

        //add semi_colon if supplied
        if (semi_colon) {
            sb.append(SEMI_COLON);
        }

        return sb.toString();
    }

}
