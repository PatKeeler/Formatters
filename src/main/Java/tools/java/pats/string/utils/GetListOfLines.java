package tools.java.pats.string.utils;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.constants.ProjectStaticConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Return a {@code List<String>} of lines seperated by line terminators.
 * 
 * @author Pat Keeler
 */
@ThreadSafe
public class GetListOfLines implements Serializable, ProjectStaticConstants {

    private static final long serialVersionUID = 1951L;
	

	/**
	 * Default constructor.
	 */
	public GetListOfLines() {
		super();
	}

	
	/**
	 * Split the message on line terminators.
	 * <p/>
	 * Remove lines with control characters only (line.length() == 0).
	 * 
	 * @param input sql string
	 * @return {@code List<String>} of good lines.
	 */
	public List<String> getSplitLines(String input) {
		
		//Split input string on line terminators (systems line separator).
		String[] lines = p1.split(input);

        //If lines.length == 1, try again with p2, see note above.
        if (lines.length == 1) {
            lines = p2.split(input);
        }
		
		List<String> splitList = new ArrayList<String>();
		
		for (String s : lines) {
			if (s.length() > 0) {
				splitList.add(s);
			}
		}
		
		return splitList;
	}
}
