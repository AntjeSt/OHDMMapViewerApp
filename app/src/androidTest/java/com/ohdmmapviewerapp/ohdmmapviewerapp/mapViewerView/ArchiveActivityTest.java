package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.widget.ListView;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.ohdmmapviewerapp.ohdmmapviewerapp.R;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.Map;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.MapViewerModel;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerModel.MapViewerModelFactory;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModel;
import com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerViewModel.MapViewerViewModelFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class ArchiveActivityTest {


    // test mapfile from test/resources must be on emulator in location:
    // data/com.ohdmmapviewerapp.ohdmmapviewerapp/files
    // + according MapFiles from test/resources in location:
    // storage/emulated/0/Android/data/com.mohdmmapviewerapp.ohmmapviewerapp/files/ohdmMapFiles/



    // relaunches application before each test
    @Rule
    public ActivityScenarioRule<ArchiveActivity> activityRule =
            new ActivityScenarioRule<>(ArchiveActivity.class);

    // delete shared preferences before test
    @Test
    public void recentsRVsOnlyShowingAfterOpeningMap(){
        onView(withId(R.id.recentsRecyclerView)).check(matches(not(isDisplayed())));
        onView(withId(R.id.allMapsRecyclerView)).perform(scrollTo()).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.menuButton)).perform(click());
        onView(withId(R.id.recentsRecyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void recentsRVNotShownWhenNoMapsOpenend(){
        onView(withId(R.id.recentsRecyclerView)).check(matches(not(isDisplayed())));
    }

    @Test
    public void displaysFolderRv(){
        onView(withId(R.id.foldersRecyclerView)).check(matches(isDisplayed()));
    }
    @Test
    public void displaysAllMapsRv(){
        onView(withId(R.id.allMapsRecyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void mapWithPathIsClickable(){
        onView(withId(R.id.allMapsRecyclerView)).perform(scrollTo()).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.menuButton)).check(matches(isDisplayed()));
    }

    @Test
    public void lastAddItemOpensDownloadActivity(){
        onView(withId(R.id.allMapsRecyclerView)).perform(scrollTo()).perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        onView(withId(R.id.searchView)).check(matches(isDisplayed()));
    }











}