package com.apache.kafka.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CommonEnv {

    // Формат даты 2022-03-17T16:17:41.567
    public static String now(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX");
        return df.format(calendar.getTime());
    }
    // Формат даты 2022-06-16T14:50:33.1083356
    public static String nowFNS(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX");
        return df.format(calendar.getTime());
    }

}
