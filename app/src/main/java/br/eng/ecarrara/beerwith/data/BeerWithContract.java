package br.eng.ecarrara.beerwith.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

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
    public static final String DATE_FORMAT = "yyyyMMdd";

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
        public static final String COLUMN_BEER_DESC = "beer_dec";

        // Contact URI for the person you drunk with
        public static final String COLUMN_CONTACT_URI = "contact_uri";

        public static Uri buildDrinkWithUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

}
