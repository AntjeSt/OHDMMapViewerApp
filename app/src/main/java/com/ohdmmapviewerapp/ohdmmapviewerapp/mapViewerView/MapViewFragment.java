package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.ohdmmapviewerapp.ohdmmapviewerapp.R;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.Map;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModelFactory;

import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.android.rendertheme.AssetsRenderTheme;
import org.mapsforge.map.rendertheme.XmlRenderTheme;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.mapsforge.MapsForgeTileProvider;
import org.osmdroid.mapsforge.MapsForgeTileSource;
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.TilesOverlay;
import org.osmdroid.views.overlay.infowindow.InfoWindow;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

import java.io.File;
import java.util.ArrayList;

public class MapViewFragment extends Fragment implements MapEventsReceiver {


    private MapsForgeTileSource tileSource;
    private MapsForgeTileProvider tileProvider;
    private MapViewerViewModel mapViewerViewModel;
    private Context ctx;
    private MapView mapView;
    private Map currentMap;
    private Drawable defaultMarkerIcon;
    private boolean darkMode;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map_view, container, false);
        mapView = (MapView) view.findViewById(R.id.fragmentMapView);
        ctx = getContext();
        mapViewerViewModel = MapViewerViewModelFactory.produceMapViewerViewModel(ctx);
        defaultMarkerIcon = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_baseline_location_on_24, null);
        currentMap = (Map) this.getArguments().getSerializable("map");

        mapView.setUseDataConnection(false);
        AndroidGraphicFactory.createInstance(this.getActivity().getApplication());


        File file = new File (currentMap.getMapFilePath());
        File [] maps = {file};
        tileSource = MapsForgeTileSource.createFromFiles(maps, setRenderTheme(), "renderTheme");
        tileProvider = new MapsForgeTileProvider(
                new SimpleRegisterReceiver(getContext()),
                tileSource, null);
        mapView.setTileProvider(tileProvider);
        Log.d("overlays",mapView.getOverlays().toString());

        this.setUpMapView();

        if (mapViewerViewModel.getAllOHDMMarkers(currentMap) != null) {
            this.loadSavedMarker();
        }

        return view;
    }

    private void setUpMapView() {
        mapView.setMinZoomLevel(10.0);
        mapView.setMultiTouchControls(true);
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        mapView.zoomToBoundingBox(tileSource.getBoundsOsmdroid(), true);
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this);
        if (darkMode) {
            TilesOverlay tilesOverlay = new TilesOverlay(tileProvider,getContext());
            tilesOverlay.setColorFilter(TilesOverlay.INVERT_COLORS);
            mapView.getOverlays().add(0,mapEventsOverlay);
            mapView.getOverlays().add(1,tilesOverlay);
        }
        else {
            mapView.getOverlays().add(0, mapEventsOverlay);
        }

    }

    private void loadSavedMarker() {
        if (mapViewerViewModel.getAllOHDMMarkers(currentMap).size() != 0){
            ArrayList<Marker> allMarkers = mapViewerViewModel.getMarkers(currentMap, mapView);
            for (Marker marker: allMarkers) {
                setUpMarker(marker);
                MarkerInfoWindow markerInfoWindow = setUpInfoWindow(marker);
                mapView.getOverlays().add(marker);
                marker.setInfoWindow(markerInfoWindow);
                markerInfoWindow.setRelatedObject(marker);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        mapViewerViewModel.persistData();
    }

    @Override
    public void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (tileSource != null)
            tileSource.dispose();
        if (tileProvider != null)
            tileProvider.detach();
        mapViewerViewModel.persistData();
        AndroidGraphicFactory.clearResourceMemoryCache();
    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
        InfoWindow.closeAllInfoWindowsOn(mapView);
        return false;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {
        Marker newMarker = new Marker(mapView);
        setUpMarker(newMarker, p);
        MarkerInfoWindow markerInfoWindow = setUpInfoWindow(newMarker);
        newMarker.setInfoWindow(markerInfoWindow);
        //markerInfoWindow.setRelatedObject(newMarker);
        mapView.getOverlays().add(newMarker);
        mapView.invalidate();
        return true;
    }

    private MarkerInfoWindow setUpInfoWindow(Marker marker){

        MarkerInfoWindow infoWindow = new MarkerInfoWindow(R.layout.infowindow, mapView) {

            @Override
            public void onOpen(Object item) {
                InfoWindow.closeAllInfoWindowsOn(mapView);
            }

            @Override
            public void onClose() {
                // save marker inputs to marker
                View infoView = this.getView();
                EditText title = (EditText) infoView.findViewById(R.id.marker_title);
                marker.setTitle(title.getText().toString());
                EditText snippet = (EditText) infoView.findViewById(R.id.marker_snippet);
                marker.setSnippet(snippet.getText().toString());
                mapViewerViewModel.saveMarker(marker, currentMap);
            }
        };

        View infoView = infoWindow.getView();
        EditText title = (EditText) infoView.findViewById(R.id.marker_title);
        TextView longLat = infoView.findViewById(R.id.marker_location);
        EditText snippet = (EditText) infoView.findViewById(R.id.marker_snippet);
        Spinner spinner = infoView.findViewById(R.id.marker_color);

        if (marker.getTitle()!= null){
            title.setText(marker.getTitle());
        }
        if (marker.getSnippet()!= null){
            snippet.setText(marker.getSnippet());
        }
        longLat.setText(marker.getPosition().getLongitude() + " " +  marker.getPosition().getLatitude());

        ColorList colorList = new ColorList(getContext().getApplicationContext());
        ColorSpinnerAdapter adapter = new ColorSpinnerAdapter(getContext().getApplicationContext(), colorList.getColorList(), marker);
        adapter.setDropDownViewResource(R.layout.color_spinner_item);
        spinner.setAdapter(adapter);

        if (marker.getSubDescription() != null){
            int colorObjectPosition = adapter.getColorPosition(marker.getSubDescription());
            spinner.setSelection(colorObjectPosition);
            DrawableCompat.setTint(marker.getIcon(), adapter.getItem(colorObjectPosition).getColor());
        }

        ImageView deleteMarkerButton = infoView.findViewById(R.id.deleteMarkerButton);

        deleteMarkerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoWindow.close();
                marker.remove(mapView);
                mapViewerViewModel.deleteMarker(marker, currentMap);
                Snackbar.make(mapView, "Deleted marker", Snackbar.LENGTH_SHORT).show();
            }
        });

    return infoWindow;
    }

    // set up marker for saved markers
    private void setUpMarker(Marker marker){
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        Drawable iconCopy = defaultMarkerIcon.getConstantState().newDrawable().mutate();
        marker.setIcon(iconCopy);
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                //DrawableCompat.setTint(marker.getIcon(),  ContextCompat.getColor(ctx, R.color.orange));
                marker.getInfoWindow().open(marker,marker.getPosition(),0,-70);
                return true;
            }
        });
    }

    // set up marker for new markers
    private void setUpMarker(Marker marker, GeoPoint p){
        marker.setPosition(p);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                DrawableCompat.setTint(marker.getIcon(),  ContextCompat.getColor(ctx, R.color.orange));
                marker.getInfoWindow().open(marker,marker.getPosition(),0,-70);
                return true;
            }
        });
        Drawable iconCopy = defaultMarkerIcon.getConstantState().newDrawable().mutate();
        marker.setIcon(iconCopy);
    }

    private XmlRenderTheme setRenderTheme() {
        XmlRenderTheme theme = null;
        String renderTheme = mapViewerViewModel.getThemeFile();
        switch (renderTheme) {
            case "null":
                darkMode = false;
                return theme;
            case "darkmode":
                darkMode = true;
                return theme;
            case "rendertheme-v4.xml":
            try {
                theme = new AssetsRenderTheme(getContext().getApplicationContext().getAssets(), "renderthemes/", renderTheme);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return theme;
        }
        return theme;
    }


}