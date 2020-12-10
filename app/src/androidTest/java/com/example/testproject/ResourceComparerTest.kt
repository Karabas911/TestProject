package com.example.testproject

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.Test

import org.junit.Before

class ResourceComparerTest {

    private lateinit var resComparer: ResourceComparer

    @Before
    fun setUp() {
        resComparer = ResourceComparer()
    }

    @Test
    fun resAndValueAreEquals_isTrue() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resComparer.compareResAndValue(context, R.string.app_name, "TestProject")
        assertThat(result).isTrue()
    }

    @Test
    fun resAndValueAreNotEquals_isFalse() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resComparer.compareResAndValue(context, R.string.app_name, "TestProjectTest")
        assertThat(result).isFalse()
    }
}