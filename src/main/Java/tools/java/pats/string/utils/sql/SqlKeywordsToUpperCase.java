package tools.java.pats.string.utils.sql;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.enums.SqlKeywords;
import tools.java.pats.enums.SqlNodes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

/**
 * This class upperCases all sql commands.
 * 
 * @author Pat Keeler
 */
@ThreadSafe
public class SqlKeywordsToUpperCase implements Serializable {

    private static final long serialVersionUID = 1951L;


	public String upperCaseKeywords(String sql) {

		StringBuffer sb = new StringBuffer(format(" %s ",sql));
		
		List<String> cmdList = getUpperCaseCommands();

		Collections.sort(cmdList, new SortCommandsByLength());

		Pattern cmdPat;
		Matcher m;
		String upper = sb.toString().toUpperCase();
		
		for (String s : cmdList) {
			cmdPat = Pattern.compile(("(\\s+|\\()") + (s) + ("(\\s+|\\(|\\))"));
			m = cmdPat.matcher(upper);

			while (m.find()) {
				sb.replace(m.start(), m.end(), m.group());
			}
		}
		
		return sb.toString();
	}

	/**
	 * Create and return a list of all the sql reserved words.
	 * 
	 * @return {@code List<String>}
	 */
	public List<String> getUpperCaseCommands() {
		
		List<String> commands = new ArrayList<String>();
		
		//Add all commands.
        commands.addAll(SqlNodes.getTypes());
		commands.addAll(SqlKeywords.getCommands());

		return commands;
		
	}
}
