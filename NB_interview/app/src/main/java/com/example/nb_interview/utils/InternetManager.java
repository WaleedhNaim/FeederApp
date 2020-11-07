package com.example.nb_interview.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class InternetManager {

    private static InternetManager instance;

    public static InternetManager getInstance() {
        if (instance == null) {
            instance = new InternetManager();
        }
        return instance;
    }

    public boolean isAvailable(Context context) {
        boolean isOnline;

        if (isNetworkAvailable(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();

                isOnline = (urlc.getResponseCode() == 200);

            } catch (IOException e) {
                isOnline = false;
            }
        } else {
            isOnline = false;
        }


        return isOnline;
    }



    private boolean isNetworkAvailable(Context context) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


}
