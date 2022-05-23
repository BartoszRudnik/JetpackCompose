package com.example.noteapp.repository

import com.example.noteapp.data.NoteDatabaseDao
import com.example.noteapp.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDatabaseDao: NoteDatabaseDao) {
    suspend fun addNote(note: Note) = noteDatabaseDao.addNote(note)

    suspend fun updateNote(note: Note) = noteDatabaseDao.updateNote(note)

    suspend fun deleteNote(note: Note) = noteDatabaseDao.deleteSingleNote(note.id)

    suspend fun deleteAll() = noteDatabaseDao.deleteAll()

    fun getAllNotes(): Flow<List<Note>> =
        noteDatabaseDao.getNotes().flowOn(Dispatchers.IO).conflate()
}