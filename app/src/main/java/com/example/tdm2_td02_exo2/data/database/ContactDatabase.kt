package com.example.tdm2_td02_exo2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tdm2_td02_exo2.data.dao.ContactDao
import com.example.tdm2_td02_exo2.data.entity.Contact

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao?

    companion object {
        private var instance: ContactDatabase? = null
        @Synchronized
        fun getInstance(context: Context): ContactDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java, "contact_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }

}