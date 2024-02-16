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
class DrawingFragmentEspressoTest {
    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    //TODO: update test below it is just a placeholder

    fun goToDrawFrag(){
        //clicks on the imageView
        onView(withId(R.id.imageView)).perform(click())
        //clicks on the new canvas drawing
        onView((withId(R.id.newCanvasButton))).perform(click())
    }
    @Test
    fun goToDrawingFragment() {
        goToDrawFrag()
        //checks if Save text is on the current Screen to verify we are on the Drawing Fragment
        onView(withText("My Drawing")).check(matches(isDisplayed()))
    }

    @Test
    fun checkCanvasExist() {
        goToDrawFrag()
        //TODO: find Canvas id
    }

    @Test
    fun checkPenButtonExist() {
        goToDrawFrag()
        //checks button name
        onView(withId(R.id.penButton)).check(matches(withText("Pen")))
    }

    @Test
    fun checkColorButtonExist() {
        goToDrawFrag()
        //checks button name
        onView(withId(R.id.penButton)).check(matches(withText("Pen")))
    }

//Checks that all the components exist on the Drawing Fragments
    //TODO: Create test: check if the canvas name is on there
    //TODO: Create test: check if the eraser button is on the screen
    //TODO: Create test: check back button exists
    //TODO: Create test: check the save button exists
    //TODO: Create test: check the more colors button exists
    //TODO: Create test: check the sliders (RGB) for the pen color exist
    //TODO: Create test: check the size slider for the pen exists
    //TODO: Create test: check the canvas exists

//Checks functionality of buttons/sliders on the screen
    //TODO: Create test: check the name of the canvas updates when you click on save and reopen drawing
    //TODO: Create test: check back button takes the user to the home screen
    //TODO: Create test: check pen button let's you change pen shape
    //TODO: Create test: check pen size updates when using slider
    //TODO: Create test: check pen color updates when using sliders
    //TODO: Create test: check eraser updates the canvas

}