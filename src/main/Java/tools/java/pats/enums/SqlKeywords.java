package tools.java.pats.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Sql commands, not part of SqlNodes.java, to upperCase in
 * a SQL string.
 * 
 * @author Pat Keeler
 */
public enum SqlKeywords {

    ADD("ADD"),
    AND("AND"),
    AS("AS"),
    ASC("ASC"),
    CASE("CASE"),
    CHECK("CHECK"),
    CONSTRAINTS("CONSTRAINTS"),
    DESC("DESC"),
    ELSE("ELSE"),
    END("END"),
    EXISTS("EXISTS"),
    IF("IF"),
    IN("IN"),
    IS("IS"),
    LIKE("LIKE"),
    NOLOCK("NOLOCK"),
    NOT("NOT"),
    NULL("NULL"),
	ON("ON"),
    OR("OR"),
    OVER("OVER"),
    THEN("THEN"),
    WHEN("WHEN");

	private String cmd;
	
	private SqlKeywords(String cmd) {
		this.cmd = cmd;
	}
	
	public static String[] getCommands() {
		
		List<String> cmdList = new ArrayList<String>();
		
		for (SqlKeywords s : SqlKeywords.values()) {
			cmdList.add(s.cmd);
		}
		
		return cmdList.toArray(new String[0]);
		
	}
	
}
