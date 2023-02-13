package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel;

class RecentQueue {

    private static RecentQueue instance;
    private Map[] recentMaps;
    private final int SIZE = 3;
    private int count = 0;


    public RecentQueue() {
        recentMaps = new Map[SIZE];
    }

    public static RecentQueue getLastViewedQueueInstance (){
        if (RecentQueue.instance == null){
            RecentQueue.instance = new RecentQueue();
        }
        return RecentQueue.instance;
    }

    //add a map to the front of the queue
    public void offer(Map map) {
        if (map != null && count != 0){
            poll(map);
        }
        else {
            recentMaps[0] = map;
            count++;
        }
    }

    // add map to the front if it doesn't exist yet, move other elements one position down
    private void poll(Map map) {
        if (count == 1) {
            if (recentMaps[0].getMapID() == (map.getMapID())) {
                recentMaps[0] = map;
            } else {
                recentMaps[1] = recentMaps[0];
                recentMaps[0] = map;
                count++;
            }
        } else if (count >= 2) {
            if (recentMaps[0].getMapID() == map.getMapID()){
                recentMaps[0] = map;
            }
            else if (recentMaps[1].getMapID() == map.getMapID()) {
                recentMaps[1] = recentMaps[0];
                recentMaps[0] = map;
            }
            else {
                recentMaps[2] = recentMaps[1];
                recentMaps[1] = recentMaps[0];
                recentMaps[0] = map;
                if (count == 2) {
                    count++;
                };
            }
        }
    }

    public void deleteMap(Map map){
        for (int i = 0; i < count; i++) {
            if (recentMaps[i].getMapID() == map.getMapID()) {
                    recentMaps[i] = null;
                    if (i != count-1){
                        if (count == 3 && i == 1){
                            recentMaps[1] = recentMaps[2];
                            recentMaps[2] = null;
                        }
                        else if (count == 3 && i == 0){
                            recentMaps[0] = recentMaps[1];
                            recentMaps[1] = recentMaps[2];
                            recentMaps[2] = null;
                        }
                        else if (count == 2 && i == 0){
                            recentMaps[0] = recentMaps[1];
                            recentMaps[1] = null;
                        }
                    }
                    count--;
                }
        }
    }

    public void updateMap(Map map){
        for (int i = 0; i < count; i++) {
            if (recentMaps[i].getMapID() == map.getMapID()) {
                recentMaps[i] = map;
            }
        }
    }

    public Map getMap(int position){
        return recentMaps[position];
    }

    public Map[] getRecentMaps() {
        return recentMaps;
    }

    public void setRecentMaps(Map[] maps){
        recentMaps = maps;
        count = this.getNonNullCount(maps);
    }

    public int getNonNullCount(Map[] maps) {
        int count = 0;
        for(Map map : maps)
            if (map != null)
                count++;
        return count;
    }

    public int getCount() {
        return count;
    }

}