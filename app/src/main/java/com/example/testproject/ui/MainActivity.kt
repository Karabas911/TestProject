package com.example.testproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.testproject.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as? NavHostFragment
        navHostFragment?.let {
            val current = it.childFragmentManager.fragments[0]
            when (current) {
                is ListFragment -> finish()

                else -> super.onBackPressed()
            }
        }
    }
}