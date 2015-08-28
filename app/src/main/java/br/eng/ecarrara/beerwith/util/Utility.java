package br.eng.ecarrara.beerwith.util;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.eng.ecarrara.beerwith.R;

/**
 * Created by ecarrara on 28/08/2015.
 */
public class Utility {

    public static final String DATE_FORMAT = "yyyyMMdd";

    public static String getDateFormattedTime(Calendar calendar, String dateFormat) {
        String finalDateString = "";
        try {
            Date inputDate = calendar.getTime();
            SimpleDateFormat requestedDateFormat = new SimpleDateFormat(dateFormat);
            finalDateString = requestedDateFormat.format(inputDate);
        } catch (NullPointerException npex) {
            npex.printStackTrace();
        }
        return finalDateString;
    }

    /**
     * Converts a date string from the format {@link Utility#DATE_FORMAT} to the format
     * defined in {@link br.eng.ecarrara.beerwith.R.string#full_date_format}
     * @param context the current context to be used to look for the localized format
     * @param dateString the date string to be formatted, expected to be of the form specified
     *                in {@link Utility#DATE_FORMAT}
     * @return The day in the form of a string formatted
     */
    public static String getFormattedDate(Context context, String dateString) {
        return getFormattedMonthDay(context.getString(R.string.full_date_format), dateString);
    }

    /**
     * Converts a date string from the format {@link Utility#DATE_FORMAT} to the requested format.
     * @param format desired format to be applied to the dateString
     * @param dateString the date string to be formatted, expected to be of the form specified
     *                in {@link Utility#DATE_FORMAT}
     * @return The day in the form of a string formatted "2014, December 6"
     */
    public static String getFormattedMonthDay(String format, String dateString) {
        String finalDateString = "";
        SimpleDateFormat dbDateFormat = new SimpleDateFormat(Utility.DATE_FORMAT);
        try {
            Date inputDate = dbDateFormat.parse(dateString);
            SimpleDateFormat requestedDateFormat = new SimpleDateFormat(format);
            finalDateString = requestedDateFormat.format(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException npex) {
            npex.printStackTrace();
        }
        return finalDateString;
    }

}
