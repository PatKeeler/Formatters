package tools.java.pats.formatters;

import net.jcip.annotations.ThreadSafe;

import java.io.Serializable;

import static java.lang.String.format;

/**
 *
 * Created by IntelliJ IDEA.
 * User: Pat Keeler
 * Date: 8/26/11
 * Time: 8:36 PM
 * To change this template use File | Settings | File Templates.
 */

@ThreadSafe

public class IfLinesFormatter implements Serializable {

    private static final long serialVersionUID = 1951L;

    /**
     * Method to format and return IF statements.
     */
    public String formatNode(String line, String indent, String userIndent) {

        StringBuffer newList = new StringBuffer();

        if (line.toUpperCase().trim().startsWith("IF")) {
            String[] nodes = line.split(",");
            if (nodes.length == 3) {
                String[] or = nodes[0].split(" OR ");
                if (or.length == 2) {
                    newList.append(format("\n%s%s%s%s", indent, userIndent, or[0].trim(), ","));
                    newList.append(format("\n%s%s%s%s%s%s", indent, userIndent, userIndent, userIndent, "OR " + or[1].trim(), ","));
                } else {
                newList.append(format("\n%s%s%s%s", indent, userIndent, nodes[0].trim(), ","));
                }
                newList.append(format("\n%s%s%s%s%s", indent, userIndent, userIndent, nodes[1].trim(), ","));
                newList.append(format("\n%s%s%s%s%s", indent, userIndent, userIndent, nodes[2].trim(), ","));
            }
        }

        return newList.toString();
    }
}
