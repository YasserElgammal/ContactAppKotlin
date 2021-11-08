package com.yasserelgammal.contactappkotlin.ui.fragments.common

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import com.yasserelgammal.contactappkotlin.data.model.Group

/*
common & shared functions
*/
class SharedViewModel(application: Application): AndroidViewModel(application) {

    val items = listOf("Family", "Friends", "Work")

    fun parseGroup(gType: String): Group {
        return when(gType){
            "Family" -> { Group.FAMILY }
            "Friends" -> { Group.FRIENDS }
            "Work" -> { Group.WORK }
            else -> Group.FRIENDS
        }
    }

    fun parseGroupToString(gType: Group): String {
        return when(gType){
            Group.FAMILY -> {"Family" }
            Group.FRIENDS -> { "Friends" }
            Group.WORK -> { "Work" }
        }
    }

    fun validations(name: String, phone: String, title: String, notes: String): Boolean {
        return !TextUtils.isEmpty(name) || !TextUtils.isEmpty(phone) || !TextUtils.isEmpty(title) || !TextUtils.isEmpty(notes)
    }
}