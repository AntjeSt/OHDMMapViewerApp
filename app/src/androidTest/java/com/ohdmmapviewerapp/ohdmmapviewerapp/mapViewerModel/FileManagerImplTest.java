package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class FileManagerImplTest {

    Map berlin;
    Map london;
    Map bukarest;
    Map malta;

    @Before
    public void instantiateMaps(){
        berlin = new Map("Berlin","01-03-2020");
        london = new Map("London", "01-03-2020");
        bukarest = new Map ("Bukarest", "03-03-1920");
        malta = new Map ("Malta", "04-05-1970");
    }

    @Test
    public void savedMapListSameAsRetrievedMapList() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        FileManager fileManager = FileManagerImpl.getFileManagerInstance(appContext);
        ArrayList<Map> mapList = new ArrayList<>();
        mapList.add(berlin);
        mapList.add(london);
        mapList.add(bukarest);
        mapList.add(malta);
        fileManager.saveMapList(mapList);
        ArrayList<Map> retrievedMaps = fileManager.getMapList();
        assertEquals(true,compareArrayLists(mapList, retrievedMaps));
    }

    @Test
    public void returnRecentMapsAfterSaving(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        FileManager fileManager = FileManagerImpl.getFileManagerInstance(appContext);
        Map[] maps = new Map[3];
        maps[0] = berlin;
        berlin.setMapID(123L);
        maps[1] = london;
        london.setMapID(456L);
        maps[2] = bukarest;
        bukarest.setMapID(789L);
        fileManager.saveRecentMaps(maps);
        ArrayList<String> expectedMaps = new ArrayList<>();
        expectedMaps.add("123");
        expectedMaps.add("456");
        expectedMaps.add("789");
        ArrayList<String> recentMaps = fileManager.getRecentMaps();
        assertArrayEquals(expectedMaps.toArray(), recentMaps.toArray());
    }

    private boolean compareArrayLists(ArrayList<Map> list1, ArrayList<Map> list2){
        boolean same = false;
            for (int i = 0; i < list1.size(); i++){
                if (list1.get(i).getMapID() == list2.get(i).getMapID() && list1.get(i).getTitle().equals(list2.get(i).getTitle())){
                    same = true;
                }
                else {
                    same = false;
                }
            }
            return same;
    }

    @Test
    public void addmapFilePath(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        MapViewerModel mapViewerModel = MapViewerModelFactory.produceMapViewerModel(appContext);
        mapViewerModel.setMapPath("1195136010","storage/emulated/0/Android/data/com.ohdmmapviewerapp.ohdmmapviewerapp/files/ohdmMapFiles/1195136010.map");
    }

    // first insert a new mapfile via sftpservertest to not disturb other test data
    @Test
    public void deleteMapFile(){
        boolean exists;
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        FileManager fileManager = FileManagerImpl.getFileManagerInstance(appContext);
        fileManager.deleteMapFile("storage/emulated/0/Android/data/com.ohdmmapviewerapp.ohdmmapviewerapp/files/ohdmMapFiles/5299705564.map");
        File file = new File(appContext.getExternalFilesDir("ohdmMapFiles"), "5299705564.map");
        if (file.exists()){
            exists = true;
        }
        else{
            exists = false;
        }
        assertEquals(false,exists);
    }

}