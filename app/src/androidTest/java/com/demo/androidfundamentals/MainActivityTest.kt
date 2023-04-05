package com.demo.androidfundamentals

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.demo.androidfundamentals.ViewGroupItemCountAssertion.Companion.withItemCount
import org.hamcrest.Description
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testFilterFlow() {
        // check data/list is visible
        onView(withId(R.id.recycler_view)).check(withItemCount(greaterThan(1)))
        val initialItemsCount = getCountFromRecyclerView(R.id.recycler_view)
        // check filter button is visible
        // do click on filter button
        onView(withId(R.id.filter_btn)).perform(click())
        // check filter layout is visible
        onView(withId(R.id.chip_group)).check(matches(isDisplayed()))

        // check years chips are visible
        onView(withId(R.id.chip_group)).check(withItemCount(greaterThan(1)))
        // select a random year (or 1st)
        // todo - find a way to select any chip randomly like position
        onView(withId(88)).check(matches(withText("1972"))).perform(click())
        // do click apply button
        onView(withId(R.id.apply_button)).perform(click())
        // check if the movie list is updated
        val filteredItemsCount = getCountFromRecyclerView(R.id.recycler_view)
        assertNotEquals(initialItemsCount, filteredItemsCount)

    }

    @Test
    fun testSortButton() {
        onView(withId(R.id.sort_btn)).perform(click())
        onView(withId(R.id.by_name)).perform(click())
    }
}