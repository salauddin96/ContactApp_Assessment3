package org.techtales.contactapp_assessment3

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class Person(
    @PrimaryKey(autoGenerate = true)
    val id:Int?,
    @ColumnInfo(name = "first_name")
    val firstName:String,
    @ColumnInfo(name = "last_name")
    val lastName:String,
    @ColumnInfo(name = "roll_no")
    val rollNo:Int?
)
