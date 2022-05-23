package com.example.noteapp.data

import androidx.room.*
import com.example.noteapp.model.Note
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface NoteDatabaseDao {

    @Query("SELECT * from note")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * from note where id =:id")
    suspend fun getNote(id: String): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: Note)

    @Query("DELETE from note")
    suspend fun deleteAll()

    @Query("DELETE from note where id =:id")
    suspend fun deleteSingleNote(id: UUID)
}