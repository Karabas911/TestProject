package com.example.testproject.prefs

interface PreferenceWrapper {

    fun getNextUserId(): Int

    fun getLoginUserId(): Int

    fun setLoginUserId(userId: Int)

    fun removeLoggedInId()
}