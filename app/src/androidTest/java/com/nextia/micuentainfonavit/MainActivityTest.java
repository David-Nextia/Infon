package com.nextia.micuentainfonavit;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.Espresso.*;
import androidx.test.espresso.assertion.ViewAssertions.*;

import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class MainActivityTest {
    @Test
    public void test() {
        ActivityScenario activity= ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.rootView)).check(matches(isDisplayed()));
    }
}