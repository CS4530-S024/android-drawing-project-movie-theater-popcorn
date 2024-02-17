package com.example.drawingapp

import android.view.View
import android.widget.SeekBar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.google.android.material.slider.Slider
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class DrawingFragmentEspressoTest {
    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    /***
     * component id holders
     */
    private val redBar = R.id.redSeekBar
    private val blueBar = R.id.blueSeekBar
    private val greenBar = R.id.greenSeekBar

    /***
     * Checks that all the components exist on the Drawing Fragments
     */
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
        componentExistCheck(R.id.draw_view)
    }
    @Test
    fun checkNewDrawingNameExist() {
        createNewDrawing()
//        onView(withText(drawingName)).check(matches(isDisplayed()))
        componentExistCheck(R.id.imageName)
//        onView(withId(R.id.imageName)).check(matches())
    }
    @Test
    fun checkExistingDrawingNameExist() {
        //TODO: add steps for click on an existing drawing
        //onView(withId(R.id.imageName)).check(matches(isDisplayed()))
        TODO("not yet implemented")
    }
    @Test
    fun checkEraserButtonExist() {
        createNewDrawing()
//        onView(withText("eraser")).check(matches(isDisplayed()))
        componentExistCheck(R.id.eraserButton)
    }

    @Test
    fun checkBackButtonExist() {
        createNewDrawing()
//        onView(withText("Back to Main")).check(matches(isDisplayed()))
        componentExistCheck(R.id.backButton)
    }
    @Test
    fun checkSaveButtonExist() {
        createNewDrawing()
//        onView(withText("Save")).check(matches(isDisplayed()))
        componentExistCheck(R.id.saveButton)
    }

    @Test
    fun checkColorButtonExist() {
        createNewDrawing()
//        onView(withText("More Colors")).check(matches(isDisplayed()))
        componentExistCheck(R.id.colorSelectButton)
    }

    @Test
    fun checkPenShapeButtonExist() {
        createNewDrawing()
//        onView(withText("Pen")).check(matches(isDisplayed()))
        componentExistCheck(R.id.penButton)
    }
    @Test
    fun checkRGBSlidersExist() {
        createNewDrawing()
        goToColorSelection()
        componentExistCheck(R.id.redSeekBar)
        componentExistCheck(R.id.greenSeekBar)
        componentExistCheck(R.id.blueSeekBar)
    }
    @Test
    fun checkRGBLabelSlidersExist() {
        createNewDrawing()
        goToColorSelection()
        componentExistCheck(R.id.redSeekBarLabel)
        componentExistCheck(R.id.greenSeekBarLabel)
        componentExistCheck(R.id.blueSeekBarLabel)
    }
    @Test
    fun checkRGBNumberLabelSlidersExist() {
        createNewDrawing()
        goToColorSelection()
        componentExistCheck(R.id.redSeekBarValue)
        componentExistCheck(R.id.greenSeekBarValue)
        componentExistCheck(R.id.blueSeekBarValue)
    }
    @Test
    fun checkSizeSlidersExist() {
        createNewDrawing()
        componentExistCheck(R.id.penSizeSlider)
    }
    @Test
    fun checkSizeLabelSlidersExist() {
        createNewDrawing()
        //TODO: find size label text
        TODO("not yet implemented")
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
    TODO("not yet implemented")

}
    @Test
    fun backButtonNavigatesToHomeScreen() {
        createNewDrawing()
        componentExistCheck(R.id.backButton)
        onView(withId(R.id.backButton)).perform(click())
        componentExistCheck(R.id.newCanvasButton)
    }
    @Test
    fun penShapeUpdates() {
        createNewDrawing()
        clickButton(R.id.penButton)
//        TODO("not yet implemented: needs options to click on")

    }
    private fun checkPeekBar(id: Int, expectedValue: Int){
        onView((withId(id))).check(matches(withValue(expectedValue)))
    }
    private fun setPeekBar(id: Int, newValue: Int){
        onView((withId(id))).perform(setValue(newValue))
    }
//    @Test
//    fun penSizeUpdates() {
//        createNewDrawing()
//        checkPeekBar(R.id.penSizeSlider, 1)
//        setPeekBar(R.id.penSizeSlider, 5)
//        checkPeekBar(R.id.penSizeSlider, 5)
//        TODO check pen size
    }
    @Test
    fun penColorUpdates() {
        var greenValue = 0
        var blueValue = 0
        var redValue = 255

        createNewDrawing()
        clickButton(R.id.colorSelectButton)
        checkPeekBar(redBar,redValue)
        checkPeekBar(blueBar, blueValue)
        checkPeekBar(greenBar, greenValue)

        greenValue = 255
        blueValue = 0
        setPeekBar(greenBar, greenValue)
        setPeekBar(blueBar,blueValue)

        checkPeekBar(redBar,redValue)
        checkPeekBar(blueBar, blueValue)
        checkPeekBar(greenBar, greenValue)

        //TODO: check Pen color
    }
    @Test
    fun eraserUpdatesCanvas() {
        createNewDrawing()

        TODO("not yet implemented")
    }

    /**
     * Helper methods below
     */
    private fun goToDrawFrag(){
        //clicks on the new canvas drawing
        onView((withId(R.id.newCanvasButton))).perform(click())
    }

    private fun createNewDrawing(){
        goToDrawFrag()
        //checks if Save text is on the current Screen to verify we are on the Drawing Fragment
        componentExistCheck(R.id.imageName)
    }

    private fun componentExistCheck(id: Int){
        onView(withId(id)).check(matches(isDisplayed()))
    }

    private fun goToColorSelection(){
        componentExistCheck(R.id.colorSelectButton)
        onView(withId(R.id.colorSelectButton)).perform(click())
    }

    private fun clickButton(id: Int){
        onView(withId(id)).perform(click())
    }

    // withValue method was from stackoverflow which allows espresso to check specific value of the slider
    // this is the link: https://stackoverflow.com/questions/65390086/androidx-how-to-test-slider-in-ui-tests-espresso
    private fun withValue(expectedValue: Int): Matcher<View?> {
        return object : BoundedMatcher<View?, SeekBar>(SeekBar::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("expected: $expectedValue")
            }

            override fun matchesSafely(slider: SeekBar?): Boolean {
                return slider?.progress == expectedValue
            }
        }
    }

    // withValue method was from stackoverflow which allows espresso to update the value of the slider
    // this is the link: https://stackoverflow.com/questions/65390086/androidx-how-to-test-slider-in-ui-tests-espresso
    private fun setValue(value: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return "Set Slider value to $value"
            }

            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(SeekBar::class.java)
            }

            override fun perform(uiController: UiController?, view: View) {
                val seekBar = view as SeekBar
                seekBar.progress = value
            }
        }
    }
}