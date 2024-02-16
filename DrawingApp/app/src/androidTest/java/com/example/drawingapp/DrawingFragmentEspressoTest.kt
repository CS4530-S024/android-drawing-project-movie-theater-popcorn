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

    fun goToDrawFrag(){
        //clicks on the imageView
        onView(withId(R.id.imageView)).perform(click())
        //clicks on the new canvas drawing
        onView((withId(R.id.newCanvasButton))).perform(click())
    }

//Checks that all the components exist on the Drawing Fragments
    //TODO: Create test: Navigation to the Drawing fragment
    //TODO: Create test: check the canvas exists
    //TODO: Create test: check if the canvas name is on the drawing Fragment
    //TODO: Create test: check if the eraser button is on the screen
    //TODO: Create test: check back button exists
    //TODO: Create test: check the save button exists
    //TODO: Create test: check the more colors button exists
    //TODO: Create test: check the pen shape button exists
    //TODO: Create test: check the sliders (RGB) for the pen color exist
    //TODO: Create test: check label exists for each color (RGB) slider exist
    //TODO: Create test: check number value label exists for each color (RGB) slider exist
    //TODO: Create test: check the size slider for the pen exists

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
    fun checkDrawingNameExist() {
        goToDrawFrag()
        //TODO: find Canvas id
    }
    @Test
    fun checkEraserButtonExist() {
        goToDrawFrag()
        //TODO: find eraser id
    }

    @Test
    fun checkBackButtonExist() {
        goToDrawFrag()
        //TODO: find eraser id
    }
    @Test
    fun checkSaveButtonExist() {
        goToDrawFrag()
        //TODO: find eraser id
    }

    @Test
    fun checkColorButtonExist() {
        goToDrawFrag()
        //TODO: find eraser id
    }

    @Test
    fun checkPenShapeButtonExist() {
        goToDrawFrag()
        //TODO: find eraser id
    }
    @Test
    fun checkRGBSlidersExist() {
        goToDrawFrag()
        //TODO: find eraser id
    }
    @Test
    fun checkRGBLabelSlidersExist() {
        goToDrawFrag()
        //TODO: find eraser id
    }
    @Test
    fun checkRGBNumberLabelSlidersExist() {
        goToDrawFrag()
        //TODO: find eraser id
    }
    @Test
    fun checkSizeSlidersExist() {
        goToDrawFrag()
        //TODO: find eraser id
    }
    @Test
    fun checkSizeLabelSlidersExist() {
        goToDrawFrag()
        //TODO: find eraser id
    }
    @Test
    fun checkSizeNumberLabelSlidersExist() {
        goToDrawFrag()
        //TODO: find eraser id
    }

//Checks functionality of buttons/sliders on the screen
    //TODO: Create test: check the name of the canvas updates when you click on save and reopen drawing
    //TODO: Create test: check back button takes the user to the home screen
    //TODO: Create test: check pen button let's you change pen shape
    //TODO: Create test: check pen size updates when using slider
    //TODO: Create test: check pen color updates when using sliders
    //TODO: Create test: check eraser updates the canvas
    @Test
    fun drawingNameUpdatesWithSave() {
        goToDrawFrag()
        //TODO: find eraser id
    }
    @Test
    fun backButtonNavigatesToHomeScreen() {
        goToDrawFrag()
        //TODO: find eraser id
    }
    @Test
    fun penShapeUpdates() {
        goToDrawFrag()
        //TODO: find eraser id
    }
    @Test
    fun penSizeUpdates() {
        goToDrawFrag()
        //TODO: find eraser id
    }
    @Test
    fun penColorUpdates() {
        goToDrawFrag()
        //TODO: find eraser id
    }
    @Test
    fun eraserUpdatesCanvas() {
        goToDrawFrag()
        //TODO: find eraser id
    }
}