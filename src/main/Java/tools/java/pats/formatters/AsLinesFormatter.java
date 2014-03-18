package tools.java.pats.formatters;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.constants.ProjectStaticConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Format select statements - determine proper column location
 * for the AS statements and format each property accordingly.
 *
 * @author keeler.pat
 * @version 1.0.0:$
 */
@ThreadSafe
public class AsLinesFormatter implements Serializable, ProjectStaticConstants {

    private static final long serialVersionUID = 1951L;

    /** AtomicInteger maximimum property length in column list */
    private final AtomicInteger maxPropLength = new AtomicInteger(0);

    /** List of columns to format.  */
    private final Collection<String> columnList;

    /** Default minimum index into line for AS. */
    private static final int defaultMinLength = 15;

    /**
     * Public constructor
     */
    public AsLinesFormatter(List<String> columns) {

        if (columns == null || columns.size() == 0) {
            columnList = new ArrayList<String>();
        }
        else {
            columnList = columns;
        }

        /* AtomicInteger - get the max length setting. */
        maxPropLength.set(getLongestColumnLength(columnList));
    }

    public List<String> formatNode() {

        /* Formatted List to return */
        List<String> newList = new ArrayList<String>();

        /* current column prop length */
        int propLength;

        /* format the columns for newList */
        for (String s : columnList) {
            //The CASE and IF statements will be formatted later.
            if ((! s.trim().startsWith("CASE")) &&
                (! s.trim().startsWith("MAX(CASE")) &&
                (! s.trim().startsWith("SUM(CASE")) &&
                (! s.trim().startsWith("IF")) &&
                (! s.trim().startsWith("("))) {

                String[] both = s.split(" AS ");

                if (both.length > 1) {
                    propLength = maxPropLength.get() - both[0].trim().length();
                    String space = SPACES.substring(0, propLength - 1);
                    String temp = both[0].trim() + space + "AS " + both[1].trim();
                    newList.add(temp);
                }
                else {
                    newList.add(s.trim());
                }
        	}
            else {
                newList.add(s);
            }
        }

        return newList;
    }


    /**
     * Find the property length of longest column.
     *
     * @param columns List
     * @return int length of longest column
     */
    private int getLongestColumnLength(Collection<String> columns) {

        /* Property lengths */
        int propLength;
        int minLength = defaultMinLength;

        /* Iterate through columns to get longest property name. 
         * Skip CASE statements.*/
        for (String s : columns) {
            s = s.trim();
        	if ((! s.trim().startsWith("CASE")) &&
                (! s.trim().startsWith("MAX(CASE")) &&
                (! s.trim().startsWith("SUM(CASE")) &&
                (! s.trim().startsWith("IF")) &&
                (! s.trim().startsWith("("))) {
                propLength = s.indexOf(" AS ");
                if (propLength + 2 > minLength) {
                    minLength = propLength + 2;
                }
        	}
        }

        return minLength;
    }

    /** AtomicInteger maxPropLength Getter */
    public int getMaxPropLength() {
        return maxPropLength.get();
    }

    /** AtomicInteger maxPropLength Setter */
    public void setMaxPropLength(int maxPropLength) {
        this.maxPropLength.set(maxPropLength);
    }
}
