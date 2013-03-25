package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * User: Pat Keeler
 * Date: 10/9/11
 * Time: 7:10 AM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class From extends Node implements Serializable {

    private static final long serialVersionUID = 1951L;

    private static String INVALID_FROM_CMD = "From cmd can not be an empty value";
    private static String INVALID_FROM_DATA = "From data can not be an empty value";

    private final String cmd;
    private final String data;


    /**
     * Final Argument Constructor.
     * @param cmd
     * @param data
     * @param recursionTab
     * @param userIndentAmount
     */
    public From(final String cmd,
                final String data,
                final String recursionTab,
                final String userIndentAmount) {

        super(recursionTab, userIndentAmount);

        this.cmd = cmd;
        this.data = data;

        if (cmd.isEmpty()) {
            throw new InvalidParameterException(INVALID_FROM_CMD);
        }

        if (data.isEmpty()) {
            throw new InvalidParameterException(INVALID_FROM_DATA);
        }
     }


    @Override
    public String processLine(Node node) {

        StringBuffer sb = new StringBuffer();

        sb.append(format("\n%s%s", tab, cmd.trim()));

        sb.append(getMultiLineSegments(data));

        return sb.toString();
    }

}
