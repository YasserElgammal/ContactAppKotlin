package com.yasserelgammal.contactappkotlin.ui.fragments.common

import androidx.recyclerview.widget.DiffUtil
import com.yasserelgammal.contactappkotlin.data.model.Person

class ContactDiffUtil(private val oldContactList: List<Person>, private val newContactList: List<Person>):
    DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldContactList.size
    }

    override fun getNewListSize(): Int {
        return newContactList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldContactList[oldItemPosition].id == newContactList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldContactList[oldItemPosition].id != newContactList[newItemPosition].id -> false
            oldContactList[oldItemPosition].name != newContactList[newItemPosition].name -> false
            oldContactList[oldItemPosition].phoneNumber != newContactList[newItemPosition].phoneNumber -> false
            oldContactList[oldItemPosition].title != newContactList[newItemPosition].title -> false
            oldContactList[oldItemPosition].image != newContactList[newItemPosition].image -> false
            oldContactList[oldItemPosition].label != newContactList[newItemPosition].label -> false
            oldContactList[oldItemPosition].fav != newContactList[newItemPosition].fav -> false
            oldContactList[oldItemPosition].notes != newContactList[newItemPosition].notes -> false
            else -> true
        }
    }

}