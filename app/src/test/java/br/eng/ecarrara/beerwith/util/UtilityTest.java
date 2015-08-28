package br.eng.ecarrara.beerwith.util;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by ecarrara on 28/08/2015.
 */
public class UtilityTest {

    @Test
    public void testGetDateFormattedTime() throws Exception {
        String expectedDateFormat = "yyyy, MMMM dd";

        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, 4 - 1, 3); //20150403

        String expectedResult = (new SimpleDateFormat(expectedDateFormat)).format(calendar.getTime());
        String result = Utility.getDateFormattedTime(calendar, expectedDateFormat);

        assertEquals("Date was not correctly formatted.", expectedResult, result);

        // testing a second format to be sure
        expectedDateFormat = "dd MM yyyy";
        expectedResult = (new SimpleDateFormat(expectedDateFormat)).format(calendar.getTime());
        result = Utility.getDateFormattedTime(calendar, expectedDateFormat);
        assertEquals("Date was not correctly formatted.", expectedResult, result);
    }
}