package com.example.testproject

import com.example.testproject.ui.Status


data class Resource<out T>(
    val status: Status,
    val data: T?,
    val msgRes: Int,
    val errorReason: Int
) {

    companion object {
        fun <T> success(data: T? = null): Resource<T> {
            return Resource(Status.SUCCESS, data, 0, 0)
        }

        fun <T> error(errorReason: Int = 0, msgRes: Int = 0, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, msgRes, errorReason)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, 0, 0)
        }
    }
}