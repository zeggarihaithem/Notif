package com.example.tdm2_td02_exo2.ui.main.listContact

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tdm2_td02_exo2.ui.main.configuration.ConfigurationViewModel

/**
*  This class is for passing  the Contact Repository to the ListContact Fragment
*  when we create the instance of the ListContactViewModel
*/

@Suppress("UNCHECKED_CAST")
open class ListContactViewModelFactory(
    private val application: Application
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListContactViewModel(
            application
        ) as T
    }

}