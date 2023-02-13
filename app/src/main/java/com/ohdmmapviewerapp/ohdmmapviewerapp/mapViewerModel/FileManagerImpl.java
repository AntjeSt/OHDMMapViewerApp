package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import org.mapsforge.map.reader.MapFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

class FileManagerImpl implements FileManager{

    private static FileManager instance;
    private static Context ctx;
    private static final String FILENAME = "mapList.txt";
    private static final String SHARED_PREFERENCES = "sharedPreferencesOHDM";
    private SharedPreferences sharedPreferences;

    private FileManagerImpl() {}

    public static FileManager getFileManagerInstance (Context ctx){
        if (FileManagerImpl.instance == null){
            FileManagerImpl.instance = new FileManagerImpl();
        }
        FileManagerImpl.ctx = ctx;
        return FileManagerImpl.instance;
    }

    @Override
    public ArrayList<Map> getMapList() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        ArrayList<Map> maps = null;
        Object obj = null;

        File file = new File(ctx.getFilesDir(),FILENAME);
        if(file.exists()){
            try {
                fis = ctx.openFileInput(FILENAME);
                ois = new ObjectInputStream(fis);
                obj = ois.readObject();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (ois != null) {
                    try {
                        ois.close();
                        maps = (ArrayList<Map>) obj;
                        return maps;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        return maps;
        }

        else{
            return null;
        }
    }

    // to internal storage
    @Override
    public void saveMapList(ArrayList<Map> mapList) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = ctx.openFileOutput(FILENAME, ctx.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(mapList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {if (oos != null) {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }
    }


    @Override
    public File getMapFile(Map map) {
        String fileName = map.getMapFilePath();
        return new File (ctx.getExternalFilesDir(null)+ "/" + fileName + ".map");
    }

    @Override
    public void deleteMapFile(String mapPath) {
        File file = new File(mapPath);
        if (file.exists()){
            file.delete();
        }
    }

    @Override
    public String getTheme() {
        sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString("theme",null);
    }

    @Override
    public void setTheme(String themeTitle) {
        sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (themeTitle){
            case "theme1":
                editor.putString("theme", "theme1");
                editor.commit();
                break;
            case "theme2":
                editor.putString("theme", "theme2");
                editor.commit();
                break;
            case "theme3":
                editor.putString("theme", "theme3");
                editor.commit();
                break;

        }

    }

    @Override
    public String getThemeFile() {
        switch (getTheme()) {
            case "theme1":
                return "rendertheme-v4.xml";
            case "theme2":
                return "default";
            case"theme3":
                return "darkmode";
        }
        return null;
    }

    @Override
    public void saveRecentMaps(Map[] recentMaps) {
        sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i =0 ; i < recentMaps.length;i++){

            if (i==0 && recentMaps[0]!= null) {
                    editor.putString("map1", String.valueOf(recentMaps[0].getMapID()));
                    editor.commit();
            }
            if (i == 1 && recentMaps[1]!= null){
                editor.putString("map2",String.valueOf(recentMaps[1].getMapID()));
                editor.commit();
            }
            if (i== 2 && recentMaps[2]!= null){
                editor.putString("map3",String.valueOf(recentMaps[2].getMapID()));
                editor.commit();
            }
        }

    }

    @Override
    public String getMostRecentMap() {
        sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString("map1",null);
    }

    @Override
    public ArrayList<String> getRecentMaps() {
        ArrayList<String> recentMaps = new ArrayList<>();
        sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String map1 = sharedPreferences.getString("map1",null);
        if (map1 != null){
            recentMaps.add(map1);
        }
        String map2 = sharedPreferences.getString("map2",null);
        if (map2 != null){
            recentMaps.add(map2);
        }
        String map3 = sharedPreferences.getString("map3",null);
        if (map3 != null){
            recentMaps.add(map3);
        }
        return recentMaps;
    }

}
