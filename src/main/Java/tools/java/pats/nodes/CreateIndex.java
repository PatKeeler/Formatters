package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 *
 * Created by IntelliJ IDEA.
 * User: Pat Keeler
 * Date: 11/5/11
 * Time: 8:20 PM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class CreateIndex extends Node implements Query, Serializable {

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
    public CreateIndex(final String cmd,
                  final String data,
                  final String recursionTab,
                  final String userIndentAmount,
                  final String selectedStyle) {

        super(recursionTab, userIndentAmount, selectedStyle);

        this.cmd = cmd;
        this.data = data;

        if (cmd.isEmpty()) {
            throw new InvalidParameterException(INVALID_CREATEINDEX_CMD);
        }

        if (data.isEmpty()) {
            throw new InvalidParameterException(INVALID_CREATEINDEX_DATA);
        }
    }


    /**
     *
     * @param node - Class type
     * @return formatted sql string
     */
    public String processLine(Query node) {

        printCmdData(this.cmd, this.data);

        //semi colon marker.
        boolean semi_colon = false;

        //Get clean string
        String myData = data.trim();

        //record if semi_colon supplied
        if (myData.endsWith(SEMI_COLON)) {
            semi_colon = true;
            myData = myData.substring(0, myData.length() - 1);
        }

        StringBuffer sb = new StringBuffer();
        String columns;

        // CREATE INDEX PIndex ON Persons (LastName, FirstName)

        //Split on open paren
        String[] strings = myData.split("\\(");

        if (strings.length == 2) {
            //Append the command and the index
            sb.append(format("\n%s%s %s", tab, cmd.trim(), strings[0].trim()));

            //append the open paren
            sb.append(format("\n%s%s%s", tab, userIndentTab, "("));

            //Get columns within parens
            columns = strings[1].substring(0, strings[1].length() - 1).trim();
            //remove close paren if needed
            if (columns.endsWith(")")) {
                columns = columns.substring(0, columns.length() -1);
            }

            //format for one or more columns.
            String[] columnArray = columns.split(",");

            for (String s : columnArray) {
                sb.append(format("\n%s%s%s%s,", tab, userIndentTab,
                        userIndentTab, s.trim()));
            }
            // Remove the last comma
            sb.replace(sb.length() - 1, sb.length(), "");

            //append the closing paren
            sb.append(format("\n%s%s%s", tab, userIndentTab, ")"));
        }
        //append semi_colon if supplied.
        if (semi_colon) {
            sb.append(SEMI_COLON);

        }

        return sb.toString();
    }

}
