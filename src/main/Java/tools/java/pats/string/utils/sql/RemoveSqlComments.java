package tools.java.pats.string.utils.sql;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.string.utils.GetListOfLines;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

/**
 * Simple class to remove sql commented lines from the input.
 * <p/>
 * Comments removed:
 * <ul>
 * <li>Single line: double dash and pound sign</li>
 * <li>Multi line: open and close curly brace</li>
 * <li>Multi line: slash asterisk combinations</li>
 * </ul>
 * 
 * @author Pat Keeler
 * 
 */
@ThreadSafe
public class RemoveSqlComments implements Serializable {

    private static final long serialVersionUID = 1951L;

	/** Pattern to identify materialized view. */
	private static final Pattern materializedView = Pattern.compile("(^\\s*/\\*\\+)");

	/** Pattern to identify single line comments. */
	private static final Pattern singleLineComment = Pattern.compile("(^\\s*(-{2}|#))");

	/** Pattern to identify end of line comments. */
	private static final Pattern endOfLineComment = Pattern.compile("(/{2}|-{2}|#)");

	/** Pattern to identify multi line comment starts. */
	private static final Pattern multiLineCommentStart = Pattern
			.compile("(^\\s*(/\\*|\\{))");

	/** Pattern to identify multi line comment ends. */
	private static final Pattern multiLineCommentEnd = Pattern.compile("(\\*/|})");

	/**
	 * Default constructor.
	 */
	public RemoveSqlComments() {
		super();
	}

	/**
	 * Takes in an SQL string, converts to a list split by line terminators and
	 * removes comments lines from the list.
	 * 
	 * @param input un-formatted sql
	 * @return String free of comments.
	 */
	public String removeComments(String input, StringBuffer comments) {

		StringBuffer sb = new StringBuffer();

		GetListOfLines getList = new GetListOfLines();
		List<String> lineList = getList.getSplitLines(input);
		Matcher match;
		String line;

        Iterator<String> iter = lineList.iterator();
		while (iter.hasNext()) {
			line = format("%s ", iter.next().toString());
			
            //Look for materialized views and comments
			match = materializedView.matcher(line);
			if (match.find()) {
				//Leave materialized views alone
				sb.append(format(" %s", line.replaceAll("(\\s)+", " ").trim()));
				continue;
			}

			//Remove single line comments.
			match = singleLineComment.matcher(line);
			if (match.find()) {
				if (sb.length() == 0) {
					comments.append(format("%s\n",line));
				}
				continue;
			}

			//Remove trailing comments.
			match = endOfLineComment.matcher(line);
            if (match.find()) {
                line = line.substring(0, match.start());
//                System.out.println(line.substring(0, match.start()));
            }

			// Skip Multi line.
			match = multiLineCommentStart.matcher(line);
			if (match.find()) {
				match = multiLineCommentEnd.matcher(line);
				if (match.find()) {
					if (sb.length() == 0) {
						comments.append(format("%s\n",line));
					}
					continue;
				} else {
					while (!match.find()) {
						if (sb.length() == 0) {
							comments.append(format("%s\n",line));
						}
						if (iter.hasNext()) {
							line = format(" %s ", iter.next().toString().trim());
						} else {
							line = "";
						}
						match = multiLineCommentEnd.matcher(line);
					}
					if (sb.length() == 0) {
						comments.append(format("%s\n",line));
					}
					continue;
				}
			}
			sb.append(format(" %s", line.replaceAll("(\\s)+", " ").trim()));
		}

		return sb.toString();
	}
}
