package com.example.tdm2_td02_exo2.data.repository

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.tdm2_td02_exo2.data.contentProvider.ContactProvider
import com.example.tdm2_td02_exo2.data.dao.ContactDao
import com.example.tdm2_td02_exo2.data.database.ContactDatabase
import com.example.tdm2_td02_exo2.data.entity.Contact
import java.util.concurrent.CountDownLatch

class ContactRepository(application: Application) {
    private val contactProvider: ContactProvider = ContactProvider(application)
    private val contactDao: ContactDao?
    private var allContact: LiveData<List<Contact>>
    private var listContactNotifier: LiveData<List<Contact>>

    init {
        val database: ContactDatabase? = ContactDatabase.getInstance(application as Context)
        contactDao = database?.contactDao()
        allContact = contactProvider.getListContact()
        listContactNotifier = contactDao!!.getContacts()

    }

    fun getAllContact(): LiveData<List<Contact>> {
        return allContact
    }

    fun getContactNotifier(): LiveData<List<Contact>> {
        return listContactNotifier
    }


    fun addContact(contact: Contact) {
        contactDao?.let { InsertContactAsyncTask(it).execute(contact) }
    }

    fun deleteContact(contact: Contact) {
        contactDao?.let { DeleteContactAsyncTask(it).execute(contact) }
    }

    class InsertContactAsyncTask(private val contactDao: ContactDao) :
        AsyncTask<Contact?, Void?, Void?>() {
        override fun doInBackground(vararg contacts: Contact?): Void? {
            contacts[0]?.let { contactDao.addContact(it) }
            return null
        }

    }

    class DeleteContactAsyncTask(private val contactDao: ContactDao) :
        AsyncTask<Contact?, Void?, Void?>() {
        override fun doInBackground(vararg contacts: Contact?): Void? {
            contacts[0]?.let { contactDao.deleteContact(it) }
            return null
        }

    }

}