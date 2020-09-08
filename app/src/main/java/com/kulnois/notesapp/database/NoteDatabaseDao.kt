package com.kulnois.notesapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kulnois.notesapp.model.Note

/**
 * Created by @kulnois on 7/09/2020.
 */

@Dao
interface NoteDatabaseDao {

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Query("SELECT * FROM note_table ORDER BY noteId DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE noteId = :key") // Consultar un valor por ID
    fun get(key: Long): Note

    @Query("SELECT * FROM note_table WHERE noteId = :key")
    fun getNoteWithId(key: Long): LiveData<Note>

    @Query("SELECT * FROM note_table ORDER BY noteId DESC LIMIT 1")
    fun getToNote(): Note?

    @Delete
    fun delete(note: Note)

}