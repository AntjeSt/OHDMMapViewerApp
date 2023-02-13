package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel;

import static org.junit.Assert.*;

import org.junit.Test;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class WKTConverterImplTest {

    @Test
    public void convertBB2WKT() {
        GeoPoint geoPoint4 = new GeoPoint(10.00, 40.00);
        GeoPoint geoPoint1 = new GeoPoint(10.00,50.00);
        GeoPoint geoPoint2 = new GeoPoint(12.00, 50.00);
        GeoPoint geoPoint3 = new GeoPoint(12.00,40.00);

        WKTConverterImpl wktConverterImpl = new WKTConverterImpl();
        ArrayList<GeoPoint> geoPoints = new ArrayList<>();
        geoPoints.add(geoPoint1);
        geoPoints.add(geoPoint2);
        geoPoints.add(geoPoint3);
        geoPoints.add(geoPoint4);
        String wkt= wktConverterImpl.convertBB2WKT(geoPoints);
        String expected = "POLYGON ((50 10, 50 12, 40 12, 40 10, 50 10))";
        assertEquals(expected, wkt);
    }

    @Test
    public void convertBB2WKTWithMinusCoordinates() {
        GeoPoint geoPoint2 = new GeoPoint(12.00, 50.00);
        GeoPoint geoPoint1 = new GeoPoint(10.00,50.00);
        GeoPoint geoPoint3 = new GeoPoint(12.00,40.00);
        GeoPoint geoPoint4 = new GeoPoint(10.00, 40.00);
        WKTConverterImpl wktConverterImpl = new WKTConverterImpl();
        ArrayList<GeoPoint> geoPoints = new ArrayList<>();
        geoPoints.add(geoPoint1);
        geoPoints.add(geoPoint2);
        geoPoints.add(geoPoint3);
        geoPoints.add(geoPoint4);
        String wkt= wktConverterImpl.convertBB2WKT(geoPoints);
        String expected = "POLYGON ((50 10, 50 12, 40 12, 40 10, 50 10))";
        assertEquals(expected, wkt);
    }
}