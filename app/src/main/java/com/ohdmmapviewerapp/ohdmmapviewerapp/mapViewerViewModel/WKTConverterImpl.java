package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.WKTWriter;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class WKTConverterImpl implements WKTConverter{

    @Override
    public String convertBB2WKT(ArrayList<GeoPoint> geoPointsBox) {
        Coordinate[] polygon = geoPoint2CoordinateArray(geoPointsBox);
        LinearRing ring = new GeometryFactory().createLinearRing(polygon);
        Polygon bbox = new GeometryFactory().createPolygon(ring, null);
        WKTWriter writer = new WKTWriter();
        String wkt = writer.write(bbox);
        return wkt;
    }

    private Coordinate[] geoPoint2CoordinateArray(ArrayList<GeoPoint> geoPointsBox){
        Coordinate[] coordinates = new Coordinate[geoPointsBox.size()+1];
        for (int i = 0; i < geoPointsBox.size()+1; i++) {
           if (i == geoPointsBox.size()){
                GeoPoint geoPoint = geoPointsBox.get(0);
                Coordinate coordinate = new Coordinate(geoPoint.getLongitude(), geoPoint.getLatitude());
                coordinates[i] = coordinate;
            }
            else {
                GeoPoint geoPoint = geoPointsBox.get(i);
                Coordinate coordinate = new Coordinate(geoPoint.getLongitude(), geoPoint.getLatitude());
                coordinates[i] = coordinate;
           }
        }
        return coordinates;
    }

}

