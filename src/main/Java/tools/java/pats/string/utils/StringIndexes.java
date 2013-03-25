package tools.java.pats.string.utils;

import net.jcip.annotations.ThreadSafe;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by IntelliJ IDEA.
 *
 * Inner class to hold start and end indexes of a String.
 *
 * User: Pat
 * Date: 12/4/11
 * Time: 7:06 AM
 * To change this template use File | Settings | File Templates.
 */
@ThreadSafe
public class StringIndexes implements Serializable {

    private static final long serialVersionUID = 1951L;


    /* AtomicInteger variables */
    private final AtomicInteger start = new AtomicInteger(0);
    private final AtomicInteger end = new AtomicInteger(0);

    /** Getters and Setters */

    /**
     * Get start.
     * @return start
     */
    public int getStart() {
        return start.get();
    }

    /**
     * Set Start.
     * @param start
     */
    public void setStart(int start) {
        this.start.set(start);
    }

    /**
     * Return end.
     * @return end
     */
    public int getEnd() {
        return end.get();
    }

    /**
     * Set end.
     * @param end
     */
    public void setEnd(int end) {
        this.end.set(end);
    }

}
