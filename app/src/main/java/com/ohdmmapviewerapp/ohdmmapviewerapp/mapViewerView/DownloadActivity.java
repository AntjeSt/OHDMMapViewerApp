package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.slider.Slider;
import com.google.android.material.snackbar.Snackbar;
import com.ohdmmapviewerapp.ohdmmapviewerapp.R;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModelFactory;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;


import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polygon;


import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends AppCompatActivity implements MapEventsReceiver {


    private MapView mapView = null;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private static final double DEFAULT_ZOOM = 6;
    private static final double MIN_ZOOM = 4 ;


    MapViewerViewModel mapViewerViewModel;

    FloatingActionButton menuButton;
    FloatingActionButton mylocationButton;
    FloatingActionButton confirmButton;
    FloatingActionButton denyButton;
    SearchView searchView;
    GeoPoint currentLocation;
    MapController mapController;
    Slider slider;
    Polygon rectangle;
    int sizeRectangle;  //meters
    GeoPoint center;
    boolean existingRectangle = false;
    Context ctx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getApplicationContext();
        mapViewerViewModel = MapViewerViewModelFactory.produceMapViewerViewModel(ctx);

        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                    } else {
                        Snackbar.make(mapView, "You won't be able to see your location unless you give the application location access.", Snackbar.LENGTH_SHORT).show();
                    }
        });

        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_download);

        menuButton = findViewById(R.id.menuButton);
        mapView = (MapView) findViewById(R.id.map);

        mapView.setTileSource(TileSourceFactory.MAPNIK);

        this.setUpMapView();


        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DownloadActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        //location button
        mylocationButton = findViewById(R.id.myLocationButton);
            mylocationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        startLocationListener();
                    }
                    else {
                        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
                    }
                }
            });

        // choose box buttons
        denyButton = findViewById(R.id.denyButton);
        confirmButton = findViewById(R.id.confirmButton);
        denyButton.setVisibility(View.GONE);
        confirmButton.setVisibility(View.GONE);

        denyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            List<Overlay> overlay = mapView.getOverlays();
            overlay.subList(1, overlay.size()).clear();
            existingRectangle = !existingRectangle;
            mapView.invalidate();

            // update UI elements
            denyButton.setVisibility(View.GONE);
            confirmButton.setVisibility(View.GONE);
            slider.setVisibility(View.GONE);
            searchView.setVisibility(View.VISIBLE);
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DownloadActivity.this, AddActivity.class);
                ArrayList <GeoPoint> geoPointsBox = (ArrayList<GeoPoint>) rectangle.getPoints();
                intent.putParcelableArrayListExtra("geoPoints", geoPointsBox);
                startActivity(intent);
            }
        });

        slider = findViewById(R.id.slider);
        slider.setVisibility(View.GONE);
        slider.addOnSliderTouchListener(touchListener);

        searchView = findViewById(R.id.searchView);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

           @Override
           public boolean onQueryTextSubmit(String query) {
               mapViewerViewModel.fetchMatchingLocations(query, DownloadActivity.this);
               hideKeyboard(DownloadActivity.this);
               return true;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               return false;
           }
       });

    }

    private void setUpMapView() {
        mapView.setHorizontalMapRepetitionEnabled(true);
        mapView.setVerticalMapRepetitionEnabled(false);
        mapView.setScrollableAreaLimitLatitude(MapView.getTileSystem().getMaxLatitude(), MapView.getTileSystem().getMinLatitude(), 0);
        mapView.getController().setZoom(DEFAULT_ZOOM);
        mapView.setMinZoomLevel(MIN_ZOOM);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this);
        mapView.getOverlays().add(0,mapEventsOverlay);
    }

    private final Slider.OnSliderTouchListener touchListener =
            new Slider.OnSliderTouchListener() {
                @Override
                public void onStartTrackingTouch(Slider slider) {
                }

                @Override
                public void onStopTrackingTouch(Slider slider) {
                    sizeRectangle = (int) slider.getValue()*100;
                    ArrayList<IGeoPoint> iBoxPoints = Polygon.pointsAsRect(center,sizeRectangle,sizeRectangle);
                    ArrayList <GeoPoint> boxPoints = convertIGeoPointToGeoPoint(iBoxPoints);
                    rectangle.setPoints(boxPoints);
                    mapView.getOverlays().add(rectangle);
                    mapView.invalidate();

                }
    };

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationListener();
                } else {
                    Snackbar.make(mapView, "You won't be able to see your location until you give the application location access.", Snackbar.LENGTH_SHORT).show();
                }
                return;
        }
    }


    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
        return false;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {

        if (!existingRectangle){
        existingRectangle = !existingRectangle;

        center = new GeoPoint (p.getLatitude(), p.getLongitude());
        sizeRectangle = determineDefaultSizeRectangleFromZoomLevel();
        ArrayList<IGeoPoint> iBoxPoints = Polygon.pointsAsRect(center,sizeRectangle,sizeRectangle);
        ArrayList <GeoPoint> boxPoints = convertIGeoPointToGeoPoint(iBoxPoints);

        rectangle = this.createRectangle(mapView, boxPoints);
        mapView.getOverlays().add(rectangle);
        mapView.invalidate();
        mapView.getController().setCenter(center);
        mapView.getController().zoomTo(12);
        slider.setVisibility(View.VISIBLE);
        slider.setValue(sizeRectangle);
        denyButton.setVisibility(View.VISIBLE);
        confirmButton.setVisibility(View.VISIBLE);
        searchView.setVisibility(View.GONE);
        }
        return true;
    }

    private Polygon createRectangle(MapView mapView, ArrayList<GeoPoint> boxPoints) {
        rectangle = new Polygon(mapView);
        rectangle.setPoints(boxPoints);
        rectangle.setStrokeColor(Color.RED);
        rectangle.setStrokeWidth(5);
        return rectangle;
    }

    private ArrayList<GeoPoint> convertIGeoPointToGeoPoint(ArrayList <IGeoPoint> iList){
        ArrayList<GeoPoint> geoPointList = new ArrayList<>();
        for (IGeoPoint iGeoPoint : iList){
            geoPointList.add((GeoPoint) iGeoPoint);
        }
        return geoPointList;
    }

    private int determineDefaultSizeRectangleFromZoomLevel(){
        int zoomLevel = (int) mapView.getZoomLevelDouble();
        if (zoomLevel <= 6){
           return 1000;
        }
        else if (zoomLevel <= 8){
            return 750;
        }
        else if (zoomLevel <= 15){
            return 500;
        }
        else {
            return 250;
        }
    }

    public void setSearchViewData(ArrayList<Address> addresses) {
        if (addresses != null){
            if (addresses.size() != 0) {
                AddressDialog addressDialog = new AddressDialog(addresses, DownloadActivity.this);
                addressDialog.show(getSupportFragmentManager(), "addresspopup");
            }
            else {
                Snackbar snackbar = Snackbar.make(mapView,"No results found. Please try another place.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
        else {
            Snackbar snackbar = Snackbar.make(mapView,"No results found. Please try another place.", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public void setAddressMarker(Address address){
        Marker addressLocationMarker = new Marker(mapView);
        mapView.getOverlays().remove(addressLocationMarker);
        GeoPoint addressLocation = new GeoPoint(address.getLatitude(), address.getLongitude());
        if (mapController == null){
            mapController = new MapController(mapView);
        }
        mapController.setCenter(addressLocation);
        mapController.zoomTo(12);
        mapView.getController().animateTo(addressLocation);
        addressLocationMarker = new Marker(mapView);
        addressLocationMarker.setInfoWindow(null);
        addressLocationMarker.setPosition(addressLocation);
        addressLocationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        Drawable addressLocationIcon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_addresslocation_on_24, null);
        addressLocationMarker.setIcon(addressLocationIcon);
        mapView.getOverlays().add(addressLocationMarker);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null){
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void startLocationListener(){
        LocationManager locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if (location != null) {
                    currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
                    mapController = new MapController(mapView);
                    mapController.setCenter(currentLocation);
                    mapController.zoomTo(18);
                    mapView.getController().animateTo(currentLocation);
                    Marker currentLocationMarker = new Marker(mapView);
                    currentLocationMarker.setPosition(currentLocation);
                    currentLocationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    Drawable currentLocationIcon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_person_pin_circle_24, null);
                    currentLocationMarker.setIcon(currentLocationIcon);
                    mapView.getOverlays().add(currentLocationMarker);
                }
            }
        };
        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1000, locationListener);
        }
    }

}