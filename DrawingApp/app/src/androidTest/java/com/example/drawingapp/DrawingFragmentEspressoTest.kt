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

    fun createNewDrawing(){
        goToDrawFrag()
        //checks if Save text is on the current Screen to verify we are on the Drawing Fragment
        onView(withText("My Drawing")).check(matches(isDisplayed()))
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
        //checks if Save text is on the current Screen to verify we are on the Drawing Fragment
        createNewDrawing()
    }

    @Test
    fun checkCanvasExist() {
        createNewDrawing()
        //TODO: find Canvas id
    }
    @Test
    fun checkNewDrawingNameExist() {
        createNewDrawing()
        onView(withText("My Drawing")).check(matches(isDisplayed()))

    }
    @Test
    fun checkExistingDrawingNameExist() {
        //TODO: add steps for click on an existing drawing
        //onView(withId(R.id.imageName)).check(matches(isDisplayed()))

    }
    @Test
    fun checkEraserButtonExist() {
        createNewDrawing()
        onView(withText("eraser")).check(matches(isDisplayed()))
    }

    @Test
    fun checkBackButtonExist() {
        createNewDrawing()
        onView(withText("Back to Main")).check(matches(isDisplayed()))
    }
    @Test
    fun checkSaveButtonExist() {
        createNewDrawing()
        onView(withText("Save")).check(matches(isDisplayed()))

    }

    @Test
    fun checkColorButtonExist() {
        createNewDrawing()
        onView(withText("More Colors")).check(matches(isDisplayed()))
    }

    @Test
    fun checkPenShapeButtonExist() {
        createNewDrawing()
        onView(withText("Pen")).check(matches(isDisplayed()))
    }
    @Test
    fun checkRGBSlidersExist() {
        createNewDrawing()

        //TODO: find RGB slider id
    }
    @Test
    fun checkRGBLabelSlidersExist() {
        createNewDrawing()
        //TODO: find RGB slider label text
    }
    @Test
    fun checkRGBNumberLabelSlidersExist() {
        createNewDrawing()
        //TODO: find RGB slider number label text
    }
    @Test
    fun checkSizeSlidersExist() {
        createNewDrawing()
        //TODO: find size slider id
    }
    @Test
    fun checkSizeLabelSlidersExist() {
        createNewDrawing()
        //TODO: find size label text
    }
    @Test
    fun checkSizeNumberLabelSlidersExist() {
        createNewDrawing()
        //TODO: find size value label text
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
        createNewDrawing()
        //TODO: have existing drawings
    }
    @Test
    fun backButtonNavigatesToHomeScreen() {
        createNewDrawing()
        onView(withText("Back To Main")).perform(click())
        onView(withText("newCanvasButton")).check(matches(isDisplayed()))
    }
    @Test
    fun penShapeUpdates() {
        createNewDrawing()
//        val oldPenSize =
        //TODO: perform action to check value of pen
    }
    @Test
    fun penSizeUpdates() {
        createNewDrawing()

        //TODO: find eraser id
    }
    @Test
    fun penColorUpdates() {
        createNewDrawing()
        //TODO: find eraser id
    }
    @Test
    fun eraserUpdatesCanvas() {
        createNewDrawing()
        //TODO: find eraser id
    }
}