package com.example.tdm2_td02_exo2.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "contact_table")
class Contact(
    @PrimaryKey
    val id: String,
    val name: String,
    val number: String,
    val mail: String?

) {
}