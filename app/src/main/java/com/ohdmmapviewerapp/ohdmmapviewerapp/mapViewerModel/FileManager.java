package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel;

import java.io.File;
import java.util.ArrayList;

interface FileManager {

    /**
     * get all maps
     * @return the Arraylist of saved maps in the users fileSystem
     */
    ArrayList<Map> getMapList();

    /**
     * saves the current mapList
     * @return true if saved succesfully
     */
    void saveMapList(ArrayList<Map> mapList);

    /**
     * get the map file
     * @param map - title of the mapfile
     * @return the map file of the
     */
    File getMapFile(Map map);

    /**
     * deletes the map to the deleted
     * @param mapPath of the deleted map to find MapFile
     */
    void deleteMapFile(String mapPath);

    /**
     * get "theme" value from sharedPreferences
     */
    String getTheme();

    /**
     * set theme in sharedPreferences
     * @param themeTitle of the deleted map to find MapFile
     */
    void setTheme(String themeTitle);

    /**
     * get theme file Path
     */
    String getThemeFile();

    /**
     * saves recent msps in sharedPreferences
     * @param recentMaps from recentQueue
     */
    void saveRecentMaps(Map[] recentMaps);

    /**
     * get last opened map
     */
    String getMostRecentMap();

    /**
     * get all recent maps from sharedPrefrences
     */
    ArrayList<String> getRecentMaps();
}
