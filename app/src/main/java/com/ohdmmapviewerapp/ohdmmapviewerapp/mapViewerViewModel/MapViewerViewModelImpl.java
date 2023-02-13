package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel;

import android.app.DatePickerDialog;
import android.content.Context;
import android.location.Address;

import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.Map;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.MapViewerModel;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.OHDMMarker;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView.DownloadActivity;
import com.ohdmmapviewerapp.ohdmmapviewerapp.serverCommunication.ServerCommunication;
import com.ohdmmapviewerapp.ohdmmapviewerapp.serverCommunication.ServerCommunicationFactory;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

class MapViewerViewModelImpl implements MapViewerViewModel {

    private static MapViewerViewModelImpl instance;
    private static MapViewerModel mapViewerModel;
    private static DateManager dateManager;
    private static ServerCommunication serverCommunication;
    private static Context ctx;
    private DownloadActivity downloadActivity;

    private MapViewerViewModelImpl(){}

    public static MapViewerViewModelImpl getMapViewerViewModelInstance(Context context, DateManager dateManager, MapViewerModel mapViewerModel){
        if (MapViewerViewModelImpl.instance == null){
            MapViewerViewModelImpl.instance = new MapViewerViewModelImpl();
        }
        MapViewerViewModelImpl.ctx = context;
        MapViewerViewModelImpl.dateManager = dateManager;
        MapViewerViewModelImpl.mapViewerModel = mapViewerModel;
        MapViewerViewModelImpl.serverCommunication = ServerCommunicationFactory.produceServerCommunication(ctx);
        if (mapViewerModel.getTheme() == null){
            mapViewerModel.setTheme("theme1");
        }
        return MapViewerViewModelImpl.instance;
    }


    @Override
    public ArrayList<Map> getRecentMapsList() {
        ArrayList<Map> recents = new ArrayList<>();
        Map[] maps = mapViewerModel.getRecentMaps();
        for (int i = 0; i < maps.length; i++){
            if (maps[i] != null){
                recents.add(maps[i]);
            }
        }
        return recents;
    }

    @Override
    public int getRecentMapsListCount() {
        return mapViewerModel.getRecentMapsCount();
    }


    @Override
    public ArrayList<Map> getAllMapsOrderedAlphabetically() {
        ArrayList<Map> maps = mapViewerModel.getMaps();
        if (maps != null){
        maps.sort(Comparator.comparing(Map::getTitle));
        return maps;}
        return null;
    }

    @Override
    public String convertDateString(int day, int month, int year) {
        return dateManager.convertDateString(day, month, year);
    }

    @Override
    public DatePickerDialog getDatePickerDialog(DatePickerDialog.OnDateSetListener dateSetListener, Context ctx) {
        return dateManager.getDatePickerDialog(dateSetListener, ctx);
    }

    @Override
    public Map saveMap(String title, String date, String folder) {
        Map map = new Map(title, date, folder);
        mapViewerModel.saveMap(map);
        return map;
    }

    @Override
    public Map saveMap(String title, String date) {
        Map map = new Map(title, date);
        mapViewerModel.saveMap(map);
        return map;
    }

    @Override
    public void deleteMap(Map map) {
        mapViewerModel.deleteMap(map.getMapID());
    }

    @Override
    public ArrayList<String> getAllFoldersOrderedAlphabetically() {
        Set<String> folderSet = mapViewerModel.getAllFolders();
        if (folderSet != null){
        ArrayList<String> folders =new ArrayList<>(folderSet);
        folders.sort(String::compareTo);
        return folders;
        }
        else {
            return null;
        }
    }


    @Override
    public void deleteFolder(String folder) {
        mapViewerModel.deleteFolder(folder);
    }

    @Override
    public ArrayList<OHDMMarker> getAllOHDMMarkers(Map map) {
        return mapViewerModel.getAllOHDMMarkers(map);
    }

    @Override
    public void saveMarker(Marker marker, Map map) {
        mapViewerModel.saveMarker(convertMarkerToOHDMMarker(marker), map);
    }

    @Override
    public void deleteMarker(Marker marker, Map map) {
        mapViewerModel.deleteMarker(convertMarkerToOHDMMarker(marker), map);
    }

    @Override
    public String getTodaysDate() {
        return dateManager.getTodaysDate();
    }


