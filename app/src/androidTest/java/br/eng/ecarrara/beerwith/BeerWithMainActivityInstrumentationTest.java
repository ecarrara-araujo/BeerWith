package br.eng.ecarrara.beerwith;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.test.espresso.matcher.CursorMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.eng.ecarrara.beerwith.data.BeerWithDataHelper;
import br.eng.ecarrara.beerwith.data.BeerWithContract.DrinkWithEntry;
import br.eng.ecarrara.beerwith.util.Utility;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertTrue;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

/**
 * Created by ecarrara on 28/08/2015.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class BeerWithMainActivityInstrumentationTest {

    Context mContext;

    private static final String BEER_DESC = "Cold Beer";

    @Rule
    public ActivityTestRule<BeerWithMainActivity> mActivityRule = new ActivityTestRule<>(
            BeerWithMainActivity.class);

    @Before
    public void setUp() {
        mContext = getTargetContext();
        BeerWithDataHelper.cleanTestData(mContext);
    }

    @After
    public void tearDown() {
        int rowsDeleted = BeerWithDataHelper.cleanTestData(mContext);
        assertTrue(rowsDeleted > 0);
    }

    @Test
    public void testCreatingABeerWithEntry() throws Exception {

        onView(withId(R.id.add_beer_with)).perform(click());
        onView(withId(R.id.edt_txt_beer_info)).perform(typeText(BEER_DESC));
        onView(withId(R.id.btn_done)).perform(click());

        onData(CursorMatchers.withRowString(DrinkWithEntry.COLUMN_BEER_DESC, BEER_DESC))
                .inAdapterView(withId(R.id.beer_with_list))
                .onChildView(withId(R.id.txt_view_beer_info))
                .check(matches(withText(BEER_DESC)));

    }
}


