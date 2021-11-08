package com.yasserelgammal.contactappkotlin.data.repository

import androidx.lifecycle.LiveData
import com.yasserelgammal.contactappkotlin.data.ContactDao
import com.yasserelgammal.contactappkotlin.data.model.Person

class ContactRepository(private val contactDao: ContactDao) {
    //get all contacts from database
    val getAllData: LiveData<List<Person>> = contactDao.getAllData()
    //get all favorite contacts from database
    val getAllFav: LiveData<List<Person>> = contactDao.getFavoriteContacts()

    // insert new contact
    suspend fun insertData(contact: Person){
        contactDao.insertContact(contact)
    }

    //update contact
    suspend fun updateData(contact: Person){
        contactDao.updateData(contact)
    }

    //remove contact
    suspend fun deleteItem(contact: Person){
        contactDao.delete(contact)
    }

    // delete all contacts
    suspend fun deleteAll(){
        contactDao.deleteAllContacts()
    }

    // add contact to favorite
    suspend fun favContact(fav: Int, id: Int){
        contactDao.favContact(fav, id)
    }

    //function to get searched Contacts in database
    fun getSearchContacts(query: String): LiveData<List<Person>> {
        return contactDao.searchContact(query)
    }


}