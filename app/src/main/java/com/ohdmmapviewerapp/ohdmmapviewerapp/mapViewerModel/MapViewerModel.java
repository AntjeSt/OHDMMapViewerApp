package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

public interface MapViewerModel {

    /**
     * new map gets saved
     * @param map
     */
    void saveMap(Map map);

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

    /**
     * deletes map with same mapID;
     * @param mapID - in archive
     */
    void deleteMap(long mapID);

    /** retrieve the mapFile of the current map to show in MapViewFragment
     * @param currentMap
     * @return mapfile of the current map
     */
    File getMapFile(Map currentMap);

    /** update maps map file path
     * @param mapID - map to update
     * @param storageLocation - path to map file
     */
    void setMapPath(String mapID, String storageLocation);

    /**gives back all the maps currently saved in the arraylist
     * @return list with all maps from user
     */
    ArrayList<Map> getMaps();

    /**persists mapList
     */
    void saveMapListToFile();

    /** gets map file path of params map
     * @param mapID
     * @return map file path
     */
    String getStorageLocation(String mapID);

    /** returns all maps from specified folder
     * @param folder
     * @return maplist
     */
    ArrayList<Map> getAllMapsOfFolder(String folder);

    /** returns all folders
     * @return all unique folders
     */
    Set<String> getAllFolders();

    /** deletes folder
     * @param folder
     */
    void deleteFolder(String folder);

    /** updated folder name
     * @param oldFolder - old folder name
     * @param folder - new folder name
     */
    void updateFolder(String oldFolder, String folder);

    /**gives back all last Viewed Maps (3)
     * @return list with all last viewed maps
     */
     Map[] getRecentMaps();

    /** returns last Viewed Map
     * @return last viewed map
     */
    Map getMostRecentMap();

    /**returns  number of recent maps
     * @return number of recent maps in recentqueue
     */
    int getRecentMapsCount();

    /** saves map as rrecently opened
     * @param map - just openend map
     */
    void addRecentsMap(Map map);

    /** saves all recent maps
     */
    void saveRecentMaps();

    /** deletes marker from map
     * @param marker
     * @param map
     */
    void deleteMarker(OHDMMarker marker, Map map);

    /** saves marker in map
     * @param marker
     * @param map
     */
    void saveMarker(OHDMMarker marker, Map map);

    /** gets all markers from map
     * @param map
     * @return marker list
     */
    ArrayList<OHDMMarker> getAllOHDMMarkers(Map map);

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
}


