package com.kulnois.notesapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kulnois.notesapp.model.Note
import com.kulnois.notesapp.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * Created by @kulnois on 5/09/2020.
 */

class NoteListViewModel (private val noteRepository: NoteRepository) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _notesList = noteRepository.getNotes()

    val properties: LiveData<List<Note>>
        get() = _notesList

    private val _navigateToEditor = MutableLiveData<Boolean>()
    val navigateToEditor: LiveData<Boolean>
        get() = _navigateToEditor

    private val _navigateToNoteDetail = MutableLiveData<Long>()
    val navigateToNoteDetail
        get() = _navigateToNoteDetail


    val isEmpty: LiveData<Boolean> = Transformations.map(properties) {
        it.isEmpty()
    }

    fun navigateToEditor() {
        _navigateToEditor.value = true
    }

    fun navigateToEditorDone() {
        _navigateToEditor.value = false
    }

    fun onNoteClicked(id: Long) {
        _navigateToNoteDetail.value = id
    }

    fun onNoteNavigated() {
        _navigateToNoteDetail.value = null
    }


}