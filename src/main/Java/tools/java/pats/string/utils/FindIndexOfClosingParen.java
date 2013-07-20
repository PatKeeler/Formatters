package tools.java.pats.string.utils;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.constants.ProjectStaticConstants;

import java.io.Serializable;
import java.security.InvalidParameterException;

/**
 * This utility, given the index of opening paranthesis, will 
 * parse the given input string and return the index of the matching 
 * closing parenthesis.
 * 
 * @author Pat Keeler
 */
@ThreadSafe
public class FindIndexOfClosingParen implements Serializable, ProjectStaticConstants {

    private static final long serialVersionUID = 1951L;

	/**
	 * Default constructor.
	 */
	public FindIndexOfClosingParen() {
		super();
	}
	

	/**
	 * Find the matching closing paren and return it's index.
	 * 
	 * @param index
	 * @param input
	 * @return
	 */
	public int findClosingIndex(int index, String input) {

		int parenCount = 0;
		int n = 0;

		for (n = index; n < input.length(); n++) {
			if (input.charAt(n) == OPEN_PAREN_BYTE) {
				parenCount++;
			} else if (input.charAt(n) == CLOSING_PAREN_BYTE) {
				parenCount--;
			}

			if (parenCount == 0) {
				break;
			}
		}

		if (parenCount > 0) {
			throw new InvalidParameterException(
					"\n  Non matching parenthesis, fix sql and resubmit." +
					"\n    sql: " + input.substring(index));
		}
		return n;
	}	
}