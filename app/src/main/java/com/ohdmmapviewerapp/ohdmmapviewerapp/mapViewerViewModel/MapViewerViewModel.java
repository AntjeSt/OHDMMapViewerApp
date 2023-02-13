package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel;

import android.app.DatePickerDialog;
import android.content.Context;
import android.location.Address;

import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.Map;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.OHDMMarker;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView.DownloadActivity;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.File;
import java.util.ArrayList;

public interface MapViewerViewModel  {

    /**
     * new map gets created from params and saved
     * @param title
     * @param date
     * @param folder
     */
    Map saveMap (String title, String date, String folder);

    /**
     * new map gets created from params and saved
     * @param title
     * @param date
     */
    Map saveMap (String title, String date);

    /**
     * deletes map with same mapID
     * @param map
     */
    void deleteMap (Map map);

    /**
     * exchanges the current saved map with the changed plant if the user does alterations
     * @param mapID
     * @param title
     * @param folder
     *
     */
    void updateMap(long mapID, String title, String folder);

    /**updates map title
     * @param mapID
     * @param title - new map title
     */
    void updateMap(long mapID, String title);

    /** specify map file Path
     * @param mapID
     * @param storageLocation to save the map file to
     */
    void setMapPath(String mapID, String storageLocation);

    /** get the file Path of inserted map
     * @return map file Path
     */
    String getStorageLocation(String mapID);

    /** gets all maps ordered alphabetically
     * @return all maps
     */
    ArrayList<Map> getAllMapsOrderedAlphabetically();

    /** gets all maps minus the param
     * @param map
     * @return list of osmdroid markers
     */
    ArrayList<Map> getRemainingMapsOrderedAlphabetically(Map map);

    /** deletes folder
     * @param folder
     */
    void deleteFolder (String folder);

    /** updated folder name
     * @param oldFolder - old folder name
     * @param folder - new folder name
     */
    void updateFolder(String oldFolder, String folder);

    /** returns all maps from specified folder
     * @param folder
     * @return maplist with specified folder
     */
    ArrayList<Map> getAllMapsOfFolder(String folder);

    /** returns all folders ordered alphabetically
     * @return folderlist
     */
    ArrayList<String> getAllFoldersOrderedAlphabetically();

    /** saves marker in map
     * @param marker
     * @param map
     */
    void saveMarker(Marker marker, Map map);

    /** deletes marker from map
     * @param marker
     * @param map
     */
    void deleteMarker(Marker marker, Map map);

    /** gets all markers from map
     * @param map
     * @return marker list
     */
    ArrayList<OHDMMarker> getAllOHDMMarkers(Map map);

    /** gets all osmdroid markers of map
     * @param map
     * @param mapView
     */
    ArrayList<Marker> getMarkers(Map map, MapView mapView);

    /**save just openend map
     */
    void addRecentsMap(Map map);

    /** returns last openen map
     * @return last openend map
     */
    Map getMostRecentMap();


    /**gives back all last Viewed Maps max (3)
     * @return list with all last viewed maps
     */
   ArrayList<Map> getRecentMapsList();

    /**returns   number of recent maps
     * @return number of recent maps
     */
    int getRecentMapsListCount();

    /**call to save maplist and recentQueue to storage
     */
    void persistData();

    /**
     * converts returned date from calender to string
     * @return date as string
     */
    String convertDateString(int day, int month, int year);

    /**
     * shows datePickerDialog
     * @return DatePickerDialog for popup calender
     */
    DatePickerDialog getDatePickerDialog(DatePickerDialog.OnDateSetListener dateSetListener, Context ctx);

    /**
     * gets current Date as a string
     * @return date as String
     */
    String getTodaysDate();

    /**get all maps of query param
     * @param query
     * @param downloadActivity - for callback
     */
    void fetchMatchingLocations(String query, DownloadActivity downloadActivity);

    /**loads addresses to AdressDialog
     * @param addresses returned by geocoding
     */
    void setSearchViewData(ArrayList<Address> addresses);

    /** saves sepcified theme
     * @param themeTitle - old folder name
     */
    void setTheme(String themeTitle);

    /**gets saved theme
     * @return themeTitle - old folder name
     */
    String getTheme();

    /**gets saved theme file path
     * @return theme file path
     */
    String getThemeFile();

    /** send request to server to load map in OHDMConverter
     * @param map - to retrieve map file title
     * @param geoPoints of bounding box
     */
    void requestData(Map map, ArrayList<GeoPoint> geoPoints);

    /** download map file from server
     * @param mapID
     */
    void downloadFile(String mapID);

    /** check if internet connection is working
     * @param ctx
     * @return true if internet is working
     */
    boolean isConnected(Context ctx);

    /** show notification to user that map is downloaded
     */
    void showNotification();

    /** delete map if request failed
     * @param mapID of requested map
     */
    void httpRequestFailed(String mapID);
}




