package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.ohdmmapviewerapp.ohdmmapviewerapp.R;

import org.junit.Rule;
import org.junit.Test;

public class DownloadActivityTest {


    // relaunches application before each test
    @Rule
    public ActivityScenarioRule<DownloadActivity> activityRule =
            new ActivityScenarioRule<>(DownloadActivity.class);

    // delete shared preferences before test
    @Test
    public void recentsRVsOnlyShowingAfterOpeningMap(){
        onView(withId(R.id.recentsRecyclerView)).check(matches(not(isDisplayed())));
        onView(withId(R.id.allMapsRecyclerView)).perform(scrollTo()).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.menuButton)).perform(click());
        onView(withId(R.id.recentsRecyclerView)).check(matches(isDisplayed()));
    }
}
