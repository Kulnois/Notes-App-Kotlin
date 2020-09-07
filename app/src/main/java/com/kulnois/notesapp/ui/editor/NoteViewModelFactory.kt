package com.kulnois.notesapp.ui.editor

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kulnois.notesapp.repository.NoteRepository

/**
 * Created by @kulnois on 7/09/2020.
 */

class NoteViewModelFactory (
    private val repository: NoteRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteEditorViewModel::class.java)) {
            return NoteEditorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}