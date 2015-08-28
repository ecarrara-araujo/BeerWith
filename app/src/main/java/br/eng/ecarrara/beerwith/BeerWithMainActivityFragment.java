package br.eng.ecarrara.beerwith;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.eng.ecarrara.beerwith.adapters.BeerWithAdapter;
import br.eng.ecarrara.beerwith.data.BeerWithContract.DrinkWithEntry;

/**
 * A placeholder fragment containing a simple view.
 */
public class BeerWithMainActivityFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = BeerWithMainActivityFragment.class.getSimpleName();
    private static final int BEER_WITH_LOADER = 0;

    private static final String[] BEER_WITH_COLUMNS = {
            DrinkWithEntry._ID,
            DrinkWithEntry.COLUMN_CONTACT_URI,
            DrinkWithEntry.COLUMN_BEER_DESC,
            DrinkWithEntry.COLUMN_DRINKING_DATE
    };

    private BeerWithAdapter mBeerWithAdapter;

    private ListView mBeerWithListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beer_with_main, container, false);

        mBeerWithAdapter = new BeerWithAdapter(getContext(), null, 0);

        mBeerWithListView = (ListView) rootView.findViewById(R.id.beer_with_list);
        mBeerWithListView.setAdapter(mBeerWithAdapter);

        View emptyView = rootView.findViewById(R.id.empty);
        mBeerWithListView.setEmptyView(emptyView);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(BEER_WITH_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(BEER_WITH_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                DrinkWithEntry.CONTENT_URI,
                BEER_WITH_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mBeerWithAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mBeerWithAdapter.swapCursor(null);
    }
}
