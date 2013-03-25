package tools.java.pats.formatters.Operators.Factory;

import java.security.InvalidParameterException;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.formatters.Operators.OperatorsFormatter;
import tools.java.pats.nodes.Node;

/**
 * This factory will return 1 of 2 possible instances
 *   based on the amount of user supplied indents.
 *
 * Created by IntelliJ IDEA.
 * User: Pat
 * Date: 4/12/12
 * Time: 9:25 PM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class OperatorsFormatterFactory {

    /** Invalid indent parameter message */
    private static String INVALID_INDENT = "Indent amount must be a value between 0 and 9";


    /**
     * public method to return 1 of the 2 instances.
     *
     * @param indents
     * @param recursionTab
     * @param userIndentAmount
     * @return
     */
     public static OperatorsFormatter getFormatter(int indents,
                                                   String recursionTab,
                                                   String userIndentAmount) {

        //Verify indents input
        try {
            if (indents < Node.ZERO_INDENTS || indents > Node.NINE_INDENTS) {
                throw new InvalidParameterException(INVALID_INDENT);
            }
        } catch (Exception e) {
            throw new InvalidParameterException(e.getLocalizedMessage());
        }

        return new OperatorsFormatter(indents,
                                      recursionTab,
                                      userIndentAmount);
    }
}
