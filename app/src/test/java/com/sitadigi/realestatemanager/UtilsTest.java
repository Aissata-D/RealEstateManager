package com.sitadigi.realestatemanager;

import com.sitadigi.realestatemanager.oldFiles.Utils;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
@RunWith(JUnit4.class )
//@RunWith(RobolectricTestRunner.class)
public class UtilsTest extends TestCase {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testConvertEuroToDollar() {
        final double conversionRateEuroToDollar = 1.01;
        int euro = 1;
        double dollar = Utils.convertEuroToDollar(euro);

        assertEquals(conversionRateEuroToDollar, dollar);
    }

    @Test
    public void testGetTodayDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateExpected = dateFormat.format(new Date());
        String dateActual = Utils.getTodayDate();
        assertEquals(dateExpected, dateActual);
    }
}