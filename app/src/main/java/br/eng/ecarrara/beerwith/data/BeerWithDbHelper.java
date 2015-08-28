package br.eng.ecarrara.beerwith.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.eng.ecarrara.beerwith.data.BeerWithContract.DrinkWithEntry;

/**
 * Created by ecarrara on 13/12/2014.
 */
public class BeerWithDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION  = 1;
    public static final String DATABASE_NAME = "drinkwith.db";

    public BeerWithDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_DRINK_WITH_TABLE = "CREATE TABLE " +
                DrinkWithEntry.TABLE_NAME + " (" +
                DrinkWithEntry._ID + " INTEGER PRIMARY KEY, " +
                DrinkWithEntry.COLUMN_DRINKING_DATE + " TEXT NOT NULL " +
                DrinkWithEntry.COLUMN_BEER_DESC + "TEXT NOT NULL" +
                DrinkWithEntry.COLUMN_CONTACT_URI + " TEXT NOT NULL " +
                ") ON CONFLICT IGNORE" +
                ");";

        db.execSQL(SQL_CREATE_DRINK_WITH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }
}
