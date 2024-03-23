package com.example.drawingapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.core.content.ContextCompat
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.core.graphics.toColor
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
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
import junit.framework.TestCase.assertEquals
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.internal.Checks
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class DrawingFragmentEspressoTest {
    @get: Rule val activityRule = ActivityScenarioRule(MainActivity::class.java)
    @get:Rule val composeTestRule = createComposeRule()

    /***
     * component id holders
     */
    private val redBar = R.id.redSeekBar
    private val blueBar = R.id.blueSeekBar
    private val greenBar = R.id.greenSeekBar

    /***
     * Checks that all the components exist on the Drawing Fragments
     */
    //Test: Navigation test when click on a saved drawing and directs to the canvas page
    //Test: Navigation to the Drawing fragment
    //Test: check the canvas exists
    //Test: check if the canvas name is on the drawing Fragment
    //Test: check if the eraser button is on the screen
    //Test: check back button exists
    //Test: check the save button exists
    //Test: check the more colors button exists
    //Test: check the pen shape button exists
    //Test: check the sliders (RGB) for the pen color exist
    //Test: check label exists for each color (RGB) slider exist
    //Test: check number value label exists for each color (RGB) slider exist
    //Test: check the previous color button exists on the color dialog
    //Test: check the new color button exists on the color dialog
    //Test: check the size slider for the pen exists

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
        componentExistCheck(R.id.imageName)
    }
    @Test
    fun checkExistingDrawingNameExist() {
        onView(withText("Pick Existing Drawing")).check(matches(isDisplayed()))
        componentExistCheck(R.id.existingDrawing)
    }
    @Test
    fun checkEraserButtonExist() {
        createNewDrawing()
        componentExistCheck(R.id.eraserButton)
    }

    @Test
    fun checkBackButtonExist() {
        createNewDrawing()
        componentExistCheck(R.id.backButton)
    }
    @Test
    fun checkSaveButtonExist() {
        createNewDrawing()
        componentExistCheck(R.id.saveButton)
    }
    @Test
    fun checkColorButtonExist() {
        createNewDrawing()
        componentExistCheck(R.id.colorSelectButton)
    }
    @Test
    fun checkPenShapeButtonExist() {
        createNewDrawing()
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
    fun checkPrevColorButtonExist() {
        createNewDrawing()
        clickButton(R.id.colorSelectButton)
        componentExistCheck(R.id.prevPenColor)
    }
    fun checkNewColorButtonExist() {
        createNewDrawing()
        clickButton(R.id.colorSelectButton)
        componentExistCheck(R.id.newPenColor)    }
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
    fun newDrawingSavedSuccessfully() {
        createNewDrawing()
        saveDrawing("test_save_first_drawing")


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
//      TODO("not yet implemented: needs options to click on")

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
//    }
    @Test
    fun penColorUpdatesWithSliders() {
        var greenValue = 0
        var blueValue = 0
        var redValue = 255

        createNewDrawing()
        clickButton(R.id.colorSelectButton)
        checkPeekBar(redBar,redValue)
        checkPeekBar(blueBar, blueValue)
        checkPeekBar(greenBar, greenValue)

        componentExistCheck(R.id.prevPenColor)
        componentExistCheck(R.id.newPenColor)


    //checks the penColor was set to Red for both the prevPenColor button and newPenColor button
        onView(withId(R.id.prevPenColor)).check(matches(withValueColor(Color.rgb(redValue,greenValue,blueValue))))
        onView(withId(R.id.newPenColor)).check(matches(withValueColor(Color.rgb(redValue,greenValue,blueValue))))

        greenValue = 255
        blueValue = 0
        setPeekBar(greenBar, greenValue)
        setPeekBar(blueBar,blueValue)

        //Checking the sliders values were updated with the values given
        checkPeekBar(redBar,redValue)
        checkPeekBar(blueBar, blueValue)
        checkPeekBar(greenBar, greenValue)

        //checks the penColor was updated for the newPenColor button
        onView(withId(R.id.newPenColor)).check(matches(withValueColor(Color.rgb(redValue,greenValue,blueValue))))
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

    private fun saveDrawing(fileName: String) {
        componentExistCheck(R.id.imageName)
        componentExistCheck(R.id.saveButton)
        onView(withId(R.id.imageName)).perform(typeText(fileName))
        onView(withId(R.id.imageName)).check(matches(withText(fileName)))
        onView(withId(R.id.saveButton)).perform(click())
    }

    private fun openExistingDrawing(){
        onView(withText("Pick Existing Drawing")).check(matches(isDisplayed()))
        componentExistCheck(R.id.existingDrawing)
        clickButton(R.id.existingDrawing)
        composeTestRule.onNodeWithText("Go Back").assertExists()
//        composeTestRule.onNodeWithText("Go Back").performClick()

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

    // withValue Color was inspired from stackoverflow which allows espresso to check the button background color
    // this is the link: https://stackoverflow.com/questions/74442653/create-a-viewmatcher-for-the-background-color-of-a-button
    private fun withValueColor(expectedValue: Int): Matcher<View?> {
        return object : BoundedMatcher<View?, Button>(Button::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("expected: $expectedValue")
            }

            override fun matchesSafely(button: Button?): Boolean {
                val backColor = (button?.background as ColorDrawable).color
                return backColor == expectedValue
            }
        }
    }
}
