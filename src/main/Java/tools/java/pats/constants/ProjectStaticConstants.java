package tools.java.pats.constants;

import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Pat
 * Date: 3/23/13
 * Time: 7:27 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectStaticConstants {

    /** Byte variables */
    public static final byte CLOSING_PAREN_BYTE = ')';
    public static final byte OPEN_PAREN_BYTE = '(';

    /** String variables */
    public static final String CLOSING_PAREN_STRING = ")";
    public static final String OPEN_PAREN_STRING = "(";
    public static final String SEMI_COLON = ";";

    /** User integer indents */
    public static final int ZERO_INDENTS = 0;
    public static final int TWO_INDENTS  = 2;
    public static final int FOUR_INDENTS = 4;
    public static final int NINE_INDENTS = 9;

    /** Space string, 160 spaces */
    public static final String SPACES =
            "                                                                                 " +
            "                                                                                 ";


	/** Patterns to identify all line terminators.
     *  \n - unix,
     *  \r\n or \n - windows (\n works most of the time, except for notepad).
     *  \r - apple, recently they are moving to \n.
     */
    public static final Pattern p1 = Pattern.compile(System.getProperty("line.separator"));
	public static final Pattern p2 = Pattern.compile("\n");

}
