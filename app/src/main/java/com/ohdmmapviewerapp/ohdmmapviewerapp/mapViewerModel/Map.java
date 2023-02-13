package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class Map implements Serializable {

    private String title;
    private String date;
    private String folder;
    private String mapFilePath;
    private long mapID;
    private ArrayList<OHDMMarker> markerList;

    public Map(String title, String date, String folder) {
    this.title = title;
    this.date = date;
    this.folder = folder;
    this.mapID = generateUniqueID();
    }

    public Map(String title, String date) {
        this.title = title;
        this.date = date;
        this.mapID = generateUniqueID();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMapFilePath() {
        return mapFilePath;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public long getMapID() {
        return mapID;
    }

    // testing
    public void setMapID(long mapID){
        this.mapID = mapID;
    }

    public void setMapFilePath(String path) {
        this.mapFilePath = path;
    }

    public ArrayList<OHDMMarker> getMarkerList() {
        return markerList;
    }

    public void setMarkerList(ArrayList<OHDMMarker> markerList) {
        this.markerList = markerList;
    }

    long generateUniqueID(){
        return (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
    }

    @Override
    public String toString() {
        return "Map{" +
                "id='" + mapID + '\'' +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", folder='" + folder + '\'' +
                ", mapPath='" + mapFilePath + '\'' +
                ", markerList=" + markerList +
                '}';
    }

}
