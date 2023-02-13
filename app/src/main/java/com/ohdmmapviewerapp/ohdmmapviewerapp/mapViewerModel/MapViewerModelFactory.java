package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel;

import android.content.Context;

import com.ohdmmapviewerapp.ohdmmapviewerapp.serverCommunication.ServerCommunication;

public class MapViewerModelFactory {

    public static MapViewerModel produceMapViewerModel (Context ctx){
        FileManager fileManager = FileManagerImpl.getFileManagerInstance(ctx);
        RecentQueue recentQueue = RecentQueue.getLastViewedQueueInstance();
        return MapViewerModelImpl.getMapViewerModelInstance(ctx, fileManager, recentQueue);
    }

    // testing purpose ( for mocking fileManager)
    public static MapViewerModel produceMapViewerModelTest (Context ctx, FileManager mockFileManager){
        FileManager fileManager = mockFileManager;
        RecentQueue recentQueue = RecentQueue.getLastViewedQueueInstance();
        return MapViewerModelImpl.getMapViewerModelInstance(ctx, fileManager, recentQueue);
    }


}
