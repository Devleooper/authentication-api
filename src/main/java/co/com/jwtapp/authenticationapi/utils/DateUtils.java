package co.com.jwtapp.authenticationapi.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss");

    public static String formatDate(Date date) {
        return simpleDateFormat.format(date);
    }
}
