package juust.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Date createUserDate(String date, String time) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + time);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to create date", e);
        }
    }
}
