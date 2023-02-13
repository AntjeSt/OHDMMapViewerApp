package com.ohdmmapviewerapp.ohdmmapviewerapp.serverCommunication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModelFactory;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpService extends Service {

    private static final String SERVERURL = "https://e9f80ec6-e33f-4e16-afd8-6ee6bc49b903.mock.pstmn.io/mapfilerequest";
    private final String TAG = "HttpRequest";


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String fileName = intent.getStringExtra("fileName");
        String date = intent.getStringExtra("date");
        String wkt = intent.getStringExtra("wkt");
        String token = intent.getStringExtra("token");
        this.sendFileRequest(fileName, date, wkt, token);
        return START_STICKY;
    }

    private void sendFileRequest(String fileName, String date, String wkt, String token) {
        Looper looper = Looper.getMainLooper();
        Handler handler = new Handler(looper);

        new Thread(new Runnable() {
            @Override
            public void run(){
                Log.d(TAG, "started request");
                OkHttpClient client = new OkHttpClient();
                RequestBody requestDetails = new FormBody.Builder()
                        .add("FileName", fileName)
                        .add("Date-String", date)
                        .add("WKT-Polygon-String", wkt)
                        .add("Token", token)
                        .build();

                Request request = new Request.Builder()
                        .url(SERVERURL)
                        .post(requestDetails)
                        .build();

                Call call = client.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure( Call call, IOException e) {
                        Log.d(TAG, "failed");
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                MapViewerViewModel mapViewerModel = MapViewerViewModelFactory.produceMapViewerViewModel(getApplicationContext());
                                mapViewerModel.httpRequestFailed(fileName);
                            stopSelf();
                            }
                        });
                    }
                    @Override
                    public void onResponse( Call call, Response response) throws IOException {
                        Log.d(TAG, String.valueOf(response.code()));
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //stopSelf();
                            }
                        });
                    }
                });

            }
        }).start();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}



