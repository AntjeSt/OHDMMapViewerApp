package com.ohdmmapviewerapp.ohdmmapviewerapp.serverCommunication;

import static android.content.Context.CONNECTIVITY_SERVICE;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

public class ConnectionObserver {

    public boolean isConnected(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager)ctx.getSystemService(CONNECTIVITY_SERVICE) ;
        NetworkCapabilities network = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        if (network.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && network.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                && network.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED)){
            return true;
        } else {
            return false;
        }
    }


}
