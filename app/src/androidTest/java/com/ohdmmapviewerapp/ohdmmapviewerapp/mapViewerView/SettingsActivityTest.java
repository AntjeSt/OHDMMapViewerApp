package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;

import org.junit.Rule;
import org.junit.Test;

public class SettingsActivityTest {

    @Rule
    public ActivityScenarioRule<ArchiveActivity> activityRule =
            new ActivityScenarioRule<>(ArchiveActivity.class);

    @Test
    public void onClickThemeBackgroundGreen(){
        MapViewerViewModel mapViewerViewModel = mock(MapViewerViewModel.class);
        when(mapViewerViewModel.getTheme()).thenReturn("theme1");

    }

}