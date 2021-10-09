package com.yasserelgammal.contactappkotlin.data.repository

import androidx.lifecycle.LiveData
import com.yasserelgammal.contactappkotlin.data.ContactDao
import com.yasserelgammal.contactappkotlin.data.model.Person

class ContactRepository(private val contactDao: ContactDao) {

    val getAllData: LiveData<List<Person>> = contactDao.getAllData()
    val getAllFav: LiveData<List<Person>> = contactDao.getFavoriteContacts()

    suspend fun insertData(contact: Person){
        contactDao.insertContact(contact)
    }

    suspend fun updateData(contact: Person){
        contactDao.updateData(contact)
    }

    suspend fun deleteItem(contact: Person){
        contactDao.delete(contact)
    }

    suspend fun deleteAll(){
        contactDao.deleteAllContacts()
    }

    suspend fun favContact(fav: Int, id: Int){
        contactDao.favContact(fav, id)
    }

}