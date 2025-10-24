package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import android.content.ComponentName;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before public void initIntents() { Intents.init(); }
    @After  public void releaseIntents() { Intents.release(); }

    private void addCity(String name) {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText(name));
        onView(withId(R.id.button_confirm)).perform(click());
    }

    // 1) Check whether the activity correctly switched
    @Test
    public void switchesToShowActivityOnItemClick() {
        addCity("Edmonton");
        onData(CoreMatchers.anything())
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        intended(hasComponent(new ComponentName(
                "com.example.androiduitesting", ShowActivity.class.getName())));
    }

    // 2) Test whether the city name is consistent
    @Test
    public void cityNameIsConsistentInShowActivity() {
        addCity("Vancouver");
        onData(CoreMatchers.anything())
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        onView(withId(R.id.text_city_name))
                .check(matches(withText("Vancouver")));
    }

    // 3) Test the "back" button
    @Test
    public void backButtonReturnsToMainActivity() {
        addCity("Calgary");
        onData(CoreMatchers.anything())
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        onView(withId(R.id.button_back)).perform(click());
        onView(withId(R.id.button_add)).check(matches(isDisplayed()));
    }
}
