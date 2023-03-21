package com.demo.androidfundamentals.core

import com.demo.androidfundamentals.models.MovieModel

enum class Status {
    SUCCESS,
    ERROR,
    LOADING;

    /**
     * Returns `true` if the [Status] is success else `false`.
     */
    fun isSuccessful() = this == SUCCESS

    /**
     * Returns `true` if the [Status] is loading else `false`.
     */
    fun isLoading() = this == LOADING

    /**
     * Returns `true` if the [Status] is in error else `false`.
     */
    fun isError() = this == ERROR
}
