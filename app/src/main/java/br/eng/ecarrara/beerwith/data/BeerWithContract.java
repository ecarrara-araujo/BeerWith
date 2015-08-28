package br.eng.ecarrara.beerwith.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.eng.ecarrara.beerwith.util.Utility;

/**
 * Created by ecarrara on 28/08/2015.
 */
public class BeerWithContract {

    public static final String CONTENT_AUTHORITY = "beerwith.ecarrara.eng";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible Paths
    public static final String PATH_DRINK_WITH = "drink_with";

    // Format used for storing dates in the database.  Also used for converting those strings
    // back into date objects for comparison/processing.
    public static final String DATE_FORMAT = Utility.DATE_FORMAT;

    public static final class DrinkWithEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DRINK_WITH).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_DRINK_WITH;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_DRINK_WITH;

        public static final String TABLE_NAME = "drink_with";

        // Date when you drunk with this person
        public static final String COLUMN_DRINKING_DATE = "drinking_date";

        // Brief description of the beer you drunk
        public static final String COLUMN_BEER_DESC = "beer_desc";

        // Contact URI for the person you drunk with
        public static final String COLUMN_CONTACT_URI = "contact_uri";

        public static Uri buildDrinkWithUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    /**
     * Converts Date class to a string representation, used for easy comparison and database lookup.
     * @param date The input date
     * @return a DB-friendly representation of the date, using the format defined in DATE_FORMAT.
     */
    public static String getDbDateString(Date date){
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }

    /**
     * Converts a dateText to a long Unix time representation
     * @param dateText the input date string
     * @return the Date object
     */
    public static Date getDateFromDb(String dateText) {
        SimpleDateFormat dbDateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return dbDateFormat.parse(dateText);
        } catch ( ParseException e ) {
            e.printStackTrace();
            return null;
        }
    }

}
