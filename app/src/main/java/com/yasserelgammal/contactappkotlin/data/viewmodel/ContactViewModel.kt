package com.yasserelgammal.contactappkotlin.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.yasserelgammal.contactappkotlin.data.ContactDatabase
import com.yasserelgammal.contactappkotlin.data.model.Person
import com.yasserelgammal.contactappkotlin.data.repository.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val contactDao = ContactDatabase.getDatabase(application).contactDao()
    private val repository : ContactRepository = ContactRepository(contactDao)

    val getAllData: LiveData<List<Person>> = repository.getAllData
    val getAllFav: LiveData<List<Person>> = repository.getAllFav

    fun insertData(contact : Person){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(contact)
        }
    }

    fun updateData(contact: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(contact)
        }
    }

    fun deleteItem(contact: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(contact)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun favContact(fav: Int, id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.favContact(fav, id)
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Person>>{
        return repository.getSearchContacts(searchQuery)
    }

}