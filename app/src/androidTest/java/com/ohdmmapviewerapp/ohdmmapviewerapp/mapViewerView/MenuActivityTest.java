package com.ohdmmapviewerapp.ohdmmapviewerapp.mapViewerView;

import static org.junit.Assert.*;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.ohdmmapviewerapp.ohdmmapviewerapp.R;

import org.junit.Rule;
import org.junit.Test;

public class MenuActivityTest {

    // relaunches application before each test
    @Rule
    public ActivityScenarioRule<MenuActivity> activityRule =
            new ActivityScenarioRule<>(MenuActivity.class);

    @Test
    public void onClickArchiveCardItemsOpenArchiveActivities(){
        onView(withId(R.id.archiveCard)).perform(click());
        onView(withId(R.id.titleArchive)).check(matches(isDisplayed()));
    }

    @Test
    public void onBackButtonArchiveBackToMenu(){
        onView(withId(R.id.archiveCard)).perform(click());
        onView(withId(R.id.titleArchive)).check(matches(isDisplayed()));
        onView(withId(R.id.back)).perform(click());
        onView(withId(R.id.titleMenu)).check(matches(isDisplayed()));
    }
}