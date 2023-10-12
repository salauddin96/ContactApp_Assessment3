package org.techtales.contactapp_assessment3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Person::class], version = 1)
abstract class PersonDatabase:RoomDatabase() {
    abstract fun ContactDao():ContactDao

    companion object{
        @Volatile
        private var INSTANCE:PersonDatabase?=null

        fun getDatabase(context: Context):PersonDatabase{
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PersonDatabase::class.java,
                    "person_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}