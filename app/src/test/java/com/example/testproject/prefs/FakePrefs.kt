package com.example.testproject.prefs

import com.example.testproject.prefs.PreferenceWrapper

class FakePrefs: PreferenceWrapper {

    private var nextUserId = 0

    private var savedUserId = 1

    override fun getNextUserId(): Int {
        nextUserId++
        return nextUserId
    }

    override fun getLoginUserId(): Int = savedUserId

    override fun setLoginUserId(userId: Int) {
        savedUserId = userId
    }

    override fun removeLoggedInId() {
        savedUserId = -100
    }
}