package com.example.tdm2_td02_exo2.ui.main.listContact

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tdm2_td02_exo2.data.entity.Contact
import com.example.tdm2_td02_exo2.data.repository.ContactRepository

class ListContactViewModel(@NonNull application: Application?) : ViewModel() {
    private val repository: ContactRepository = ContactRepository(application!!)
    private val contact: LiveData<List<Contact>>

    fun getContact(): LiveData<List<Contact>> {
        return contact
    }

    fun deleteContact(contact: Contact) {
        repository.deleteContact(contact)
    }

    init {
        contact = repository.getContactNotifier()
    }
}
