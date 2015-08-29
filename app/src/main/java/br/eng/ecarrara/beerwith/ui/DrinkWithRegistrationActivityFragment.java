package br.eng.ecarrara.beerwith.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import br.eng.ecarrara.beerwith.R;
import br.eng.ecarrara.beerwith.data.BeerWithContract;
import br.eng.ecarrara.beerwith.data.BeerWithContract.DrinkWithEntry;
import br.eng.ecarrara.beerwith.util.Utility;

/**
 * A placeholder fragment containing a simple view.
 */
public class DrinkWithRegistrationActivityFragment extends Fragment {

    private static final String LOG_TAG =
            DrinkWithRegistrationActivityFragment.class.getSimpleName();

    private Uri mContactUri;
    private String mContactName;

    private TextView mContactNameTextView;
    private TextView mBeerDescTextView;
    private Button mSelectContactButton;

    public DrinkWithRegistrationActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View drinkWithRegistrationView = inflater.inflate(
                R.layout.fragment_drink_with_registration, container, false);

        getActivity().setTitle(R.string.drinking_with);

        mSelectContactButton =
                (Button) drinkWithRegistrationView.findViewById(R.id.btn_add_contact);
        mSelectContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceedToContactAssociation();
            }
        });

        Button doneButton =
                (Button) drinkWithRegistrationView.findViewById(R.id.btn_done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDrinkWithEntry();
            }
        });

        mContactNameTextView =
                (TextView) drinkWithRegistrationView.findViewById(R.id.txt_view_contact_name);
        mBeerDescTextView =
                (TextView) drinkWithRegistrationView.findViewById(R.id.edt_txt_beer_info);

        TextView dateTextView =
                (TextView) drinkWithRegistrationView.findViewById(R.id.txt_view_date);
        dateTextView.setText(getFormattedDate());

        return drinkWithRegistrationView;
    }

    private static final int CONTACT_PICKER_RESULT = 1000;

    private void proceedToContactAssociation() {
        Intent contactRequestIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactRequestIntent, CONTACT_PICKER_RESULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    processContactUri(mContactUri = data.getData());
                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
            }
        } else {
            Log.e(LOG_TAG, "There was an error retrieving activity result.");
        }
    }

    private void processContactUri(Uri uri) {
        mContactUri = uri;
        Cursor contactCursor =
                getActivity().getContentResolver().query(uri, null, null, null, null);

        if(null != contactCursor && contactCursor.moveToFirst()) {
            mContactName = contactCursor.getString(
                    contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }

        mSelectContactButton.setVisibility(View.GONE);
        mContactNameTextView.setText(mContactName);
        mContactNameTextView.setVisibility(View.VISIBLE);
    }

    private void saveDrinkWithEntry() {
        Log.d(LOG_TAG, "Saving entry to the DB.");

        ContentValues drinkWithValues = new ContentValues();
        drinkWithValues.put(DrinkWithEntry.COLUMN_BEER_DESC,
                mBeerDescTextView.getText().toString());

        if(null != mContactUri) {
            drinkWithValues.put(DrinkWithEntry.COLUMN_CONTACT_URI, mContactUri.toString());
        } else {
            drinkWithValues.put(DrinkWithEntry.COLUMN_CONTACT_URI, "");
        }

        drinkWithValues.put(DrinkWithEntry.COLUMN_DRINKING_DATE,
                BeerWithContract.getDbDateString(Calendar.getInstance().getTime()));
        Uri returnUri =
                getActivity()
                        .getContentResolver().insert(DrinkWithEntry.CONTENT_URI, drinkWithValues);
        if(returnUri == null) {
            Toast.makeText(getContext(), "Error inserting data on DB", Toast.LENGTH_LONG).show();
        }
        getActivity().finish();
    }

    public String getFormattedDate() {
        return Utility.getDateFormattedTime(Calendar.getInstance(),
                getString(R.string.full_date_format));
    }
}
