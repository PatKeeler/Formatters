package tools.java.pats.string.utils;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.constants.ProjectStaticConstants;

import java.io.Serializable;

import static java.lang.String.format;

/**
 * Program to add quotes around each line of an input string.
 * <p/>
 * Each line will have the concatenating + sign appending the line 
 * to the next line.  
 * <p/>
 * The output can be pasted into a Java program as a String value.
 * 
 * @author Pat Keeler
 *
 */
@ThreadSafe
public class AddQuotesForJavaString implements Serializable, ProjectStaticConstants {

    private static final long serialVersionUID = 1951L;


	/**
	 * This method adds the quotes.
	 * 
	 * @param input
	 * @return quoted string.
	 */
	public String addQuotes(String input) {

		StringBuffer sb = new StringBuffer();

		//Split input string on line terminators (systems line separator).
		String[] lines = p1.split(input);

        //If lines.length == 1, try again with p2, see note above.
        if (lines.length == 1) {
            lines = p2.split(input);
        }
		
		//Add quotes to each line.
        for (String s : lines) {
			if (s.length() > 0) {
				sb.append(format("\n\"%s \" + ", s));
			}			
		}

		//Remove leading newline character.
		if (sb.charAt(0) == '\n') {
			sb.deleteCharAt(0);
		}
		
		//Replace the last + sign with a ;.
		if (sb.charAt(sb.length() - 2) == '+') {
			sb.replace(sb.length() - 2, sb.length() - 1, ";");
		}
		
		return sb.toString();
	}

}
