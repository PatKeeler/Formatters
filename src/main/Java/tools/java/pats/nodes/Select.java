package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * User: Pat Keeler
 * Date: 10/8/11
 * Time: 9:32 PM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class Select extends Node implements Query, Serializable {

    private static final long serialVersionUID = 1951L;

    private static String INVALID_SELECT_CMD = "Select cmd can not be an empty value";
    private static String INVALID_SELECT_DATA = "Select data can not be an empty value";

    private final String cmd;
    private final String data;


    /**
     * Final Argument Constructor.
     *
     * @param cmd
     * @param data
     * @param recursionTab
     * @param userIndentAmount
     * @param selectedStyle
     */
    public Select(final String cmd,
                  final String data,
                  final String recursionTab,
                  final String userIndentAmount,
                  final String selectedStyle) {

        super(recursionTab, userIndentAmount, selectedStyle);

        this.cmd = cmd;
        this.data = data;

        if (cmd.isEmpty()) {
            throw new InvalidParameterException(INVALID_SELECT_CMD);
        }

        if (data.isEmpty()) {
            throw new InvalidParameterException(INVALID_SELECT_DATA);
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
