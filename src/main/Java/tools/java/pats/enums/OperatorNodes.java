package tools.java.pats.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pat
 * Date: 4/6/14
 * Time: 8:07 AM
 */
public enum OperatorNodes {

    AND(" AND ", "AND"),
    CONTAINS("CONTAINS", "CONTAINS"),
    EXISTS("EXISTS ", "EXISTS"),
    IN(" IN ", "IN"),
    NOT_IN(" NOT IN ", "NOT IN"),
    NOT_EXISTS("NOT EXISTS ", "NOT EXISTS"),
    ON(" ON ", "ON"),
    OR(" OR ", "OR")
    ;

    // The operator search string.
    public String operator;

    // FormatString defines the formatting string.
    public String formatString;


    /**
     * Constructor - Node name and it's corresponding
     *   Class name.
     */
    private OperatorNodes(String operator, String formatString) {

        this.operator = operator;
        this.formatString = formatString;
    }


    /**
     * Return a <code>List<Operator><code> of all operator strings.
     */
    public static List<String> getOperators() {

        List<String> operatorList = new ArrayList<String>();

        for (OperatorNodes node : OperatorNodes.values()) {
            operatorList.add(node.operator);
        }

        return operatorList;
    }


    /**
     * Return Class name representing the Node type.
     */
    public String getFormatString() {

        return formatString.toString();
    }

}
