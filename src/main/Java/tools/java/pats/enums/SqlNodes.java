/**
 * SqlNodes
 * 10/7/11 10:45 AM
 * $Id:$
 */
package tools.java.pats.enums;


import java.util.ArrayList;
import java.util.List;


/**
 * SqlNodes
 *
 * List of all Nodes found in Sql Script.
 *
 * Each node equals one command and it's data to be formatted.
 *
 * @author keeler.pat
 * @version 1:$
 */
public enum SqlNodes
{
    ADD("ADD", "Add"),
    ALTER_COLUMN("ALTER COLUMN", "Alter"),
    ALTER_TABLE("ALTER TABLE", "Alter"),
    BETWEEN("BETWEEN", "Between"),
    COMMENT("COMMENT", "Comment"),
    CREATE_DATABASE("CREATE DATABASE", "CreateDatabase"),
    CREATE_INDEX("CREATE INDEX", "CreateIndex"),
    CREATE_UNIQUE_INDEX("CREATE UNIQUE INDEX", "CreateIndex"),
    CREATE_TABLE("CREATE TABLE", "CreateTable"),
    CREATE_VIEW("CREATE VIEW", "CreateView"),
    DELETE("DELETE", "Delete"),
    DELETE_FROM("DELETE FROM", "Delete"),
    DROP("DROP", "Drop"),
    DROP_COLUMN("DROP COLUMN", "Drop"),
    DROP_DATABASE("DROP DATABASE", "Drop"),
    DROP_INDEX("DROP INDEX", "Drop"),
    FROM("FROM", "From"),
    GROUP_BY("GROUP BY", "GroupBy"),
    HAVING("HAVING", "Having"),
    INSERT_INTO("INSERT INTO", "Insert"),
    INNER_JOIN("INNER JOIN", "Join"),
    INTO("INTO", "Into"),
    JOIN("JOIN", "Join"),
    LEFT_JOIN("LEFT JOIN", "Join"),
    LEFT_OUTER_JOIN("LEFT OUTER JOIN", "Join"),
    MODIFY("MODIFY", "Modify"),
    ORDER_BY("ORDER BY", "OrderBy"),
    RENAME_COLUMN("RENAME COLUMN","Rename"),
    RENAME_TO("RENAME TO","Rename"),
    RIGHT_JOIN("RIGHT JOIN", "Join"),
    RIGHT_OUTER_JOIN("RIGHT OUTER JOIN", "Join"),
    SELECT("SELECT", "Select"),
    SELECT_DISTINCT("SELECT DISTINCT", "Select"),
    SELECT_TOP("SELECT TOP", "Select"),
    SET("SET", "Set"),
    TRUNCATE_TABLE("TRUNCATE TABLE", "Truncate"),
    UNION("UNION", "Union"),
    UNION_ALL("UNION ALL", "Union"),
    UPDATE("UPDATE", "Update"),
    VALUES("VALUES", "Values"),
    WHERE("WHERE", "Where"),
    WITH("WITH", "With")
    ;

    // Type represents the Sql Command type.
    public String type;

    // ClassName defines the Class to process this Node type.
    public String className;

    /* Constructor - Node name and it's corresponding
    *   Class name. */
    private SqlNodes(String node, String className) {
        this.type = node;
        this.className = className;
    }

    // Return a <code>List<Node><code> of all possible Nodes.
    public static String[] getTypes() {

        List<String> typeList = new ArrayList<String>();

        for (SqlNodes node : SqlNodes.values()) {
            typeList.add(node.type);
        }

        return typeList.toArray(new String[0]);
    }

    // Return Class name representing the Node type.
    public String getClassName() {

        return className.toString();
    }
}
