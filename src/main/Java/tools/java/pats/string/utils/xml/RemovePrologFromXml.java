package tools.java.pats.string.utils.xml;

import net.jcip.annotations.ThreadSafe;

import java.io.Serializable;
import java.security.InvalidParameterException;

/**
 * This class will remove all prolog from beginning of xml string.
 * 
 * @author Pat Keeler
 */
@ThreadSafe
public class RemovePrologFromXml implements Serializable {

    private static final long serialVersionUID = 1951L;

    private static final String LESS_THAN = "<";

    /**
     * Remove the prolog method.
     *
     * @param input xml string
     * @return clean xml
     */
	public String removeProlog(String input) {
		
		if (input.isEmpty()) {
			throw new InvalidParameterException(
					"Input string cannot be null or blank!");
		}

		int index = input.indexOf(LESS_THAN);
		
		if (index == 0) {
			return input;
		} else {
			return input.substring(index);
		}
	}
}
