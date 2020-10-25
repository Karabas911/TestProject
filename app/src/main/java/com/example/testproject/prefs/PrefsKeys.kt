package com.example.testproject.prefs

import androidx.annotation.StringDef
import com.example.testproject.prefs.PrefsKeys.Companion.LOGGED_USER_ID
import com.example.testproject.prefs.PrefsKeys.Companion.USER_REGISTRATION_ID

@Retention(AnnotationRetention.SOURCE)
@StringDef(USER_REGISTRATION_ID, LOGGED_USER_ID)
annotation class PrefsKeys {

    companion object {
        const val USER_REGISTRATION_ID = "registration_id"

        const val LOGGED_USER_ID = "logged_in_user"
    }
}