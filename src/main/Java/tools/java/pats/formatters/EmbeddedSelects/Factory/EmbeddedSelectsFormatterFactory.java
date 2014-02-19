package tools.java.pats.formatters.EmbeddedSelects.Factory;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.formatters.EmbeddedSelects.EmbeddedSelectsFormatter;
import tools.java.pats.nodes.Node;

import java.security.InvalidParameterException;

/**
 * This factory will return 1 of 2 possible instances
 *   based on the amount of user supplied indents.
 *
 * Created by IntelliJ IDEA.
 * User: Pat
 * Date: 12/19/11
 * Time: 7:58 PM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class EmbeddedSelectsFormatterFactory {


    /**
     * public method to return 1 of the 2 instances.
     *
     * @param indents - formatting indent amount
     * @param recursionTab - indent based on recursion
     * @param userIndentAmount - user indent amount
     * @return instance
     */
     public static EmbeddedSelectsFormatter getFormatter(int indents,
                                                         String recursionTab,
                                                         String userIndentAmount,
                                                         String selectedStyle) {

        /** Invalid indent parameter message */
        String INVALID_INDENT = "Indent amount must be a value of 2 or 4";

        //Verify indents input
        try {
            if (indents != Node.TWO_INDENTS &&
                    indents != Node.FOUR_INDENTS) {
                throw new InvalidParameterException(INVALID_INDENT);
            }
        } catch (Exception e) {
            throw new InvalidParameterException(e.getLocalizedMessage());
        }

        return new EmbeddedSelectsFormatter(indents,
                                            recursionTab,
                                            userIndentAmount,
                                            selectedStyle);
    }
}
