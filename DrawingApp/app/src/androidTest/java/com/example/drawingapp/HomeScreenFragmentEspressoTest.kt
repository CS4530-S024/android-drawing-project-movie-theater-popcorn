package com.example.drawingapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
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
        //clicks on the new canvas drawing
        componentExistCheck(R.id.newCanvasButton)
        clickButton(R.id.newCanvasButton)
        //checks if Save text is on the current Screen to verify we are on the Drawing Fragment
        componentExistCheck(R.id.saveButton)
    }

    //TODO: Create test: Check new drawing button exists
    //TODO: Create test: Check existing drawing exists
    //TODO: Create test: Navigation test when click on new drawing button
    //TODO: Create test: Navigation test when click on saved drawing
    @Test
    fun checkNewDrawingButtonExists() {
        componentExistCheck(R.id.newCanvasButton)
    }
    @Test
    fun checkExistingDrawingExists() {
        componentExistCheck(R.id.existingDrawing)

    }
    @Test
    fun checkNewDrawingButton() {
        clickButton(R.id.newCanvasButton)
        componentExistCheck(R.id.saveButton)
    }
    @Test
    fun checkExistingDrawingButton() {
        TODO("not yet implemented")
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