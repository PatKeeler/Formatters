package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * User: Pat Keeler
 * Date: 11/5/11
 * Time: 7:49 PM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class Truncate extends Node implements Query, Serializable {

    private static final long serialVersionUID = 1951L;

    private static String INVALID_TRUNCATE_CMD = "Truncate cmd can not be an empty value";
    private static String INVALID_TRUNCATE_DATA = "Truncate data can not be an empty value";

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
    public Truncate(final String cmd,
                  final String data,
                  final String recursionTab,
                  final String userIndentAmount,
                  final String selectedStyle) {

        super(recursionTab, userIndentAmount, selectedStyle);

        this.cmd = cmd;
        this.data = data;

        if (cmd.isEmpty()) {
            throw new InvalidParameterException(INVALID_TRUNCATE_CMD);
        }

        if (data.isEmpty()) {
            throw new InvalidParameterException(INVALID_TRUNCATE_DATA);
        }
    }


    /**
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
