package com.github.nekdenis.simpledict.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.github.nekdenis.simpledict.exception.InternetConnectionException;

public class ConnectionStatus {

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        return i != null && i.isConnected() && i.isAvailable();
    }

    public static void throwExceptionIfConnectionNotAvailible(Context context) throws InternetConnectionException {
        if (!checkInternetConnection(context)) {
            throw new InternetConnectionException("No Internet. Check Internet connection");
        }
    }
}
