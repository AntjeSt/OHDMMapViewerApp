package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

class MapViewerModelImpl implements MapViewerModel {

    private static MapViewerModelImpl instance;
    private static FileManager fileManager;
    private static RecentQueue recentQueue;
    private static Context ctx;

    private static ArrayList<Map> mapList;

    private MapViewerModelImpl(){}

    public static MapViewerModelImpl getMapViewerModelInstance (Context ctx, FileManager fileManager, RecentQueue recentQueue){
        if (MapViewerModelImpl.instance == null){
            MapViewerModelImpl.instance = new MapViewerModelImpl();
        }
        MapViewerModelImpl.ctx = ctx;
        MapViewerModelImpl.fileManager = fileManager;
        MapViewerModelImpl.recentQueue = recentQueue;
        mapList = fileManager.getMapList();
        return MapViewerModelImpl.instance;
    }


    @Override
    public void saveMap(Map map) {
        if (mapList == null) {
            mapList = new ArrayList<Map>();
            mapList.add(map);
            this.saveMapListToFile();
        }
        else {
            mapList.add(map);
            this.saveMapListToFile();
        }
    }

    @Override
    public void updateMap(long mapID, String title, String folder) {
        for (int i = 0; i < mapList.size(); i++){
            if (mapID == mapList.get(i).getMapID()){
                if (!mapList.get(i).getTitle().equals(title)) {
                    mapList.get(i).setTitle(title);
                }
                mapList.get(i).setFolder(folder);
                break;
            }
        }
        this.saveMapListToFile();
    }

    @Override
    public void updateMap(long mapID, String title) {
        for (int i = 0; i < mapList.size(); i++){
            if (mapID == mapList.get(i).getMapID()){
                if (!mapList.get(i).getTitle().equals(title)) {
                    mapList.get(i).setTitle(title);
                }
                break;
            }
        }
        this.saveMapListToFile();
    }

    @Override
    public void deleteMap(long mapID) {
        for (int i = 0; i < mapList.size(); i++){
            if (mapID == mapList.get(i).getMapID()){
                mapList.remove(i);
                fileManager.deleteMapFile(mapList.get(i).getMapFilePath());
                // delete from queue
                break;
            }
        }
        this.saveMapListToFile();
    }

    @Override
    public ArrayList<Map> getMaps() {
        return mapList;
    }

    @Override
    public Map[] getRecentMaps() {
        this.loadRecentMaps();
        return recentQueue.getRecentMaps();
    }

    private void loadRecentMaps() {
        ArrayList<String> recentMapsStrings = fileManager.getRecentMaps();
        Map[] recentMaps = new Map[3];
        for (int i = 0; i < recentMapsStrings.size(); i++) {
            if (recentMapsStrings.get(i)!= null){
                recentMaps[i] = getMapByID(Long.parseLong(recentMapsStrings.get(i)));
            }
            else {
                recentMaps[i] = null;
            }
        }
        recentQueue.setRecentMaps(recentMaps);
    }

    @Override
    public int getRecentMapsCount() {
        this.loadRecentMaps();
        return recentQueue.getCount();
    }

    @Override
    public void addRecentsMap(Map map) {
        recentQueue.offer(map);
        this.saveRecentMaps();
    }

    @Override
    public void setMapPath(String mapID, String storageLocation) {
            for (int i = 0; i < mapList.size(); i++){
                if (Long.parseLong(mapID) == mapList.get(i).getMapID()){
                    mapList.get(i).setMapFilePath(storageLocation);
                    break;
                }
            }
            this.saveMapListToFile();
    }


    private Map getMapByID(long mapID) {
        for (int i = 0; i < mapList.size(); i++){
            if (mapID == mapList.get(i).getMapID()){
                return mapList.get(i);
            }
        }
        return null;
    }

    @Override
    public void saveRecentMaps() {
        if (recentQueue.getCount()!= 0) {
            fileManager.saveRecentMaps(recentQueue.getRecentMaps());
        }
    }

