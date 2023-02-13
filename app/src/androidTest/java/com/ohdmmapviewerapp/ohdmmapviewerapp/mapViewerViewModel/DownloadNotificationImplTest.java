package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.BeforeClass;
import org.junit.Test;

public class DownloadNotificationImplTest {

    static Context appContext;

    @BeforeClass
    public static void createNotificationChannel(){
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void notificationIsShownOnClickArchiveActivityOpens() throws InterruptedException {
        DownloadNotificationImpl downloadNotificationServiceImpl = new DownloadNotificationImpl(appContext);
        downloadNotificationServiceImpl.showNotification();
        Thread.sleep(50000);
        // manual click since espresso doesn't support no view attached actions
    }
}