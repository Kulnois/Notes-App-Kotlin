package com.kulnois.notesapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by @kulnois on 7/09/2020.
 */

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var noteId: Long,

    @ColumnInfo(name = "title")
    var name: String,

    @ColumnInfo(name = "content")
    var content: String,

    @ColumnInfo(name = "updatedAt")
    var updatedAt: Long,
)