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
        //clicks on the new canvas drawing
        clickButton(R.id.newCanvasButton)
        //checks if Save text is on the current Screen to verify we are on the Drawing Fragment
        componentExistCheck(R.id.saveButton)
    }

    @Test
    fun goBackToHomeScreenFragment() {
        //clicks on the new canvas drawing
        componentExistCheck(R.id.newCanvasButton)
        clickButton(R.id.newCanvasButton)
        //clicks on the back button
        componentExistCheck(R.id.backButton)
        clickButton(R.id.backButton)
        //checks if we have made it back to the home screen
        onView(withText("Start New Drawing")).check(matches(isDisplayed()))
    }

    @Test
    fun onTheHomeScreenFragment() {
        //checks if we are on the home screen
        onView(withText("Start New Drawing")).check(matches(isDisplayed()))
        componentExistCheck(R.id.newCanvasButton)
    }

    /***
     * Helper methods
     */
    private fun clickButton(id: Int){
        onView(withId(id)).perform(click())
    }
    private fun componentExistCheck(id: Int){
        onView(withId(id)).check(matches(isDisplayed()))
    }
}