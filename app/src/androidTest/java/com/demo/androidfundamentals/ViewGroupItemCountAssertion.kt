package com.demo.androidfundamentals

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matcher


class ViewGroupItemCountAssertion private constructor(matcher: Matcher<Int>) :
    ViewAssertion {
    private val matcher: Matcher<Int>

    init {
        this.matcher = matcher
    }

    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        if(view is RecyclerView) {
            val adapter = view.adapter
            assertThat(adapter!!.itemCount, matcher)
        } else if(view is ViewGroup) {
            assertThat(view.childCount, matcher)
        } else {
            throw Exception("unsupported view type")
        }
    }

    companion object {
        @JvmStatic
        fun withItemCount(expectedCount: Int): ViewGroupItemCountAssertion {
            return withItemCount(`is`(expectedCount))
        }

        @JvmStatic
        fun withItemCount(matcher: Matcher<Int>): ViewGroupItemCountAssertion {
            return ViewGroupItemCountAssertion(matcher)
        }
    }
}