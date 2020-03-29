package com.lw.util;

import org.springframework.format.datetime.DateFormatter;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class CommonUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static String getDate(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.setPattern("yyyy-MM-dd");
        return dateFormatter.print(date, Locale.CHINA);
    }

    public static String getDateTime(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.setPattern("yyyy-MM-dd HH:mm:ss");
        return dateFormatter.print(date, Locale.CHINA);
    }

    public static String getDateTime(Date date){
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.setPattern("yyyy-MM-dd HH:mm:ss");
        return dateFormatter.print(date, Locale.CHINA);
    }

}
