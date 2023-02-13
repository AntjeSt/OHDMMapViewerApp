package com.ohdmmapviewerapp.ohdmmapviewerapp.serverCommunication;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModelFactory;

import java.util.Map;

public class FireBaseMessagingService extends FirebaseMessagingService {

    private final String TAG= "FirebaseMessage";

    @Override
    public void onMessageReceived(RemoteMessage message) {
       Map<String, String> messageData =  message.getData();
       Log.d(TAG, String.valueOf(messageData));
       MapViewerViewModel mapViewerViewModel = MapViewerViewModelFactory.produceMapViewerViewModel(this.getApplicationContext());
       Log.d(TAG, messageData.get("mapID"));
       mapViewerViewModel.downloadFile(messageData.get("mapID"));
    }

}
