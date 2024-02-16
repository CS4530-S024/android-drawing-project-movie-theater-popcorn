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
class FragmentTransactionsEspressoTest {
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

    @Test
    fun goBackToHomeScreenFragment() {
        //clicks on the imageView
        onView(withId(R.id.imageView)).perform(click())
        //clicks on the new canvas drawing
        onView((withId(R.id.newCanvasButton))).perform(click())
        //clicks on
        onView((withId(R.id.backButton))).perform(click())
        //checks if we have made it back to the home screen
        onView(withText("Start New Drawing")).check(matches(isDisplayed()))
    }

    @Test
    fun onTheHomeScreenFragment() {
        //clicks on the imageView
        onView(withId(R.id.imageView)).perform(click())
        //checks if we are on the home screen
        onView(withText("Start New Drawing")).check(matches(isDisplayed()))
    }

    //TODO: Create Check splash screen test
}