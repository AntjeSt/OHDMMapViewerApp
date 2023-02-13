package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;

import com.ohdmmapviewerapp.ohdmmapviewerapp.serverCommunication.ServerCommunication;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

public class MapViewerModelImplTest {

    MapViewerModel mapViewerModel;
    FileManager fileManager;
    Context context;
    Map map;
    Map map1;
    Map map2;
    Map map3;
    Map map4;
    Map map5;
    Map map6;
    Map map7;

    @Before
    public void setUp() throws Exception {
    context = mock(Context.class);
    fileManager = mock(FileManager.class);
    map = new Map ("berlin", "10-20-2013", "berlin");
    map1 = new Map ("berlin", "10-20-2013", "Berlin");
    map2 = new Map ("bd", "10-20-2013", "budapest");
    map3 = new Map ("bern", "10-20-2013", "bern");
    map4 = new Map ("mm", "10-20-2013", "paris");
    map5 = new Map ("pp", "10-20-2013", "Paris");
    map6 = new Map ("oo", "10-20-2013", "Paris");
    map7 = new Map ("pb", "10-20-2013", "Berlin");
    }


    @Test
    public void collectMapsWithSameFolder(){
        String folder = "Berlin";
        ArrayList<Map> mapList = new ArrayList<>();
        mapList.add(map);
        mapList.add(map1);
        mapList.add(map3);
        when(fileManager.getMapList()).thenReturn(mapList);
        mapViewerModel = MapViewerModelFactory.produceMapViewerModelTest(context, fileManager);
        ArrayList<Map> folderMaps = mapViewerModel.getAllMapsOfFolder(folder);
        ArrayList<Map> expectedMaps = new ArrayList<>();
        expectedMaps.add(map1);
        assertEquals(expectedMaps, folderMaps);
    }

    @Test
    public void noDuplicatesInFolderListFromAllMaps(){
        ArrayList<Map> mapList = new ArrayList<>();
        mapList.add(map1);
        mapList.add(map5);
        mapList.add(map6);
        when(fileManager.getMapList()).thenReturn(mapList);
        mapViewerModel = MapViewerModelFactory.produceMapViewerModelTest(context, fileManager);
        Set<String> folderMaps = mapViewerModel.getAllFolders();
        int size = folderMaps.size();
        assertEquals(2, size);
    }

    @Test
    public void deletingFolderDeletesMapsOfFolder(){
        ArrayList<Map> mapList = new ArrayList<>();
        mapList.add(map);
        mapList.add(map1);
        mapList.add(map2);
        mapList.add(map7);
        when(fileManager.getMapList()).thenReturn(mapList);
        mapViewerModel = MapViewerModelFactory.produceMapViewerModelTest(context, fileManager);
        mapViewerModel.deleteFolder("Berlin");
        ArrayList<Map> expectedMaps = new ArrayList<>();
        expectedMaps.add(map);
        expectedMaps.add(map2);
        assertEquals(expectedMaps, mapList);
    }

    @Test
    public void updateFolderNameCorrectly(){
        ArrayList<Map> mapList = new ArrayList<>();
        mapList.add(map2);
        mapList.add(map3);
        mapList.add(map4);
        when(fileManager.getMapList()).thenReturn(mapList);
        mapViewerModel = MapViewerModelFactory.produceMapViewerModelTest(context, fileManager);
        mapViewerModel.updateFolder("paris", "Prag");
        assertEquals(true, containsFolder("Prag", mapList));
    }

    public boolean containsFolder(String folder, ArrayList<Map> mapList){
        for (int i = 0; i < mapList.size(); i++){
            if (folder.equals(mapList.get(i).getFolder())) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void onOpenMapInsert2RecentQueue(){
        when(fileManager.getMapFile(map)).thenReturn(null);
        mapViewerModel = MapViewerModelFactory.produceMapViewerModelTest(context, fileManager);
        mapViewerModel.getMapFile(map);
        assertEquals(1, mapViewerModel.getRecentMapsCount());
    }


}