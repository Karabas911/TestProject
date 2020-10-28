package com.example.testproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.testproject.R
import com.example.testproject.prefs.PreferenceWrapper
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private val prefs: PreferenceWrapper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initStartFragment()
    }

    private fun initStartFragment() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.main_navigation)
        val navController = navHostFragment.navController

        // Check if user logged in
        val userId = prefs.getLoginUserId()
        val destination = if (userId > 0) R.id.listFragment else R.id.authFragment
        navGraph.startDestination = destination
        navController.graph = navGraph
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