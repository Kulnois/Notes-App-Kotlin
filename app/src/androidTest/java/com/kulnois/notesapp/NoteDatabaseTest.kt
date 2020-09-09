package com.kulnois.notesapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kulnois.notesapp.database.NoteDatabase
import com.kulnois.notesapp.database.NoteDatabaseDao
import com.kulnois.notesapp.model.Note
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception

/**
 * Created by @kulnois on 7/09/2020.
 */

@RunWith(AndroidJUnit4::class)
class NoteDatabaseTest {
    private lateinit var noteDao: NoteDatabaseDao
    private lateinit var db: NoteDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        noteDao = db.noteDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insetAndGetNote() {
        val note = Note()
        noteDao.insert(note)
        val toNote = noteDao.getToNote()
        assertEquals(toNote?.updatedAt, -1L)
    }

}