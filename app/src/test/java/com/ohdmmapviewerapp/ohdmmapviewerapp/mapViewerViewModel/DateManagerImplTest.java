package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel;

import static org.junit.Assert.*;

import org.junit.Test;

public class DateManagerImplTest {

    @Test
    public void convertDate2String() {
        DateManager dateManager = new DateManagerImpl();
        String date = dateManager.convertDateString(14, 3, 2020);
        assertEquals("14-03-2020", date);
    }

    @Test
    public void mirrorDateString() {
        DateManager dateManager = new DateManagerImpl();
        String mirrored = dateManager.mirrorDateString("10-03-2023");
        String expected = "2023-03-10";
        assertEquals(expected, mirrored);
    }
}