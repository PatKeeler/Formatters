package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * User: Pat Keeler
 * Date: 10/23/11
 * Time: 8:14 AM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class Union extends Node implements Serializable {

    private static final long serialVersionUID = 1951L;

    private static String INVALID_UNION_CMD = "Union cmd can not be an empty value";

    private final String cmd;
    private final String data;



    /**
     * Final Argument Constructor.
     * @param cmd
     * @param recursionTab
     * @param userIndentAmount
     */
    public Union(final String cmd,
                 final String data,
                final String recursionTab,
                final String userIndentAmount,
                final String selectedStyle) {

      super(recursionTab, userIndentAmount, selectedStyle);

        this.cmd = cmd;
        this.data = data;

        if (cmd.isEmpty()) {
            throw new InvalidParameterException(INVALID_UNION_CMD);
        }

        /** Data is not used in the class. */
     }


    /**
     * This method only adds the union command sorrounded by blank lines.
     *
     * @param node
     * @return
     */
    @Override
    public String processLine(Node node) {

        StringBuffer sb = new StringBuffer();

        sb.append(format("\n\n%s%s\n", tab, cmd.trim()));

        return sb.toString();
    }

}
