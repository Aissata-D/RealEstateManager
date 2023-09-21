package com.sitadigi.realestatemanager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sitadigi.realestatemanager.R
lateinit var listPropertyFragment: ListPropertyFragment

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
         listPropertyFragment = supportFragmentManager.findFragmentById(R.id.frame_layout_test)
                 as ListPropertyFragment

    }
}