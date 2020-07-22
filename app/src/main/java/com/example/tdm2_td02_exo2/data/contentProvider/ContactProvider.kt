package com.example.tdm2_td02_exo2.data.contentProvider

import android.app.Application
import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tdm2_td02_exo2.data.entity.Contact


@Suppress("UNCHECKED_CAST")
class ContactProvider(private val application: Application) {


    fun getListContact(): LiveData<List<Contact>> {
        return providePhoneContact()!!
    }

    private fun providePhoneContact(): LiveData<List<Contact>>? {
        val liveListContact = MutableLiveData<List<Contact>>()
        val listContact = ArrayList<Contact>()
        var cursor: Cursor? = null
        val contentResolver: ContentResolver = application.contentResolver
        try {
            cursor =
                contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (cursor!!.count > 0) {
            while (cursor.moveToNext()) {
                var number: String = ""
                var email: String = ""
                val id: String =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name: String =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                val hasNumber =
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))
                if (hasNumber > 0) {
                    val phoneCursor: Cursor? =
                        contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                            arrayOf(id),
                            null
                        )
                    while (phoneCursor!!.moveToNext()) {

                        number =
                            phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    }
                    phoneCursor.close()
                }
                //get Email
                val emailCursor = contentResolver.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?",
                    arrayOf(id),
                    null
                )
                val emailIdx = emailCursor!!.getColumnIndex(Email.DATA)
                // let's just get the first email
                if (emailCursor.moveToFirst()) {
                    email = emailCursor.getString(emailIdx);
                }

                listContact.add(
                    Contact(
                        id,
                        name,
                        number,
                        email
                    )
                )
            }
        }
        cursor.close()
        liveListContact.value = listContact
        return liveListContact
    }


}