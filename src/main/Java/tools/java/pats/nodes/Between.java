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
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class Between extends Node implements Query, Serializable {

    private static final long serialVersionUID = 1951L;

    private static String INVALID_BETWEEN_CMD = "Between cmd can not be an empty value";
    private static String INVALID_BETWEEN_DATA = "Between data can not be an empty value";

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
    public Between(final String cmd,
                  final String data,
                  final String recursionTab,
                  final String userIndentAmount,
                  final String selectedStyle) {

        super(recursionTab, userIndentAmount, selectedStyle);

        this.cmd = cmd;
        this.data = data;

        if (cmd.isEmpty()) {
            throw new InvalidParameterException(INVALID_BETWEEN_CMD);
        }

        if (data.isEmpty()) {
            throw new InvalidParameterException(INVALID_BETWEEN_DATA);
        }
    }


    /**
     *
     * @param node
     * @return
     */
    public String processLine(Query node) {

        StringBuffer sb = new StringBuffer();

        sb.append(format("\n%s%s%s%s", userIndentTab, userIndentTab, tab, cmd.trim()));

        sb.append(format("\n%s%s%s%s%s", userIndentTab, userIndentTab,
                tab, userIndentTab, formatForAndOrInString(data)));

        return sb.toString();
    }


    /**
     * Method to call the operator formatter.
     *
     * @param element - string to be modified.
     * @return String  - formatted element
     */
    @Override
    protected String formatForAndOrInString(String element) {

        StringBuffer sb = new StringBuffer();
        element = element.trim();
        int indents = 2;

        OperatorsFormatter formatOperators =
                OperatorsFormatterFactory.getFormatter(indents, tab, stringIndentAmount, selectedStyle);

        sb.append(formatOperators.formatOperators(element));

        return sb.toString();
    }

}
