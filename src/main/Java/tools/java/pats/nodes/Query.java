package tools.java.pats.nodes;

/**
 * Created with IntelliJ IDEA.
 * User: Pat
 * Date: 6/21/13
 * Time: 5:49 PM
 *
 * This interface must be implemented by all subclasses of Node.
 *
 * This interface allows the creation of Node subclasses through
 * reflection.  See <code>SqlNodeParser</code> for implementation details.
 */
public interface Query {

    public String processLine(Query node);

}
