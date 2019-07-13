package tools.java.pats.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum of comment components.
 * <p>
 * This enum handles single or multiple line comment characters of 
 * <ul>
 *   <li><bold>"--"</bold> single line.</li>
 *   <li><bold>"\/*"</bold> multi line start.</li>
 *   <li><bold>"*\/"</bold> multi line end.</li>
 * </ul>
 * The getCommentComponents reutrn a list of all components. 
 *   
 * @author keeler.pat
 *
 */
public enum SqlCommentComponents {
	
	SINGLE_LINE_COMMENT("--"),
	MULTI_LINE_START("/*"),
	MULTI_LINE_END("*/");
	
	private String comment;
	
	SqlCommentComponents(String comment) {
		this.comment = comment;
	}
	
	public String getComment() {
		return this.comment;
	}

	public static String[] getCommentComponents() {
		
		List<String> commentList = new ArrayList<String>();
		
		for (SqlCommentComponents s : SqlCommentComponents.values()) {
			commentList.add(s.comment);
			
		}
		
		return commentList.toArray(new String[commentList.size()]);
	}
}
