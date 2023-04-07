package com.demo.androidfundamentals

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.google.android.material.chip.ChipGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher

fun getCountFromView(@IdRes ViewId: Int): Int {
    var count = 0
    val matcher = object : TypeSafeMatcher<View?>() {
        override fun matchesSafely(item: View?): Boolean {
            when(item){
             is RecyclerView -> count = item.adapter?.itemCount ?: 0
             is ChipGroup -> count = item.childCount
            }
            return true
        }

        override fun describeTo(description: Description?) {}
    }
    Espresso.onView(Matchers.allOf(ViewMatchers.withId(ViewId), ViewMatchers.isDisplayed()))
        .check(ViewAssertions.matches(matcher))
    val result = count
    count = 0
    return result
}

fun nthChildOf(parentMatcher: Matcher<View?>, childPosition: Int): Matcher<View?>? {
    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("position $childPosition of parent ")
            parentMatcher.describeTo(description)
        }

        override fun matchesSafely(view: View): Boolean {
            if (view.parent !is ViewGroup) return false
            val parent = view.parent as ViewGroup
            return parentMatcher.matches(parent) && parent.childCount > childPosition && parent.getChildAt(
                childPosition
            ).equals(view)
        }
    }
}