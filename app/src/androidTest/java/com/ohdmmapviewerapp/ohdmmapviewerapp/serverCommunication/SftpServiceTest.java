package com.ohdmmapviewerapp.ohdmmapviewerapp.serverCommunication;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;

import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.Map;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModelFactory;

import org.junit.Test;

import java.io.File;

public class SftpServiceTest {

    // Rebex Tiny Sftp Server or similar must be running
    // Internet connection must be available
    // MANAGE_EXTERNAL_STORAGE permission must be given on the emulator/ phone before running this test
    @Test
    public void testSftpConnectionDownloadsFile() throws InterruptedException {
        boolean exists;
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        MapViewerViewModel mapViewerViewModel = MapViewerViewModelFactory.produceMapViewerViewModel(appContext);
        Intent intent = new Intent(appContext, SftpService.class);
        intent.putExtra("map", "2685618858");
        appContext.startService(intent);
        Thread.sleep(20000);
        File file = new File(appContext.getExternalFilesDir("ohdmMapFiles"), "2685618858.map");
        if (file.exists()){
            exists = true;
        }
        else{
            exists = false;
        }
        assertEquals(true, exists);
    }

    @Test
    public void mapFilePathUpdatedOnDownloadFileMapBecomesClickable() throws InterruptedException {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        MapViewerViewModel mapViewerViewModel = MapViewerViewModelFactory.produceMapViewerViewModel(appContext);
        Intent intent = new Intent(appContext, SftpService.class);
        Map map = mapViewerViewModel.saveMap("B","01-02-2020");
        map.setMapID(12345L);
        intent.putExtra("map", map);
        appContext.startService(intent);
    }

}