package com.demo.androidfundamentals

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher

fun getCountFromRecyclerView(@IdRes RecyclerViewId: Int): Int {
    var count = 0
    val matcher = object : TypeSafeMatcher<View?>() {
        override fun matchesSafely(item: View?): Boolean {
            count = (item as RecyclerView).adapter?.itemCount ?: 0
            return true
        }

        override fun describeTo(description: Description?) {}
    }
    Espresso.onView(Matchers.allOf(ViewMatchers.withId(RecyclerViewId), ViewMatchers.isDisplayed()))
        .check(ViewAssertions.matches(matcher))
    val result = count
    count = 0
    return result
}