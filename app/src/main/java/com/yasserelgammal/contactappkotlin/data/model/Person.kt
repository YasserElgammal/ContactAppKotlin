package com.yasserelgammal.contactappkotlin.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
/*
Main model
 */
@Entity(tableName = "contact_table")
@Parcelize
data class Person(

    @PrimaryKey (autoGenerate = true)
    val id: Int,

    val name: String,
    val title: String,
    val phoneNumber: String,
    val label: Group,
    val notes: String,
    val image: Int,
    val fav: Int
):Parcelable
