package tools.java.pats.string.utils.sql;

import net.jcip.annotations.ThreadSafe;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Sort the sql commands by length.  We want the longest lengths at
 * the top of the list.
 * 
 * @author Pat Keeler
 */
@ThreadSafe
public class SortCommandsByLength implements Comparator<String>, Serializable {

    private static final long serialVersionUID = 1951L;

    /**
     * Sort by length.
     *
     * @param cmdA string
     * @param cmdB string
     * @return
     */
	@Override
	public int compare(String cmdA, String cmdB) {
		
		if (cmdA.length() > cmdB.length()) {
			return -1;
		}
		else if(cmdA.length() < cmdB.length()) {
			return 1;
		}
		//Else even
		return 0;
	}

}
