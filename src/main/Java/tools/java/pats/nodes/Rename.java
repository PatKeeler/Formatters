package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * User: Pat Keeler
 * Date: 11/6/11
 * Time: 6:57 AM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class Rename extends Node implements Query, Serializable {

    private static final long serialVersionUID = 1951L;

    private static String INVALID_RENAME_CMD = "Rename cmd can not be an empty value";
    private static String INVALID_RENAME_DATA = "Rename data can not be an empty value";

    private final String cmd;
    private final String data;


    /**
     * Final Argument Constructor.
     * @param cmd
     * @param data
     * @param recursionTab
     * @param userIndentAmount
     */
    public Rename(final String cmd,
                  final String data,
                  final String recursionTab,
                  final String userIndentAmount,
                  final String selectedStyle) {

        super(recursionTab, userIndentAmount, selectedStyle);

        this.cmd = cmd;
        this.data = data;

        if (cmd.isEmpty()) {
            throw new InvalidParameterException(INVALID_RENAME_CMD);
        }

        if (data.isEmpty()) {
            throw new InvalidParameterException(INVALID_RENAME_DATA);
        }
    }


    /**
     * This class will have the table name as the data resulting in
     * a one line statement.
     *
     * @param node
     * @return
     */
    public String processLine(Query node) {

        StringBuffer sb = new StringBuffer();

        sb.append(format("\n%s%s\n%s%s%s", tab, cmd.trim(),
                tab, userIndentTab, data.trim()));

        return sb.toString();
    }

}
