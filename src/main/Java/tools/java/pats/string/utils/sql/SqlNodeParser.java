package tools.java.pats.string.utils.sql;

import net.jcip.annotations.ThreadSafe;
import tools.java.pats.enums.SqlNodes;
import tools.java.pats.nodes.Node;
import tools.java.pats.string.utils.FindIndexOfClosingParen;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;


/**
 * This class breaks the input into nodes of sql commands and
 * their data elements.
 *
 * Example:  "select this from that where you != me" turns into
 * three nodes as follows:
 * 
 * Node: Select
 *     Command: SELECT
 *     data: this
 *
 * Node: From
 *     Command: FROM
 *     data: that

 * Node: Where
 *     Command: WHERE
 *     data: you != me
 *
 * @author Pat Keeler
 */
@ThreadSafe
public class SqlNodeParser implements Serializable {

    private static final long serialVersionUID = 1951L;

	/** The Sql command to break up. */
	private final String sql;

    /** Variables used in construction of Nodes. */
    private final String tab;

    /** Block or expanded format style */
    private final String selectedStyle;

    private final String userIndentAmount;
    
    /** Pattern for "WITH (NOLOCK)", we need to include this in the From
     * statement.  It's not a WITH statement.*/
    Pattern p = Pattern.compile("WITH\\s*\\(NOLOCK\\)");
    Matcher matcher;


    /**
     * Constructor.
     *
     * @param sql
     * @param tab
     * @param userIndentAmount
     */
	public SqlNodeParser(final String sql, final String tab,
                         final String userIndentAmount,
                         final String selectedStyle) {

        //These fields used in Node Constructors.
		this.sql = sql;
        this.tab = tab;
        this.userIndentAmount = userIndentAmount;
        this.selectedStyle = selectedStyle;

	}
    /**
     * Find the components of sql command and return each string in a list.
     * Ignore components if within parenthesis, at this stage.
     *
     * @return List of Nodes
     */
    public List<Node> parseSql() {

        //The list of Nodes found in the Input.
        List<Node> nodeList = new ArrayList<Node>();

        //The start of the data pointer
        int index = 0;

        //The location pointer into the sql string
        int i = 0;

        // List of Enums.
        List<String> sqlEnumList = getSqlEnums();

		//Sort the commands by length.
		Collections.sort(sqlEnumList, new SortCommandsByLength());

        //When we find open paren - jump to closing paren
        FindIndexOfClosingParen findClosingParen = new FindIndexOfClosingParen();

        /**
         * Index through string, break it up into a list of each command and it's data.
         */
        String cmd = "";
        String data = "";
        boolean first = true;

        loop:
		for (i = 0; i < sql.length(); i++) {
			if (sql.charAt(i) == '(') {
				i = findClosingParen.findClosingIndex(i, sql);
			}
            //Parse the sql string looking for commands.
            for (String s : sqlEnumList) {

                //Get sql string to parse, followed by space.
                String cmdSpace = format("%s%s%s", " ", s, " ");

                //Make sure enough string left for this command to exist.
                if (cmdSpace.length() <= sql.substring(i, sql.length()).length()) {
                    if (sql.substring(i).startsWith(cmdSpace)) {
                    	//If it's a "WITH(NOLOCK)" skip - it's part of "FROM".
                    	if (cmdSpace.equals(" WITH ")) {
                        	matcher = p.matcher(sql.substring(i));
                        	if (matcher.find()) {
                        		continue loop;
                        	}
                    	}
                        if (first) {
                            //If first time through there is no data yet.
                            //Just close this "first" gate.
                            first = false;
                        } else {
                            //capture the data string
                            data = sql.substring(index, i);
                            Node node = getNode(cmd, data);
                            nodeList.add(node);
                        }
                        //Capture command and reset pointers
                        cmd = s;
                        i += cmdSpace.length() - 2;
                        index = i + 1;
                        continue loop;
                    }
                    else {
                        //Is command followed by open paren ?
                        String cmdParen = format("%s%s%s", " ", s, "(");
                        if (sql.substring(i).startsWith(cmdParen)) {
                        	//If it's a "WITH(NOLOCK)" skip - it's part of "FROM".
                        	System.out.println(sql.substring(i, i + 13));
                        	if (sql.substring(i).startsWith(" WITH(NOLOCK)")) {
                        		continue loop;
                        	}
                            if (first) {
                            //If first time through there is no data yet.
                            //Just close this "first" gate.
                            first = false;
	                        } else {
	                            //capture the data string
	                            data = sql.substring(index, i);
	                            Node node = getNode(cmd, data);
	                            nodeList.add(node);
	                        }
	                        //Capture command and reset pointers
	                        cmd = s;
	                        i += cmdParen.length() - 1;
	                        index = i;
	                        continue loop;
                        }
                    }
                }
            }
		}

        //See if bad sql string
        if (index == 0) {
            throw new InvalidParameterException(
                    "Invalid Sql String, no Sql commands found!");
        }

        // Get data for last Node.
        if(index < i) {
            //Account for cmd and open paren not seperated with a space
            // i.e. "SELECT("
            if (sql.charAt(index - 1 ) == '(') {
                data = sql.substring(index - 1, i);
            } else {
                data = sql.substring(index, i);
            }
            Node node = getNode(cmd, data);
            nodeList.add(node);
        }

        return nodeList;
    }

    /**
     * Create the Node.
     * 
     * @param cmd
     * @param data
     * 
     * @return node
     */
    private Node getNode(String cmd, String data) {

        SqlNodes sqlNode = SqlNodes.valueOf(cmd.trim().replace(" ", "_"));
        String className = sqlNode.getClassName();

        Class<?> clazz = null;
        try {
            clazz = Class.forName("tools.java.pats.nodes." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Node node = null;
        try {
            Constructor<?> constructor = clazz.getConstructor(String.class,
                                                              String.class,
                                                              String.class,
                                                              String.class,
                                                              String.class);

            node = (Node) constructor.newInstance(cmd, data, tab,
                                                  userIndentAmount, selectedStyle);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return node;
    }

    /**
     * Get list of all the SQL Nodes.
     */

    private List<String> getSqlEnums() {

        List<String> list = new ArrayList<String>();

        list.addAll(Arrays.asList(SqlNodes.getTypes()));

        return list;
    }

}
