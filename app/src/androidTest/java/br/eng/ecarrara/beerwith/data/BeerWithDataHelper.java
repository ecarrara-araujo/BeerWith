package br.eng.ecarrara.beerwith.data;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import br.eng.ecarrara.beerwith.data.BeerWithContract.DrinkWithEntry;

/**
 * Created by ecarrara on 28/08/2015.
 */
public class BeerWithDataHelper {

    public static ContentValues generateBeerWithEntryContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(DrinkWithEntry.COLUMN_BEER_DESC, "Beer");
        cv.put(DrinkWithEntry.COLUMN_DRINKING_DATE, "20150828");
        cv.put(DrinkWithEntry.COLUMN_CONTACT_URI,
                "content://com.android.contacts/contacts/lookup/0r1-412943452B4B4551/1");
        return cv;
    }

    public static boolean loadTestDataIntoDB(Context context, ContentValues contentValues) {
        Uri resultingUri =
                context.getContentResolver().insert(DrinkWithEntry.CONTENT_URI, contentValues);
        return (null == resultingUri);
    }

    public static int cleanTestData(Context context) {
        return context.getContentResolver().delete(
                DrinkWithEntry.CONTENT_URI,
                null,
                null
        );
    }

}
