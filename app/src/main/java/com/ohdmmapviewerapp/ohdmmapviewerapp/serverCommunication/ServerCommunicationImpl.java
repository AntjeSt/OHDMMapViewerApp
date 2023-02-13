package com.ohdmmapviewerapp.ohdmmapviewerapp.serverCommunication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.Map;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModelFactory;

public class ServerCommunicationImpl implements ServerCommunication {

    private static ServerCommunicationImpl instance;
    private static MapViewerViewModel mapViewerViewModel;
    private static Context ctx;
    private static final String TAG = "Token";
    private static String token;

    public static ServerCommunicationImpl getServerCommunicationInstance (Context context){
        if (ServerCommunicationImpl.instance == null){
            ServerCommunicationImpl.instance = new ServerCommunicationImpl();
        }
        ServerCommunicationImpl.ctx = context;
        // getToken
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        token = task.getResult();
                        Log.d(TAG, token);
                    }
                });
        return ServerCommunicationImpl.instance;
    }

    @Override
    public void sendFileRequest(String fileName, String date, String wkt) {
        Intent intent = new Intent(ctx, HttpService.class);
        intent.putExtra("fileName", fileName);
        intent.putExtra("date", date);
        intent.putExtra("wkt", wkt);
        intent.putExtra("token", token);
        ctx.startService(intent);
    }

    @Override
    public void downloadMapFile(String mapID) {
        Intent intent = new Intent(ctx, SftpService.class);
        intent.putExtra("map", mapID);
        ctx.startService(intent);
    }

    @Override
    public boolean isConnected(Context ctx) {
        ConnectionObserver connectionObserver = new ConnectionObserver();
        return connectionObserver.isConnected(ctx);
    }

    @Override
    public void fetchMatchingLocations(String searchString) {
        GeoCoder geoCoder = new GeoCoder();
        mapViewerViewModel = MapViewerViewModelFactory.produceMapViewerViewModel(ctx);
        geoCoder.fetchMatchingLocations(searchString, mapViewerViewModel);
    }

}
