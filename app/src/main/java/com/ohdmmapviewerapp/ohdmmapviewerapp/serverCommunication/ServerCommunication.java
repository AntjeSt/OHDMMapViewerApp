package com.ohdmmapviewerapp.ohdmmapviewerapp.serverCommunication;

import android.content.Context;

import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.Map;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView.DownloadActivity;

public interface ServerCommunication {

    /** send http request to server
     * @param fileName - fileName for created map = mapID
     * @param date - date of requested map area
     * @param wkt - map area as wkt polygon
     */
    void sendFileRequest(String fileName, String date, String wkt);

    /** download created mapFile to external storage
     * @param mapID - fileName of map file to download
     */
    void downloadMapFile(String mapID);

    /** check internet connection working
     * @param ctx
     */
    boolean isConnected(Context ctx);

    /** start geocoding request for string param
     * @param searchString - location names to search for
     */
    void fetchMatchingLocations (String searchString);
}
