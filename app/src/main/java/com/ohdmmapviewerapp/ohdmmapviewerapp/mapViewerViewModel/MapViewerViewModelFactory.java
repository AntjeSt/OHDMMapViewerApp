package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel;

import android.content.Context;

import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.MapViewerModel;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.MapViewerModelFactory;

public class MapViewerViewModelFactory {

    public static MapViewerViewModel produceMapViewerViewModel(Context ctx){
        DateManager dateManager = new DateManagerImpl();
        MapViewerModel mapViewerModel = MapViewerModelFactory.produceMapViewerModel(ctx);
        return MapViewerViewModelImpl.getMapViewerViewModelInstance(ctx, dateManager, mapViewerModel);
    }

    // testing purpose
    public static MapViewerViewModel produceTestMapViewerViewModel (Context ctx, MapViewerModel mapViewerModelMock){
        DateManager dateManager = new DateManagerImpl();
        MapViewerModel mapViewerModel = mapViewerModelMock;
        return MapViewerViewModelImpl.getMapViewerViewModelInstance(ctx, dateManager, mapViewerModel);
    }
}
