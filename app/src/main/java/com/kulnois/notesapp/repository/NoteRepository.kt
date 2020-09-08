package com.kulnois.notesapp.repository

import androidx.lifecycle.LiveData
import com.kulnois.notesapp.database.NoteDatabaseDao
import com.kulnois.notesapp.model.Note
import kotlinx.coroutines.*

/**
 * Created by @kulnois on 7/09/2020.
 */

class NoteRepository(
    private val noteDatabaseDao: NoteDatabaseDao,

) : Repository {


    override suspend fun addNote(note: Note) {
        insert(note)
    }

    override suspend fun updateNote(note: Note) {
        update(note)
    }

    override fun getNotes(): LiveData<List<Note>> {
        return noteDatabaseDao.getAllNotes()
    }

    override fun getNote(key: Long): LiveData<Note> {
        return noteDatabaseDao.getNoteWithId(key)
    }

    override suspend fun deleteNote(note: Note) {
        delete(note)
    }

    private suspend fun insert(note: Note) {
        withContext(Dispatchers.IO) {
            noteDatabaseDao.insert(note)
        }
    }

    private suspend fun update(note: Note) {
        withContext(Dispatchers.IO) {
            noteDatabaseDao.update(note)
        }
    }

    private suspend fun delete(note: Note) {
        withContext(Dispatchers.IO) {
            noteDatabaseDao.delete(note)
        }
    }

}