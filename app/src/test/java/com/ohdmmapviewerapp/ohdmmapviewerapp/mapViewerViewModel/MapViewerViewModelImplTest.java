package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;

import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.Map;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.MapViewerModel;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.OHDMMarker;
import com.ohdmmapviewerapp.ohdmmapviewerapp.serverCommunication.ServerCommunication;
import com.ohdmmapviewerapp.ohdmmapviewerapp.serverCommunication.ServerCommunicationFactory;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MapViewerViewModelImplTest {

    DateManager dateManager;
    MapViewerModel mapViewerModel;
    MapViewerViewModelImpl mapViewerViewModel;
    ServerCommunication serverCommunication;
    Map map1;
    Map map2;
    Map map3;
    Map map4;
    Map map5;
    Map map6;
    OHDMMarker marker1;
    OHDMMarker marker2;
    OHDMMarker marker3;
    OHDMMarker marker4;


    @Before
    public void setUp() throws Exception {
        Context context = mock(Context.class);
        dateManager = new DateManagerImpl();
        mapViewerModel = mock(MapViewerModel.class);
        serverCommunication = mock(ServerCommunication.class);
        mapViewerViewModel = MapViewerViewModelImpl.getMapViewerViewModelInstance(context, dateManager, mapViewerModel);
        when(ServerCommunicationFactory.produceServerCommunication(context)).thenReturn(serverCommunication);
        map1 = new Map("Berlin","01-03-2020");
        map2 = new Map("London", "01-03-2020");
        map3 = new Map ("Berlin", "03-03-1920");
        map4 = new Map ("Malta", "04-05-1970");
        map5 = new Map ("Madrid", "06-06-2021");
        map6 = new Map ("Paris", "06-06-2021");
    }

    @Test
    public void orderMapsListAlphabetically(){
       ArrayList<Map> maps = new ArrayList<>();
        maps.add(map1);
        maps.add(map2);
        maps.add(map6);
        maps.add(map4);
        ArrayList<Map> maps2 = new ArrayList<>();
        maps2.add(map1);
        maps2.add(map2);
        maps2.add(map4);
        maps2.add(map6);
        when(mapViewerModel.getMaps()).thenReturn(maps);
        mapViewerViewModel.getAllMapsOrderedAlphabetically();
        assertEquals(maps2, maps);
    }


    @Test
    public void orderFoldersAlphabetically(){
        Set<String>folders = new HashSet();
        folders.add("Berlin");
        folders.add("Beelitz");
        folders.add("Brandenburg");
        folders.add("Paris");
        when(mapViewerModel.getAllFolders()).thenReturn(folders);
        ArrayList<String> foldersOrdered = mapViewerViewModel.getAllFoldersOrderedAlphabetically();
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Beelitz");
        expected.add("Berlin");
        expected.add("Brandenburg");
        expected.add("Paris");
        assertEquals(true, compare(expected, foldersOrdered));
    }

    private boolean compare(ArrayList<String> expected, ArrayList<String> foldersOrdered) {
        boolean same = false;
        for (int i = 0; i < expected.size(); i++){
            if (expected.get(i).equals(foldersOrdered.get(i))){
                same = true;
            }
                else {
                    same = false;
                }
            }
        return same;
    }

}
