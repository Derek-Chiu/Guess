package com.example.android.guess

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MaterialActivityTester {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<MaterialActivity>(MaterialActivity::class.java)

    @Test
    fun guessWrong() {
        val resources = activityTestRule.activity.resources
        val secret = activityTestRule.activity.secretNumber.secret
//        val n = 5
        for (n in 1..10) {
            onView(withId(R.id.ed_number)).perform(clearText())
            onView(withId(R.id.ed_number)).perform(typeText(n.toString()))
            onView(withId(R.id.confirm_button)).perform(click())
            val message =
                if (n < secret) resources.getString(R.string.bigger)
                else resources.getString(R.string.smaller)
            onView(withText(message)).check(matches(isDisplayed()))
            onView(withText(R.string.ok)).perform(click())
        }
    }

    @Test
    fun replayButton() {
        val resources = activityTestRule.activity.resources
        val secret = activityTestRule.activity.secretNumber.secret
        val n = 11
        onView(withId(R.id.ed_number)).perform(clearText())
        onView(withId(R.id.ed_number)).perform(typeText(n.toString()))
        onView(withId(R.id.confirm_button)).perform(click())
        val message =
            if (n < secret) resources.getString(R.string.bigger)
            else resources.getString(R.string.smaller)
        onView(withText(message)).check(matches(isDisplayed()))
        onView(withText(R.string.ok)).perform(click())

        // Crash without this line, can't touch fab if softKeyBoard is showing.
        onView(withId(R.id.fab_replay)).perform(closeSoftKeyboard())

        onView(withId(R.id.fab_replay)).perform(click())
        onView(withText(R.string.are_you_sure)).check(matches(isDisplayed()))
        onView(withText(R.string.ok)).perform(click())
        onView(withId(R.id.ed_number)).check(matches(withText("")))
        onView(withId(R.id.counter)).check(matches(withText("0")))
    }
}