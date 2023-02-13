package com.ohdmmapviewerapp.ohdmmapviewerapp.serverCommunication;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.Map;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModelFactory;

public class SftpService extends Service {

    private final String HOST = "192.168.0.104";
    private final String USERNAME = "tester";
    private final String PASSWORD = "password";
    private final String TAG = "sftpClient";
    private String remoteFile;
    private String storageLocation;
    private MapViewerViewModel mapViewerViewModel;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mapViewerViewModel = MapViewerViewModelFactory.produceMapViewerViewModel(this);
        String mapID = intent.getStringExtra("map");
        downloadMapFile(mapID);
        return START_STICKY;
    }

    private void downloadMapFile(String mapID) {
        Looper looper = Looper.getMainLooper();
        Handler handler = new Handler(looper);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    JSch jsch = new JSch();
                    Session session = jsch.getSession(USERNAME, HOST, 2222);
                    session.setPassword(PASSWORD);
                    session.setConfig("StrictHostKeyChecking", "no");
                    session.connect();
                    Channel channel = session.openChannel("sftp");
                    channel.connect();
                    Log.d(TAG, "connected to server");
                    ChannelSftp sftp = (ChannelSftp) channel;
                    storageLocation = mapViewerViewModel.getStorageLocation(mapID);
                    Log.d(TAG, "Storagelocation:" + storageLocation);
                    remoteFile = mapID + ".map";
                    sftp.get(remoteFile, storageLocation);
                    sftp.exit();
                    session.disconnect();
                    Log.d(TAG, "disconnected from server");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                        mapViewerViewModel.setMapPath(mapID,storageLocation);
                        mapViewerViewModel.showNotification();
                        stopSelf();
                        }
                    });
                } catch (JSchException | SftpException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
