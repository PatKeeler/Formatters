package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * User: Pat Keeler
 * Date: 11/2/11
 * Time: 7:40 PM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class Alter extends Node implements Serializable {

    private static final long serialVersionUID = 1951L;

    private static String INVALID_ALTER_CMD = "Alter cmd can not be an empty value";
    private static String INVALID_ALTER_DATA = "Alter data can not be an empty value";

    private final String cmd;
    private final String data;


    /**
     * Final Argument Constructor.
     * @param cmd
     * @param data
     * @param recursionTab
     * @param userIndentAmount
     */
    public Alter(final String cmd,
                  final String data,
                  final String recursionTab,
                  final String userIndentAmount,
                  final String selectedStyle) {

        super(recursionTab, userIndentAmount, selectedStyle);

        this.cmd = cmd;
        this.data = data;

        if (cmd.isEmpty()) {
            throw new InvalidParameterException(INVALID_ALTER_CMD);
        }

        if (data.isEmpty()) {
            throw new InvalidParameterException(INVALID_ALTER_DATA);
        }
    }


    /**
     * This class will have the table name as the data resulting in
     * a one line statement.
     *
     * @param node
     * @return
     */
    @Override
    public String processLine(Node node) {

        StringBuffer sb = new StringBuffer();

        sb.append(format("\n%s%s\n%s%s%s", tab, cmd.trim(), 
                tab, userIndentTab, data.trim()));

        return sb.toString();
    }

}

