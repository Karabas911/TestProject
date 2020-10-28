package com.example.testproject.prefs

import android.content.SharedPreferences
import com.example.testproject.prefs.PreferenceHelper.get
import com.example.testproject.prefs.PreferenceHelper.remove
import com.example.testproject.prefs.PreferenceHelper.set

class PreferenceWrapperImpl(private val prefs: SharedPreferences) : PreferenceWrapper {

    override fun getNextUserId(): Int {
        val registrationId = prefs.get(PrefsKeys.USER_REGISTRATION_ID, 0)?.inc()
        prefs.set(PrefsKeys.USER_REGISTRATION_ID, registrationId)
        return registrationId!!
    }

    override fun getLoginUserId(): Int {
        return prefs.get(PrefsKeys.LOGGED_USER_ID, 0)!!
    }

    override fun setLoginUserId(userId: Int) {
        prefs.set(PrefsKeys.LOGGED_USER_ID, userId)
    }

    override fun removeLoggedInId() {
        prefs.remove(PrefsKeys.LOGGED_USER_ID)
    }
}