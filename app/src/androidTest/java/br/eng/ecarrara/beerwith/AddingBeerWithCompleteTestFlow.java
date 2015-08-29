package br.eng.ecarrara.beerwith;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.eng.ecarrara.beerwith.data.BeerWithDataHelper;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class AddingBeerWithCompleteTestFlow {

    private static final String BASE_APP_PACKAGE = "br.eng.ecarrara.beerwith";
    private static final String MAIN_ACTIVITY_PACKAGE = "br.eng.ecarrara.beerwith.BeerWithMainActivity";
    private static final long LAUNCH_TIMEOUT = 5000L;
    private static final long ACTIVITY_TRANSITION_TIMEOUT = 500L;
    private static final long CROSSAPP_TRANSITION_TIMEOUT = 1000L;

    private static final String BEER_DESC = "Hot Beer";

    private Context mContext;
    private UiDevice mUIDevice;

    @Before
    public void startAppMainActivity() {
        mContext = InstrumentationRegistry.getContext();
        BeerWithDataHelper.cleanTestData(mContext);

        mUIDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        mUIDevice.pressHome();

        // Launcher
        // method from https://github.com/googlesamples/android-testing/
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mUIDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the app
        final Intent intent = mContext.getPackageManager()
                .getLaunchIntentForPackage(BASE_APP_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear any other existing instances
        mContext.startActivity(intent);

        mUIDevice.wait(Until.hasObject(By.pkg(MAIN_ACTIVITY_PACKAGE).depth(0)), LAUNCH_TIMEOUT);

    }

    @After
    public void tearDown() {
        BeerWithDataHelper.cleanTestData(mContext);
    }

    @Test
    public void testAddingBeerWithFlow() throws Exception {

        // add button
        mUIDevice.findObject(
                new UiSelector().resourceId("br.eng.ecarrara.beerwith:id/add_beer_with"))
                .clickAndWaitForNewWindow(ACTIVITY_TRANSITION_TIMEOUT);

        // beer description edit text
        mUIDevice.findObject(
                new UiSelector().resourceId("br.eng.ecarrara.beerwith:id/edt_txt_beer_info")
                        .className("android.widget.EditText"))
                        .setText(BEER_DESC);

        // add contact button
        mUIDevice.findObject(
                new UiSelector().resourceId("br.eng.ecarrara.beerwith:id/btn_add_contact"))
                .clickAndWaitForNewWindow();

        // select contact
        mUIDevice.findObject(
                new UiSelector().className("android.widget.TextView").text("Mano Brou"))
                .click();
        mUIDevice.wait(Until.hasObject(By.pkg(BASE_APP_PACKAGE)), CROSSAPP_TRANSITION_TIMEOUT);

        // check if we have the right contact set
        mUIDevice.findObject(
                new UiSelector().className("android.widget.TextView").text("Mano Brou"));

        // click on done
        mUIDevice.findObject(
                new UiSelector().resourceId("br.eng.ecarrara.beerwith:id/btn_done"))
                .clickAndWaitForNewWindow();

        // check if we have the right name on the next screen
        mUIDevice.findObject(
                new UiSelector().className("android.widget.TextView").text("Mano Brou"));
    }

    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);


        // Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
}
