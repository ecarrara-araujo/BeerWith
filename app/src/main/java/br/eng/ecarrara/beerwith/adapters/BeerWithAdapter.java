package br.eng.ecarrara.beerwith.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import br.eng.ecarrara.beerwith.R;
import br.eng.ecarrara.beerwith.data.BeerWithContract.DrinkWithEntry;
import br.eng.ecarrara.beerwith.util.Utility;

/**
 * Created by ecarrara on 28/08/2015.
 */
public class BeerWithAdapter extends CursorAdapter {

    public BeerWithAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_beer_with_list, viewGroup, false);

        BeerWithViewHolder viewHolder = new BeerWithViewHolder(v);
        v.setTag(viewHolder);

        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        BeerWithViewHolder bwvh = (BeerWithViewHolder) view.getTag();

        String beerDesc = cursor.getString(cursor.getColumnIndex(DrinkWithEntry.COLUMN_BEER_DESC));
        bwvh.mBeerDescTextView.setText(beerDesc);

        String date = cursor.getString(cursor.getColumnIndex(DrinkWithEntry.COLUMN_DRINKING_DATE));
        date = Utility.getFormattedDate(view.getContext(), date);
        bwvh.mDateTextView.setText(date);

        String strContactUri = cursor.getString(
                cursor.getColumnIndex(DrinkWithEntry.COLUMN_CONTACT_URI));
        Uri contactUri = Uri.parse(strContactUri);
        String contactName = getContactNameForUri(view.getContext(), contactUri);
        bwvh.mContactNameTextView.setText(contactName);
    }

    public class BeerWithViewHolder {

        public final TextView mBeerDescTextView;
        public final TextView mContactNameTextView;
        public final TextView mDateTextView;

        public BeerWithViewHolder(View itemView) {
            this.mBeerDescTextView = (TextView) itemView.findViewById(R.id.txt_view_beer_info);
            this.mContactNameTextView = (TextView) itemView.findViewById(R.id.txt_view_contact_name);
            this.mDateTextView = (TextView) itemView.findViewById(R.id.txt_view_date);
        }
    }

    private String getContactNameForUri(Context context, Uri uri) {
        String contactName = "Nobody";
        Cursor contactCursor =
                context.getContentResolver().query(uri, null, null, null, null);

        if(null != contactCursor && contactCursor.moveToFirst()) {
            contactName = contactCursor.getString(
                    contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }

        return contactName;
    }
}
