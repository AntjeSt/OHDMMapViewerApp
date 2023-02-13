package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.Serializable;

public class OHDMMarker implements Serializable{

    private String id;
    private GeoPoint position;
    private String title;
    private String snippet;
    private String subdescriptionColor;

    public OHDMMarker(String id, GeoPoint position, String title, String snippet, String subdescriptionColor) {
        this.id = id;
        this.position = position;
        this.title = title;
        this.snippet = snippet;
        this.subdescriptionColor = subdescriptionColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GeoPoint getPosition() {
        return position;
    }

    public void setPosition(GeoPoint position) {
        this.position = position;
    }

    public String getSubdescriptionColor() {
        return subdescriptionColor;
    }

    public void setSubdescriptionColor(String subdescriptionColor) {
        this.subdescriptionColor = subdescriptionColor;
    }

}
