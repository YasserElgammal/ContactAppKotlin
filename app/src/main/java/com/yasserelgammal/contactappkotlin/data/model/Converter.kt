package com.yasserelgammal.contactappkotlin.data.model

import androidx.room.TypeConverter

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