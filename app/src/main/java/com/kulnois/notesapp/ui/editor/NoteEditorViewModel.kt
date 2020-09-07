package com.kulnois.notesapp.ui.editor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kulnois.notesapp.model.Note
import com.kulnois.notesapp.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

/**
 * Created by @kulnois on 5/09/2020.
 */

class NoteEditorViewModel (private val notesRepository: NoteRepository) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val dateString = getDateString()


    private val _navigateToList = MutableLiveData<Boolean>()
    val navigateToList: LiveData<Boolean>
        get() = _navigateToList

    fun saveNote(note: Note) {
        uiScope.launch {
            notesRepository.addNote(note)
            navigateToList()
        }
    }

    @JvmName("getDateString1")
    private fun getDateString(): String {
        return "Hoy " + SimpleDateFormat("hh:mm a").format(System.currentTimeMillis()).toString()
    }

    private fun navigateToList() {
        _navigateToList.value = true
    }

    fun navigateToListDone() {
        _navigateToList.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }



}