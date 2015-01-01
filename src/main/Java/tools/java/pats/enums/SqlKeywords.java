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
    AVG("AVG"),
    CASE("CASE"),
    CHECK("CHECK"),
    CONSTRAINTS("CONSTRAINTS"),
    CONTAINS("CONTAINS"),
    COUNT("COUNT"),
    CURRENT_DATE("CURRENT_DATE"),
    CURRENT_TIME("CURRENT_TIME"),
    CURRENT_TIMESTAMP("CURRENT_TIMESTAMP"),
    DESC("DESC"),
    ELSE("ELSE"),
    END("END"),
    EXISTS("EXISTS"),
    FIRST("FIRST"),
    FORMAT("FORMAT"),
    IF("IF"),
    IN("IN"),
    IS("IS"),
    LAST("LAST"),
    LCASE("LCASE"),
    LEN("LEN"),
    LIKE("LIKE"),
    MIN("MIN"),
    MID("MID"),
    MAX("MAX"),
    NOLOCK("NOLOCK"),
    NOT("NOT"),
    NOW("NOW"),
    NULL("NULL"),
	ON("ON"),
    OR("OR"),
    OVER("OVER"),
    ROUND("ROUND"),
    SUM("SUM"),
    THEN("THEN"),
    UCASE("UCASE"),
    WHEN("WHEN");

	private String cmd;
	
	private SqlKeywords(String cmd) {
		this.cmd = cmd;
	}
	
	public static List<String> getCommands() {
		
		List<String> cmdList = new ArrayList<String>();
		
		for (SqlKeywords s : SqlKeywords.values()) {
			cmdList.add(s.cmd);
		}
		
		return cmdList;
		
	}
	
}
