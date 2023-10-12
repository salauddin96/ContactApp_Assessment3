package org.techtales.contactapp_assessment3

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact_table")
    fun getAll(): List<Person>

    @Query("SELECT * FROM contact_table WHERE roll_no LIKE :roll LIMIT 1")
    suspend fun findByRoll(roll: Int): Person

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(person: Person)

    @Delete
    suspend fun delete(person: Person)

    @Query("DELETE FROM contact_table")
    suspend fun deleteAll()

    @Query("SELECT (SELECT COUNT(*) FROM contact_table) == 0")
    fun isEmpty(): Boolean
}