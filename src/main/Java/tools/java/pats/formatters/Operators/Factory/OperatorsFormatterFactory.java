package tools.java.pats.formatters.Operators.Factory;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.constants.ProjectStaticConstants;
import tools.java.pats.formatters.Operators.OperatorsFormatter;
import tools.java.pats.nodes.Node;

import java.io.Serializable;
import java.security.InvalidParameterException;

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
public class OperatorsFormatterFactory implements Serializable, ProjectStaticConstants {

    private static final long serialVersionUID = 1951L;


    /**
     * public method to return 1 of the 2 instances.
     *
     * @param indents - user indent
     * @param recursionTab - spaces based on recursion
     * @param userIndentAmount - user indent amount
     * @return instance
     */
     public static OperatorsFormatter getFormatter(int indents,
                                                   String recursionTab,
                                                   String userIndentAmount,
                                                   String selectedStyle) {

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
                                      userIndentAmount,
                                      selectedStyle);
    }
}
