package com.kulnois.notesapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by @kulnois on 5/09/2020.
 */

class NoteListViewModel : ViewModel() {

    private val _navigateToEditor = MutableLiveData<Boolean>()
    val navigateToEditor: LiveData<Boolean>
        get() = _navigateToEditor

    fun navigateToEditor() {
        _navigateToEditor.value = true
    }

    fun navigateToEditorDone() {
        _navigateToEditor.value = false
    }


}