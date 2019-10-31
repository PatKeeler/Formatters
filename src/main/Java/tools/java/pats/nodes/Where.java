package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.formatters.Operators.Factory.OperatorsFormatterFactory;
import tools.java.pats.formatters.Operators.OperatorsFormatter;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 *
 * Created with IntelliJ IDEA.
 * User: PJ
 * Date: 10/16/11
 * Time: 6:33 PM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class Where extends Node implements Query, Serializable {

    private static final long serialVersionUID = 1951L;

    private final String cmd;
    private final String data;


    /**
     * Final Argument Constructor.
     *
     * @param cmd - sql command name
     * @param data - sql arguments for command
     * @param recursionTab - number of user indents
     * @param userIndentAmount - length of user supplied indents
     * @param selectedStyle - block or expanded
     */
    public Where(final String cmd,
                  final String data,
                  final String recursionTab,
                  final String userIndentAmount,
                  final String selectedStyle) {

        super(recursionTab, userIndentAmount, selectedStyle);

        this.cmd = cmd;
        this.data = data;

        String INVALID_WHERE_CMD = "Where cmd can not be an empty value";
        String INVALID_WHERE_DATA = "Where data can not be an empty value";

        if (cmd.isEmpty()) {
            throw new InvalidParameterException(INVALID_WHERE_CMD);
        }

        if (data.isEmpty()) {
            throw new InvalidParameterException(INVALID_WHERE_DATA);
        }
    }


    /**
     *
     * @param node - Class type
     * @return formatted sql string
     */
    public String processLine(Query node) {

        printCmdData(this.tab, this.cmd, this.data);

        StringBuffer sb = new StringBuffer();

        if(block) {
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

        int indents = 0;

        OperatorsFormatter formatOperators =
                OperatorsFormatterFactory.getFormatter(indents, tab, stringIndentAmount, selectedStyle);

        sb.append(formatOperators.formatOperators(data));

        return sb.toString();
    }

}
