package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel;

import org.locationtech.jts.geom.Coordinate;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public interface WKTConverter {

    /** convert the geopoint boundingbox to wkt polygon
     * @return map area as wkt polygon
     */
    String convertBB2WKT(ArrayList<GeoPoint> geoPointsBox);
}
