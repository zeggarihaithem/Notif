package com.example.tdm2_td02_exo2.ui.main.configuration

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 *  This class is for passing  the Contact Repository to the Configuration Fragment
 *  when we create the instance of the ConfigurationViewModel
 */

@Suppress("UNCHECKED_CAST")
open class ConfigurationViewModelFactory(
    private val application: Application
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ConfigurationViewModel(
            application
        ) as T
    }

}