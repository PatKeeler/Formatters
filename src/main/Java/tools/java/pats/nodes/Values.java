package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * User: Pat Keeler
 * Date: 10/24/11
 * Time: 7:40 PM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class Values extends Node implements Serializable {

    private static final long serialVersionUID = 1951L;

    private static String INVALID_VALUES_CMD = "Values cmd can not be an empty value";
    private static String INVALID_VALUES_DATA = "Values data can not be an empty value";

    private static final String OPEN_PAREN = "(";

    private final String cmd;
    private final String data;


    /**
     * Final Argument Constructor.
     * @param cmd
     * @param data
     * @param recursionTab
     * @param userIndentAmount
     */
    public Values(final String cmd,
                  final String data,
                  final String recursionTab,
                  final String userIndentAmount) {

        super(recursionTab, userIndentAmount);

        this.cmd = cmd;
        this.data = data;

        if (cmd.isEmpty()) {
            throw new InvalidParameterException(INVALID_VALUES_CMD);
        }

        if (data.isEmpty()) {
            throw new InvalidParameterException(INVALID_VALUES_DATA);
        }
    }


    @Override
    public String processLine(Node node) {

        StringBuffer sb = new StringBuffer();

        sb.append(format("\n%s%s", tab, cmd.trim()));

        sb.append(formatValuesColumns(data.trim()));

        return sb.toString();
    }

}
