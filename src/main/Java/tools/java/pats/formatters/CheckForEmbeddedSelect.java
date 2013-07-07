package tools.java.pats.formatters;

import net.jcip.annotations.ThreadSafe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Pat
 * Date: 6/21/13
 * Time: 9:47 PM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class CheckForEmbeddedSelect {


    /** Default constructor */
    public CheckForEmbeddedSelect() {

        super();
    }


    /**
     * boolean method to indicate if string has embedded select statement.
     *
     * @param sql
     * @return boolean true or false
     */
    public boolean isEmbeddedSelect(String sql) {

        Pattern select = Pattern.compile("^([\\( | \\s]*SELECT)");
        Matcher m = select.matcher(sql);
        if (m.find()) {
            return true;
        }

        return false;
    }

}
