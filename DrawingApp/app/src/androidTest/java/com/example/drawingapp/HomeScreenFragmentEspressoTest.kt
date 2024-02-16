package com.example.drawingapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class CanvasEspressoTest {
    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun goToDrawingFragment() {
        //clicks on the imageView
        onView(withId(R.id.imageView)).perform(click())
        //clicks on the new canvas drawing
        onView((withId(R.id.newCanvasButton))).perform(click())
        //checks if Save text is on the current Screen to verify we are on the Drawing Fragment
        onView(withText("Save")).check(matches(isDisplayed()))
    }


    //TODO: Create test: Check new drawing button exists
    //TODO: Create test: Check existing drawing exists
    //TODO: Create test: Navigation test when click on new drawing button
    //TODO: Create test: Navigation test when click on saved drawing
    //TODO: Create test: Open an existing drawing
    //TODO: Create test: Open a new drawing
    //TODO: Create test: ???


}