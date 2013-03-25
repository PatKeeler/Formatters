package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.formatters.Operators.Factory.OperatorsFormatterFactory;
import tools.java.pats.formatters.Operators.OperatorsFormatter;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * User: Pat Keeler
 * Date: 11/5/11
 * Time: 8:10 PM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class Having extends Node implements Serializable {

    private static final long serialVersionUID = 1951L;

    private static String INVALID_HAVING_CMD = "Having cmd can not be an empty value";
    private static String INVALID_HAVING_DATA = "Having data can not be an empty value";

    private final String cmd;
    private final String data;


    /**
     * Final Argument Constructor.
     * @param cmd
     * @param data
     * @param recursionTab
     * @param userIndentAmount
     */
    public Having(final String cmd,
                  final String data,
                  final String recursionTab,
                  final String userIndentAmount) {

        super(recursionTab, userIndentAmount);

        this.cmd = cmd;
        this.data = data;

        if (cmd.isEmpty()) {
            throw new InvalidParameterException(INVALID_HAVING_CMD);
        }

        if (data.isEmpty()) {
            throw new InvalidParameterException(INVALID_HAVING_DATA);
        }
    }


    @Override
    public String processLine(Node node) {

        StringBuffer sb = new StringBuffer();

        sb.append(format("\n%s%s\n%s%s", tab, cmd.trim(), tab, userIndentTab));

        String myData = data.trim();

        int indents = 0;

        OperatorsFormatter formatOperators =
                OperatorsFormatterFactory.getFormatter(indents, tab, stringIndentAmount);

        sb.append(formatOperators.formatOperators(data));

        return sb.toString();
    }

}
