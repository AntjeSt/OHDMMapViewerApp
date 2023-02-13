package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.ohdmmapviewerapp.ohdmmapviewerapp.R;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.Map;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

public class MapViewActivityTest {



    // relaunches application before each test
    @Rule
    public ActivityScenarioRule<MapViewActivity> activityRule =
            new ActivityScenarioRule<>(MapViewActivity.class);

    @Test
    public void mapViewShowsDefaultLayoutOnNoMapsOpenendBefore(){
        onView(withId(R.id.actionButton)).check(matches(isDisplayed()));
    }




}