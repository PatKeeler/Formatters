/**
 * RemoveQuotesFromJavaString
 * 1/11/12 7:41 AM
 * $Id:$
 */
package tools.java.pats.string.utils;


import net.jcip.annotations.ThreadSafe;
import tools.java.pats.constants.ProjectStaticConstants;

import java.io.Serializable;

import static java.lang.String.format;


/**
 * This class takes in a quoted Java String and removes
 * the quotes through the following process:
 *      Split String into an array on line terminators.
 *      Removes leading double quote.
 *      Removes trailing quote/plus sign combination (middle lines)
 *      Removes trailing quote/semi colon combination (last line)
 *      Appends each line in a StringBuffer with a preceding \n.
 *
 * @author keeler.pat
 * @version 1:$
 */
@ThreadSafe
public class RemoveQuotesFromJavaString implements Serializable, ProjectStaticConstants
{

    /**
     * Default constructor.
     */
    public RemoveQuotesFromJavaString() {
        super();
    }

    /* Required for concurrency. */
    private static final long serialVersionUID = 1951L;


	/**
	 * This method removes the quotes.
	 *
	 * @param input string
	 * @return quoted string.
	 */
	public String removeQuotes(String input) {

		StringBuffer sb = new StringBuffer();

		//Split input string on line terminators (systems line separator).
		String[] lines = p1.split(input);

        //If lines.length == 1, try again with p2, see note above.
        if (lines.length == 1) {
            lines = p2.split(input);
        }

        String line;
        int index;

		//Remove quotes from each line.
        for (String s : lines) {
			if (s.length() > 0) {
                if (s.trim().startsWith("//")) {
                    continue;
                }
                //get line
                line = format("%s", s.trim());
                //see if + first on line
                if (line.startsWith("+")) {
                    //Yes, remove + and "
                    index = line.indexOf("\"");
                    line = line.substring(index + 1);
                }
                else if (line.startsWith("\"")) {
                    line = line.substring(1);
                }

                //remove single quote only at end of line.
                if (line.endsWith("\"")) {
                    line = line.substring(0, line.length() - 1);
                }
                
                line = line.replaceAll("\"\\s*\\+", "");
                line = line.replaceAll("\"\\s*;", "");
				sb.append(format("\n%s", line));
			}
		}

		//Remove leading newline character.
		if (sb.charAt(0) == '\n') {
			sb.deleteCharAt(0);
		}

		return sb.toString();
	}

}
