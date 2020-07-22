package com.example.tdm2_td02_exo2.ui.main.configuration

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tdm2_td02_exo2.data.entity.Contact
import com.example.tdm2_td02_exo2.data.repository.ContactRepository

class ConfigurationViewModel(@NonNull application: Application?) : ViewModel() {
    private val repository: ContactRepository = ContactRepository(application!!)
    private val allContact: LiveData<List<Contact>>

    fun getAllContact(): LiveData<List<Contact>> {
        return allContact
    }

    fun addContact(contact : Contact){
        repository.addContact(contact)
    }

    init {
        allContact = repository.getAllContact()

    }
}
