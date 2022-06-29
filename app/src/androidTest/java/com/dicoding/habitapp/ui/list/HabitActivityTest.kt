package com.dicoding.habitapp.ui.list

import android.app.Activity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.dicoding.habitapp.R
import com.dicoding.habitapp.ui.add.AddHabitActivity
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test

//TODO 16 : Write UI test to validate when user tap Add Habit (+), the AddHabitActivity displayed
class HabitActivityTest {
    @get:Rule
    var activity = ActivityScenarioRule(HabitListActivity::class.java)
    var addHabit: Activity? = null

    @Test
    fun habitActivity(){
        Espresso.onView(ViewMatchers.withId(R.id.fab))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.add_ed_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.add_ed_minutes_focus))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.sp_priority_level))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.add_tv_start_time))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            run {
                addHabit = ActivityLifecycleMonitorRegistry.getInstance()
                    .getActivitiesInStage(Stage.RESUMED).elementAtOrNull(0)
                TestCase.assertTrue(addHabit?.javaClass == AddHabitActivity::class.java)
            }
        }
    }
}