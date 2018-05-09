package br.com.efilho.gigigotest.utils;

import android.annotation.SuppressLint;
import android.preference.PreferenceManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    public static String API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static String APP_DATE_FORMAT = "dd/MM/yyyy";

    public static Date convertStringToDate(String dateString, String format){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = formatter.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String convertDateToString(Date date, String format){
        @SuppressLint("SimpleDateFormat")
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }
}
