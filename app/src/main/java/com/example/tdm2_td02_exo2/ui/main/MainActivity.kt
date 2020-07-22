package com.example.tdm2_td02_exo2.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tdm2_td02_exo2.R
import com.example.tdm2_td02_exo2.mail.AppExecutors

class MainActivity : AppCompatActivity() {
    companion object {
        val appExecutors = AppExecutors()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
