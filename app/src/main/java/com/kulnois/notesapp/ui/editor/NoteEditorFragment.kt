package com.kulnois.notesapp.ui.editor

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kulnois.notesapp.R
import com.kulnois.notesapp.database.NoteDatabase
import com.kulnois.notesapp.database.NoteDatabaseDao
import com.kulnois.notesapp.databinding.FragmentNoteEditorBinding
import com.kulnois.notesapp.model.Note
import com.kulnois.notesapp.repository.NoteRepository
import com.kulnois.notesapp.ui.list.NoteListViewModel
import kotlinx.android.synthetic.main.fragment_note_editor.*

/**
 * Created by @kulnois on 5/09/2020.
 */

class NoteEditorFragment : Fragment() {

    private lateinit var binding: FragmentNoteEditorBinding
    private lateinit var repository: NoteRepository
    private lateinit var noteDatabaseDao: NoteDatabaseDao
    private lateinit var application: Application
    private lateinit var viewModel: NoteEditorViewModel
    private val note : Note = Note()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteEditorBinding.inflate(inflater)
        initUI()
        return binding.root
    }

    private fun initUI() {
        @Suppress("UNUSED_VARIABLE")
        application = requireNotNull(activity).application

        noteDatabaseDao = NoteDatabase.getInstance(application).noteDatabaseDao
        repository = NoteRepository(noteDatabaseDao)
        val viewModelFactory = NoteViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get((NoteEditorViewModel::class.java))


        binding.note
        initObservers()

        binding.navigationUp.setOnClickListener {
            this.findNavController().navigateUp()
        }

        binding.saveNoteBtn.setOnClickListener {
            note?.updatedAt = System.currentTimeMillis()
            if (titleEditText.text?.trim()?.isEmpty()!! || titleEditText.text?.trim()?.isEmpty()!!) {
                Snackbar.make(noteEditorLayout, getString(R.string.data_required), Snackbar.LENGTH_SHORT).show()
            } else {
                note.title = titleEditText.text.toString()
                note.content = contentEditText.text.toString()
                viewModel.saveNote(note)
            }
        }
    }

    private fun initObservers() {
        viewModel.navigateToList.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController().navigateUp()
                viewModel.navigateToListDone()
            }
        })
    }


}