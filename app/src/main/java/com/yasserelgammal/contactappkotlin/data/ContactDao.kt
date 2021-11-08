package com.yasserelgammal.contactappkotlin.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yasserelgammal.contactappkotlin.data.model.Person

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact_table ORDER BY name ASC")
    fun getAllData() : LiveData<List<Person>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertContact(contact: Person)

    @Update
    suspend fun updateData(contact: Person)

    @Delete
    suspend fun delete(contact: Person)

    @Query("delete from contact_table")
    suspend fun deleteAllContacts()

    // this query will get favorite contacts depends on where condition
    @Query("SELECT * FROM contact_table WHERE fav= 1 ORDER BY name ASC")
    fun getFavoriteContacts(): LiveData<List<Person>>

    // query to make item as favorite
    @Query("UPDATE contact_table SET fav = :favorite WHERE id = :id")
    suspend fun favContact(favorite: Int, id: Int)

    // search query
    @Query("SELECT * FROM contact_table WHERE name LIKE :query OR title LIKE:query ORDER BY name ASC")
    fun searchContact(query: String?): LiveData<List<Person>>
}