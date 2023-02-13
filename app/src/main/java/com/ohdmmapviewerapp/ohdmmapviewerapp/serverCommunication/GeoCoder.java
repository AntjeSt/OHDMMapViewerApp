package com.ohdmmapviewerapp.ohdmmapviewerapp.serverCommunication;

import android.location.Address;
import android.os.Handler;
import android.os.Looper;

import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;

import org.osmdroid.bonuspack.location.GeocoderNominatim;

import java.io.IOException;
import java.util.ArrayList;

class GeoCoder {

    private static final String USERAGENT = "OHDMMapViewerApp";
    private ArrayList<Address> addresses;

    public void fetchMatchingLocations (String searchString, MapViewerViewModel mapViewerViewModel) {
        Looper looper = Looper.getMainLooper();
        Handler handler = new Handler(looper);

        new Thread(new Runnable() {
            @Override
            public void run() {
                GeocoderNominatim geocoder = new GeocoderNominatim(USERAGENT);
                try {
                    addresses = (ArrayList) geocoder.getFromLocationName(searchString, 10);
                }
                catch (IOException e){
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                      mapViewerViewModel.setSearchViewData(addresses);
                    }
                });
            }
        }).start();
    }


}