    private ArrayList<Marker> convertOHDMMarkerToMarker(ArrayList<OHDMMarker> ohdmMarkers, MapView mapView){
        ArrayList<Marker> allMarkers = new ArrayList<>();
        for (OHDMMarker ohdmMarker :ohdmMarkers) {
            Marker marker = new Marker(mapView);
            marker.setTitle(ohdmMarker.getTitle());
            marker.setSnippet(ohdmMarker.getSnippet());
            marker.setId(ohdmMarker.getId());
            marker.setPosition(ohdmMarker.getPosition());
            marker.setSubDescription(ohdmMarker.getSubdescriptionColor());
            allMarkers.add(marker);
        }
       return allMarkers;
    }

    @Override
    public ArrayList<Marker> getMarkers(Map map, MapView mapView) {
        return convertOHDMMarkerToMarker(getAllOHDMMarkers(map), mapView);
    }

    @Override
    public ArrayList<Map> getRemainingMapsOrderedAlphabetically(Map map) {
        ArrayList<Map> allMaps = getAllMapsOrderedAlphabetically();
        for (int i = 0; i < allMaps.size(); i++){
            if (allMaps.get(i).getMapFilePath() == null || map.getMapID() == allMaps.get(i).getMapID()){
                allMaps.remove(i);
                break;
            }
        }


        return allMaps;
    }

    @Override
    public ArrayList<Map> getAllMapsOfFolder(String folder) {
        return mapViewerModel.getAllMapsOfFolder(folder);
    }

    @Override
    public void updateFolder(String oldFolder, String folder) {
        mapViewerModel.updateFolder(oldFolder, folder);
    }

    @Override
    public void updateMap(long mapID, String title, String folder) {
        mapViewerModel.updateMap(mapID, title, folder);
    }

    @Override
    public void updateMap(long mapID, String title) {
        mapViewerModel.updateMap(mapID,title);
    }

    @Override
    public void fetchMatchingLocations(String query, DownloadActivity downloadActivity) {
        this.downloadActivity = downloadActivity;
        serverCommunication.fetchMatchingLocations(query);
    }

    @Override
    public void setSearchViewData(ArrayList<Address> addresses) {
        downloadActivity.setSearchViewData(addresses);
    }

    @Override
    public void setTheme(String themeTitle) {
        mapViewerModel.setTheme(themeTitle);
    }

    @Override
    public String getTheme() {
        return mapViewerModel.getTheme();
    }

    @Override
    public String getThemeFile() {
        return mapViewerModel.getThemeFile();
    }

    private OHDMMarker convertMarkerToOHDMMarker(Marker marker){
        String id;
        if(marker.getId() != null){
            id = marker.getId(); }
        else{
            id = getUniqueid();
            marker.setId(id);
        }
        return new OHDMMarker(id, marker.getPosition(), marker.getTitle(), marker.getSnippet(), marker.getSubDescription());
    }

    private String getUniqueid(){
        long idLong = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        return "M" + idLong;
    }

    @Override
    public void persistData(){
        mapViewerModel.saveMapListToFile();
        mapViewerModel.saveRecentMaps();
    }

    @Override
    public void addRecentsMap(Map map) {
        mapViewerModel.addRecentsMap(map);
    }

    @Override
    public Map getMostRecentMap(){
       return mapViewerModel.getMostRecentMap();
    }

    @Override
    public void requestData(Map map, ArrayList<GeoPoint> boundingbox) {
        WKTConverterImpl wktConverterImpl = new WKTConverterImpl();
        String wkt = wktConverterImpl.convertBB2WKT(boundingbox);
        String date = dateManager.mirrorDateString(map.getDate());
        String fileName = String.valueOf(map.getMapID());
        serverCommunication.sendFileRequest(fileName, date, wkt);
    }

    @Override
    public void downloadFile (String mapID){
        serverCommunication.downloadMapFile(mapID);
    }

    @Override
    public void setMapPath(String mapID, String storageLocation) {
        mapViewerModel.setMapPath(mapID, storageLocation);
    }

    @Override
    public void showNotification() {
        DownloadNotification downloadNotification = new DownloadNotificationImpl(ctx);
        downloadNotification.showNotification();
    }

    @Override
    public String getStorageLocation(String mapID) {
        return mapViewerModel.getStorageLocation(mapID);
    }

    @Override
    public void httpRequestFailed(String mapID) {
        mapViewerModel.deleteMap(Long.parseLong(mapID));

    }

    @Override
    public ArrayList<Map> getRecentMaps() {
        mapViewerModel.getRecentMaps();
        //
        return null;
    }

    public boolean isConnected(Context ctx){
        return serverCommunication.isConnected(ctx);
    }

}