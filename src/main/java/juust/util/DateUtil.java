package juust.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static Date createUserDate(String date, String time) {
        try {
            return dateTimeFormat.parse(date + " " + time);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to create date", e);
        }
    }

    public static Date createDate(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to create date", e);
        }
    }

    public static String dateToString(Date date) {
        return dateTimeFormat.format(date);
    }
}
