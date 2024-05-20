package ro.utcn.stack2147483648.utils;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateConverter {

    public static String getCreationDate(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }
}
