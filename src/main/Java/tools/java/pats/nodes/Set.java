package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * User: Pat Keeler
 * Date: 10/24/11
 * Time: 7:30 PM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class Set extends Node implements Query, Serializable {

    private static final long serialVersionUID = 1951L;

    private static String INVALID_SET_CMD = "Set cmd can not be an empty value";
    private static String INVALID_SET_DATA = "Set data can not be an empty value";

    private final String cmd;
    private final String data;


    /**
     * Final Argument Constructor.
     * @param cmd
     * @param data
     * @param recursionTab
     * @param userIndentAmount
     */
    public Set(final String cmd,
                  final String data,
                  final String recursionTab,
                  final String userIndentAmount,
                  final String selectedStyle) {

        super(recursionTab, userIndentAmount, selectedStyle);

        this.cmd = cmd;
        this.data = data;

        if (cmd.isEmpty()) {
            throw new InvalidParameterException(INVALID_SET_CMD);
        }

        if (data.isEmpty()) {
            throw new InvalidParameterException(INVALID_SET_DATA);
        }
    }


    /**
     *
     * @param node
     * @return
     */
    public String processLine(Query node) {

        StringBuffer sb = new StringBuffer();

        sb.append(format("\n%s%s", tab, cmd.trim()));

        sb.append(getMultiLineSegments(data));

        return sb.toString();
    }

}
