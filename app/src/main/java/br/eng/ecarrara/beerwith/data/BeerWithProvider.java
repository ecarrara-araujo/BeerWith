package br.eng.ecarrara.beerwith.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import br.eng.ecarrara.beerwith.data.BeerWithContract.DrinkWithEntry;

/**
 * Created by ecarrara on 28/08/2015.
 */
public class BeerWithProvider extends ContentProvider {
    private static final String LOG_TAG = BeerWithProvider.class.getSimpleName();
    private BeerWithDbHelper mOpenHelper;

    // Uri match ids
    private static final int DRINK_WITH = 100;
    private static final int DRINK_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = BeerWithContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, BeerWithContract.PATH_DRINK_WITH, DRINK_WITH);
        uriMatcher.addURI(authority, BeerWithContract.PATH_DRINK_WITH + "/#", DRINK_WITH_ID);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new BeerWithDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor returnCursor = null;

        switch (sUriMatcher.match(uri)) {
            case DRINK_WITH:
                returnCursor = mOpenHelper.getReadableDatabase().query(DrinkWithEntry.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case DRINK_WITH_ID:
                returnCursor = mOpenHelper.getReadableDatabase().query(DrinkWithEntry.TABLE_NAME,
                        projection,
                        DrinkWithEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        String type = "";

        switch (match) {
            case (DRINK_WITH):
                type = DrinkWithEntry.CONTENT_TYPE;
                break;
            case (DRINK_WITH_ID):
                type = DrinkWithEntry.CONTENT_ITEM_TYPE;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return type;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri = null;

        switch (match) {
            case DRINK_WITH: {
                long _id = db.insert(DrinkWithEntry.TABLE_NAME, null, contentValues);
                if ( _id > 0 )
                    returnUri = DrinkWithEntry.buildDrinkWithUri(_id);
                else
                    Log.e(LOG_TAG, "Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
            case DRINK_WITH:
                rowsDeleted = db.delete(
                        DrinkWithEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case DRINK_WITH_ID:
                String drinkWithId = Long.toString(ContentUris.parseId(uri));
                rowsDeleted = db.delete(
                        DrinkWithEntry.TABLE_NAME, DrinkWithEntry._ID + " = ?",
                        new String[] { drinkWithId });
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case DRINK_WITH:
                rowsUpdated = db.update(DrinkWithEntry.TABLE_NAME, contentValues,
                        selection, selectionArgs);
                break;
            case DRINK_WITH_ID:
                long _id = ContentUris.parseId(uri);
                rowsUpdated = db.update(DrinkWithEntry.TABLE_NAME, contentValues,
                        "_ID = ?", new String[]{ Long.toString(_id) });
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
