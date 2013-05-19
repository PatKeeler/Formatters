package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.formatters.Operators.Factory.OperatorsFormatterFactory;
import tools.java.pats.formatters.Operators.OperatorsFormatter;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * User: PJ
 * Date: 10/16/11
 * Time: 6:33 PM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class Where extends Node implements Serializable {

    private static final long serialVersionUID = 1951L;

    private static String INVALID_WHERE_CMD = "Where cmd can not be an empty value";
    private static String INVALID_WHERE_DATA = "Where data can not be an empty value";

    private final String cmd;
    private final String data;


    /**
     * Final Argument Constructor.
     * @param cmd
     * @param data
     * @param recursionTab
     * @param userIndentAmount
     */
    public Where(final String cmd,
                  final String data,
                  final String recursionTab,
                  final String userIndentAmount,
                  final String selectedStyle) {

        super(recursionTab, userIndentAmount, selectedStyle);

        this.cmd = cmd;
        this.data = data;

        if (cmd.isEmpty()) {
            throw new InvalidParameterException(INVALID_WHERE_CMD);
        }

        if (data.isEmpty()) {
            throw new InvalidParameterException(INVALID_WHERE_DATA);
        }
    }


    @Override
    public String processLine(Node node) {

        StringBuffer sb = new StringBuffer();

        if(block) {
//            sb.append(format("\n%s%s  ", tab, cmd.trim()));
            int indexAnd = data.indexOf(" AND ");
            int indexOr = data.indexOf(" OR ");
            if (indexAnd > -1 || indexOr > -1) {
                sb.append(format("\n%s%s\n%s%s    ", tab, cmd.trim(), tab, userIndentTab));
            } else {
                sb.append(format("\n%s%s\n%s%s", tab, cmd.trim(), tab, userIndentTab));
            }
        } else {
            sb.append(format("\n%s%s\n%s%s", tab, cmd.trim(), tab, userIndentTab));
        }

        String myData = data.trim();

        int indents = 0;

        OperatorsFormatter formatOperators =
                OperatorsFormatterFactory.getFormatter(indents, tab, stringIndentAmount, selectedStyle);

        sb.append(formatOperators.formatOperators(data));

        return sb.toString();
    }

}
