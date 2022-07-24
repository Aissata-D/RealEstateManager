package com.sitadigi.realestatemanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.sitadigi.realestatemanager.oldFiles.Utils;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UtilsIntegratedTest {

    @BeforeClass
    public static void beforeClass() throws Exception {

    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertNotNull(appContext);
        assertEquals("com.sitadigi.realestatemanager", appContext.getPackageName());
    }

    @Test
    public void testIsNetworkAvailable() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        boolean isConnected = Utils.isNetworkAvailable(context);

        if (networkInfo != null && networkInfo.isConnected()) {
            assertTrue(isConnected);
        }
        if (networkInfo != null && !networkInfo.isConnected()) {
            assertFalse(isConnected);
        }
    }
}
