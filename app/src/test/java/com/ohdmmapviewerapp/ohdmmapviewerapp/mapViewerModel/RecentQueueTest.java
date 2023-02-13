package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RecentQueueTest {
    Map berlin;
    Map london;
    Map bukarest;
    Map malta;
    Map madrid;
    Map paris;
    Map bukarest2;
    RecentQueue recentQueue;


    @Before
    public void setUp() {
        berlin = new Map("Berlin","01-03-2020");
        london = new Map("London", "01-03-2020");
        bukarest = new Map ("Bukarest", "03-03-1920");
        malta = new Map ("Malta", "04-05-1970");
        madrid = new Map ("Madrid", "06-06-2021");
        paris = new Map ("Paris", "06-06-2021");

    }

    @After
    public void tearDown(){
    }

    @Test
    public void shouldContainLastThreeMaps() {
        recentQueue = new RecentQueue();
        recentQueue.offer(berlin);
        recentQueue.offer(london);
        recentQueue.offer(bukarest);
        recentQueue.offer(malta);
        RecentQueue expectedQueue = new RecentQueue();
        expectedQueue.offer(london);
        expectedQueue.offer(bukarest);
        expectedQueue.offer(malta);
        assertArrayEquals(expectedQueue.getRecentMaps(), recentQueue.getRecentMaps());
    }

    @Test
    public void headShouldBeMap4() {
        RecentQueue recentQueue = new RecentQueue();
        recentQueue.offer(berlin);
        recentQueue.offer(london);
        recentQueue.offer(bukarest);
        recentQueue.offer(malta);
        assertEquals(malta, recentQueue.getRecentMaps()[0]);
    }

    @Test
    public void sameMapOnIndex2With3Maps() {
        RecentQueue recentQueue = new RecentQueue();
        recentQueue.offer(berlin);
        recentQueue.offer(london);
        recentQueue.offer(bukarest);
        recentQueue.offer(london);
        Map[] expectedQueue = new Map[3];
        expectedQueue[0] = london;
        expectedQueue[1] = bukarest;
        expectedQueue[2] = berlin;
        assertArrayEquals(expectedQueue, recentQueue.getRecentMaps());
    }

    @Test
    public void sameMapOnIndex3With3Maps() {
        RecentQueue recentQueue = new RecentQueue();
        recentQueue.offer(berlin);
        recentQueue.offer(london);
        recentQueue.offer(bukarest);
        recentQueue.offer(berlin);
        Map[] expectedQueue = new Map[3];
        expectedQueue[0] = berlin;
        expectedQueue[1] = bukarest;
        expectedQueue[2] = london;
        assertArrayEquals(expectedQueue, recentQueue.getRecentMaps());
    }

    @Test
    public void sameMapOnIndex2With2Maps() {
        RecentQueue recentQueue = new RecentQueue();
        recentQueue.offer(berlin);
        recentQueue.offer(london);
        recentQueue.offer(berlin);
        Map[] expectedQueue = new Map[3];
        expectedQueue[0] = berlin;
        expectedQueue[1] = london;
        assertArrayEquals(expectedQueue, recentQueue.getRecentMaps());
    }

    @Test
    public void deleteMapOnIndex2With3Maps() {
        RecentQueue recentQueue = new RecentQueue();
        recentQueue.offer(berlin);
        recentQueue.offer(london);
        recentQueue.offer(bukarest);
        recentQueue.deleteMap(london);
        Map[] expectedQueue = new Map[3];
        expectedQueue[0] = bukarest;
        expectedQueue[1] = berlin;
        assertArrayEquals(expectedQueue, recentQueue.getRecentMaps());
    }

    @Test
    public void deleteMapOnIndex1With3Maps() {
        RecentQueue recentQueue = new RecentQueue();
        recentQueue.offer(berlin);
        recentQueue.offer(london);
        recentQueue.offer(bukarest);
        recentQueue.deleteMap(bukarest);
        Map[] expectedQueue = new Map[3];
        expectedQueue[0] = london;
        expectedQueue[1] = berlin;
        assertArrayEquals(expectedQueue, recentQueue.getRecentMaps());
    }

    @Test
    public void updateMapInQueue() {
        RecentQueue recentQueue = new RecentQueue();
        recentQueue.offer(bukarest);
        recentQueue.offer(berlin);
        recentQueue.offer(london);
        bukarest.setTitle("budapest");
        recentQueue.updateMap(bukarest);
        String expectedTitle = "budapest";
        assertEquals(expectedTitle, recentQueue.getMap(2).getTitle());
    }

    @Test
    public void insert2mapsThenDeletedCount0() {
        RecentQueue recentQueue = new RecentQueue();
        recentQueue.offer(berlin);
        recentQueue.offer(london);
        recentQueue.deleteMap(london);
        recentQueue.deleteMap(berlin);
        assertEquals(0, recentQueue.getCount());
    }

    @Test
    public void getNonNullCount(){
        RecentQueue recentQueue = new RecentQueue();
        Map[] maps = new Map[3];
        maps[1] = new Map("Berlin", "21319231");
        assertEquals(1, recentQueue.getNonNullCount(maps));
    }

}