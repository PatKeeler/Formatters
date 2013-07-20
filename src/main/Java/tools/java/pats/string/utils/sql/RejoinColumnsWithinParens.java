package tools.java.pats.string.utils.sql;

import net.jcip.annotations.ThreadSafe;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.String.format;

/**
 * This class receives a String array of columns that were created by
 * a string.split command.  The columns are inspected and columns that
 * were originally with in parens are rejoined.
 *
 * Created by IntelliJ IDEA.
 * User: Pat
 * Date: 4/23/12
 * Time: 9:50 PM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class RejoinColumnsWithinParens {

    //The columns to work on
    private final List<String> columns;
    private final String tab;

    /**
     * Parameter constructor.
     * @param columns
     */
    public RejoinColumnsWithinParens(final List<String> columns, final String tab) {
        super();

        if (columns == null || columns.size() == 0) {
            throw new InvalidParameterException("The columns array can not be null or empty");
        }

        if (tab == null) {
            throw new InvalidParameterException("The tab can not be null");
        }

        this.columns = columns;
        this.tab = tab;
    }

    /**
     * Rejoin the columns if needed.
     *
     * @return String array
     */
    public List<String> rejoinColumns() {
        
        List<String> columnList = columns;
        List<String> newList = new ArrayList<String>();

        Iterator<String> iter = columnList.iterator();
        String line = "";
        String newLine = "";
        int inParen = 0;

        while (iter.hasNext()) {
            line = iter.next();
            for (int n = 0; n < line.length(); n++) {
                if (line.charAt(n) == '(') {
                    inParen += 1;
                }
                else if (line.charAt(n) == ')') {
                    inParen -= 1;
                }
            }
            if (inParen > 0) {
                //add comma.
                newLine += format("%s%s, ", tab, line);
                }
            else {
                //no comma.
                newLine += format("%s%s ", tab, line);
                newList.add(newLine);
                newLine = "";
            }
        }

        return newList;
    }

}
