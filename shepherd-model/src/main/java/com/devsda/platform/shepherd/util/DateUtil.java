package com.devsda.platform.shepherd.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Date currentDate() {
        return new Date();
    }

    public static String convertDate(Date date) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringifiedDate = formatter.format(date);
        return stringifiedDate;
    }

}
