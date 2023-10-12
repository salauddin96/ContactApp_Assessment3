package org.techtales.contactapp_assessment3

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class Person(
    @PrimaryKey(autoGenerate = true)
    val id:Int?,
    @ColumnInfo(name = "name")
    val name:String?,
    @ColumnInfo(name = "phone_no")
    val phone:Int?,
    @ColumnInfo(name = "email")
    val email:String?
)
