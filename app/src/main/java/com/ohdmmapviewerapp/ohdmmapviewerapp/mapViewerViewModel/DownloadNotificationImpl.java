package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.ohdmmapviewerapp.ohdmmapviewerapp.R;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView.ArchiveActivity;

public class DownloadNotificationImpl implements DownloadNotification {
    public static final String CHANNEL_ID = "download_channel";
    private Context context;
    private NotificationManager notificationManager;

    public DownloadNotificationImpl(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        this.createNotificationChannel();
    }

    @Override
    public void showNotification() {
        Intent activityIntent = new Intent(context, ArchiveActivity.class);
        PendingIntent activityPendingIntent = PendingIntent.getActivity(
                context,
                1,
                activityIntent,
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) ? PendingIntent.FLAG_IMMUTABLE : 0
        );

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_map_24)
                .setContentTitle("Map downloaded")
                .setContentText("Your Map is now available.")
                .setContentIntent(activityPendingIntent)
                .build();
        notificationManager.notify(1, notification);
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    DownloadNotificationImpl.CHANNEL_ID,
                    "Download Notification",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Notifies about downloaded maps");
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

}