package tools.java.pats.nodes;

import net.jcip.annotations.ThreadSafe;

import java.io.Serializable;
import java.security.InvalidParameterException;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * User: Pat Keeler
 * Date: 11/5/11
 * Time: 9:19 PM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class CreateView extends Node implements Query, Serializable {

    private static final long serialVersionUID = 1951L;

    private static String INVALID_CREATEVIEW_CMD = "CreateView cmd can not be an empty value";
    private static String INVALID_CREATEVIEW_DATA = "CreateView data can not be an empty value";

    private final String cmd;
    private final String data;


    /**
     * Final Argument Constructor.
     *
     * @param cmd
     * @param data
     * @param recursionTab
     * @param userIndentAmount
     * @param selectedStyle
     */
    public CreateView(final String cmd,
                  final String data,
                  final String recursionTab,
                  final String userIndentAmount,
                  final String selectedStyle) {

        super(recursionTab, userIndentAmount, selectedStyle);

        this.cmd = cmd;
        this.data = data;

        if (cmd.isEmpty()) {
            throw new InvalidParameterException(INVALID_CREATEVIEW_CMD);
        }

        if (data.isEmpty()) {
            throw new InvalidParameterException(INVALID_CREATEVIEW_DATA);
        }
    }


    /**
     *
     * @param node
     * @return
     */
    public String processLine(Query node) {

        StringBuffer sb = new StringBuffer();

        //add space to end of data
        String myData = format("%s ", data.trim());

        int ind = myData.indexOf(" AS ");
        //Append the command
        if (ind == -1) {
            throw new InvalidParameterException("Create View is missing AS statement.");
        }
        sb.append(format("\n%s%s %s", tab, cmd.trim(), myData.substring(0, ind).trim()));
        sb.append(format("\n%s%sAS", tab, userIndentTab));

        return sb.toString();
    }
}
