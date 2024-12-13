package com.mutdev.soulparkingtest.view

import android.widget.DatePicker
import android.widget.TimePicker
import androidx.cardview.widget.CardView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mutdev.soulparkingtest.R
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AddUpdateTodoTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun shouldAddThenUpdateThenDeleteTodoSuccessfully() {

        onView(withId(R.id.btn_add_new)).perform(click())

        onView(withId(R.id.et_title)).perform(typeText("Test Todo"))
        onView(withId(R.id.et_description)).perform(typeText("Description"))
        onView(withId(R.id.et_date)).perform(click())

        onView(withClassName(Matchers.equalTo(DatePicker::class.java.name))).perform(
            PickerActions.setDate(
                2024,
                12,
                14,
            )
        )
        onView(withId(android.R.id.button1)).perform(click())


        onView(withClassName(Matchers.equalTo(TimePicker::class.java.name))).perform(
            PickerActions.setTime(
                6,
                0,
            )
        )
        onView(withId(android.R.id.button1)).perform(click())

        onView(withId(R.id.et_date)).check(matches(withText("Saturday, December 14, 2024 6:00 AM")))

        onView(withId(R.id.btn_save)).perform(click())

        onView(withId(R.id.tv_title)).check(matches(withText("Test Todo")))

        onView(withClassName(Matchers.equalTo(CardView::class.java.name))).perform(click())

        onView(withId(R.id.et_title)).perform(replaceText("Test Update Todo"))
        onView(withId(R.id.et_description)).perform(replaceText("Updated Description"))

        onView(withId(R.id.btn_save)).perform(click())

        onView(withText("Test Update Todo")).check(matches(isDisplayed()))
        onView(withText("Updated Description")).check(matches(isDisplayed()))


        onView(withClassName(Matchers.equalTo(CardView::class.java.name))).perform(swipeLeft())
        onView(withId(android.R.id.button1)).perform(click())

        onView(withId(R.id.tv_title)).check(doesNotExist())
    }

}