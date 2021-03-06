package com.sitadigi.realestatemanager.oldFiles;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars
     * @return
     */
    // 1 euro = 1,01 dolar, in july 2022
    /*public static int convertDollarToEuro(int dollars){
        return (int) Math.round(dollars * 0.812);
    }*/
    public static int convertDollarToEuro(int dollars){
        return (int) Math.round(dollars * 0.99);
    }

    public static double convertEuroToDollar(double euros){
        double dollars = euros * 1.01;
        DecimalFormat df = new DecimalFormat("0.00"); // import java.text.DecimalFormat;
        df.format(dollars);
        return (double)dollars;
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
   /* OLD METHOD
    public static String getTodayDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(new Date());
    }*/
    // NEW METHOD
    public static String getTodayDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context
     * @return
     */
    // OLD METHOD TO CHECK INTERNET AVAILABLE
   /* public static Boolean isInternetAvailable(Context context){
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();
    }*/

    // NEW METHOD TO CHECK INTERNET AVAILABLE
    //Check all connectivity whether available or not
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        //if no network is available networkInfo will be null
        //otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
