package com.yasserelgammal.contactappkotlin.ui.fragments.list

import com.yasserelgammal.contactappkotlin.data.model.Person

/*
interface to handle recycle clicks
*/
interface ContactRecycleInteraction {

    fun onItemSelected(position: Int, item: Person)
    fun onItemUpdate(position: Int, item: Person)
    fun onItemDeleted(position: Int, item: Person)
    fun onItemCall(position: Int, item: Person)

}