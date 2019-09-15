package juust.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    public static Date createUserDate(String date, String time) {
        try {
            return format.parse(date + " " + time);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to create date", e);
        }
    }

    public static String dateToString(Date date) {
        return format.format(date);
    }
}