    @Override
    public String getStorageLocation(String mapID) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            String path = ctx.getExternalFilesDir("ohdmMapFiles") + "/" + mapID + ".map";
            Log.d("sftpClient", path);
            return path;
        } else return null;
    }


    @Override
    public Map getMostRecentMap() {
        String mapID = fileManager.getMostRecentMap();
        if (mapID != null){
            return getMapByID(Long.parseLong(mapID));
        }
        else {
            return null;
        }
    }

    @Override
    public File getMapFile(Map currentMap) {
        recentQueue.offer(currentMap);
        return fileManager.getMapFile(currentMap);
    }

    @Override
    public void deleteMarker(OHDMMarker marker, Map map) {
        for (int i=0; i< mapList.size(); i++){
            if (mapList.get(i).getMapID() == (map.getMapID())){
                for (int k = 0; k < mapList.get(i).getMarkerList().size(); k++){
                    if (marker.getId().equals(mapList.get(i).getMarkerList().get(k).getId())){
                        mapList.get(i).getMarkerList().remove(k);
                        break;
                    }
                };
                break;
            }
        }
    }

    @Override
    public void saveMarker(OHDMMarker marker, Map map) {
        for (int i=0; i< mapList.size(); i++){
            if (map.getMapID() == mapList.get(i).getMapID()){
                if (mapList.get(i).getMarkerList()!= null && !mapList.get(i).getMarkerList().isEmpty()) {
                    for (int k = 0; k < mapList.get(i).getMarkerList().size(); k++) {
                        if (marker.getId().equals(mapList.get(i).getMarkerList().get(k).getId())) {
                            mapList.get(i).getMarkerList().remove(k);
                            mapList.get(i).getMarkerList().add(marker);
                            break;
                        }
                        else{
                            mapList.get(i).getMarkerList().add(marker);
                            break;
                        }
                    }
                    break;
                }
                else if (mapList.get(i).getMarkerList()!= null && mapList.get(i).getMarkerList().isEmpty()) {
                    mapList.get(i).getMarkerList().add(marker);
                }
                else {
                    ArrayList<OHDMMarker> newMarkerList = new ArrayList<>();
                    newMarkerList.add(marker);
                    mapList.get(i).setMarkerList(newMarkerList);
                    break;
                }
            }
        }
    }

    @Override
    public ArrayList<OHDMMarker> getAllOHDMMarkers(Map map) {
        for (int i = 0; i < mapList.size(); i++) {
            if (mapList.get(i).getMapID() == (map.getMapID())) {
                return mapList.get(i).getMarkerList();
            }
        }
        return null;
    }

    @Override
    public ArrayList<Map> getAllMapsOfFolder(String folder) {
        ArrayList<Map> folderMaps = new ArrayList<>();
        for (Map map : mapList){
            if (folder.equals(map.getFolder())){
                folderMaps.add(map);
            }
        }
        return folderMaps;
    }

    @Override
    public Set<String> getAllFolders() {
        if (mapList != null){
        ArrayList<String> allFolders = new ArrayList<>();
        for (Map map: mapList){
            if (map.getFolder() != null) {
                allFolders.add(map.getFolder());
            }
        }
        return new HashSet<>(allFolders);
        }
        else {
            return null;
        }
    }

    @Override
    public void deleteFolder(String folder) {
        mapList.removeIf(map -> folder.equals(map.getFolder()));
    }

    @Override
    public void updateFolder(String oldFolder, String folder) {
        for (Map map: mapList){
            if (oldFolder.equals(map.getFolder())){
                map.setFolder(folder);
            }
        }
    }

    @Override
    public void setTheme(String themeTitle) {
        fileManager.setTheme(themeTitle);
    }

    @Override
    public String getTheme() {
        return fileManager.getTheme();

    }

    @Override
    public String getThemeFile() {
        return fileManager.getThemeFile();
    }

    @Override
    public void saveMapListToFile(){
        if (mapList != null) {
            if (mapList.size() != 0) {
                fileManager.saveMapList(mapList);
            }
        }
    }



}
