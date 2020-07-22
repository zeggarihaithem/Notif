package com.example.tdm2_td02_exo2.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tdm2_td02_exo2.data.entity.Contact

@Dao
interface ContactDao {
    @Insert
    fun addContact(contact: Contact)


    @Delete
    fun deleteContact(contact: Contact)


    @Query("SELECT * FROM contact_table ORDER BY name desc")
    fun getContacts(): LiveData<List<Contact>>//so we can observe the changes
}