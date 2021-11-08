package com.yasserelgammal.contactappkotlin.data.model

import androidx.room.TypeConverter

/* to convert group type / from it's type / to / string
 to handle it with room
 */
class Converter {

    @TypeConverter
    fun fromGroup(group: Group): String{
        return group.name
    }

    @TypeConverter
    fun toGroup(group: String): Group{
        return Group.valueOf(group)
    }
}