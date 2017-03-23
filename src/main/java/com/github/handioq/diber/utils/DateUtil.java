package com.github.handioq.diber.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Date getFromString(String dateInString, String format) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);

        try {
            date = formatter.parse(dateInString);
            System.out.println("[DateUtil] date: " + date);
            System.out.println("[DateUtil] formattedDate: " + formatter.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

}
