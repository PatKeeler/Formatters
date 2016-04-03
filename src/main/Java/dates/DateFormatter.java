package dates;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Grandpa on 3/14/2016.
 */
public class DateFormatter {

    public static void main(String[] args) {

        long millis = 1455149122057l;

        DateFormatter df = new DateFormatter();
        System.out.println(df.doInvoke(millis));
    }

    public String doInvoke(long millis) {

        Date textDate = new Date(millis);

        Calendar cal = new GregorianCalendar();
        cal.setTime(textDate);

        DateFormat df = new SimpleDateFormat("EEE, MM/dd/yyyy hh:mm a");

        return df.format(textDate);

    }
}
