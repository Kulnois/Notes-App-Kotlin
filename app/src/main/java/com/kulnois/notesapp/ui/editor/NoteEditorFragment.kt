package com.kulnois.notesapp.ui.editor

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.kulnois.notesapp.R
import com.kulnois.notesapp.database.NoteDatabase
import com.kulnois.notesapp.database.NoteDatabaseDao
import com.kulnois.notesapp.databinding.FragmentNoteEditorBinding
import com.kulnois.notesapp.model.Note
import com.kulnois.notesapp.repository.NoteRepository
import com.kulnois.notesapp.util.hideKeyboard

/**
 * Created by @kulnois on 5/09/2020.
 */

class NoteEditorFragment : Fragment() {

    private lateinit var binding: FragmentNoteEditorBinding
    private lateinit var repository: NoteRepository
    private lateinit var noteDatabaseDao: NoteDatabaseDao
    private lateinit var application: Application
    private lateinit var viewModel: NoteEditorViewModel
    private lateinit var arguments: NoteEditorFragmentArgs
    private var note : Note = Note()
    private var dialogView: View? = null
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
        application = requireNotNull(this.activity).application
        arguments = NoteEditorFragmentArgs.fromBundle(requireArguments())

        noteDatabaseDao = NoteDatabase.getInstance(application).noteDatabaseDao
        repository = NoteRepository(noteDatabaseDao)
        val viewModelFactory = NoteViewModelFactory(repository, arguments.noteKey)
        viewModel = ViewModelProvider(this, viewModelFactory).get((NoteEditorViewModel::class.java))

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        initObservers()

        binding.navigationUp.setOnClickListener {
            this.findNavController().navigateUp()
        }

        binding.saveNoteBtn.setOnClickListener {
            note.updatedAt = System.currentTimeMillis()
            it.hideKeyboard()
            if (binding.titleEditText.text?.trim()?.isEmpty()!! || binding.titleEditText.text?.trim()?.isEmpty()!!) {
                Snackbar.make(binding.noteEditorLayout, getString(R.string.data_required), Snackbar.LENGTH_SHORT).show()
            } else {
                note.title = binding.titleEditText.text.toString()
                note.content = binding.contentEditText.text.toString()
                if (note.noteId == 0L) {
                    viewModel.saveNote(note)
                } else {
                    viewModel.updateNote(note)
                }
            }
        }

        binding.deleteNoteButton.setOnClickListener {
            showMessageConfirmDelete()
        }
    }

    private fun initObservers() {
        viewModel.navigateToList.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController().navigateUp()
                viewModel.navigateToListDone()
            }
        })

        viewModel.getNote().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                note = it
            }
        })
    }

    private fun showMessageConfirmDelete() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.title_delete_note_message))
            .setMessage(resources.getString(R.string.content_delete_note_message))
            .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                viewModel.deleteNote(note)
                dialog.dismiss()
            }
            .show()
    }

    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun getView(): View? {
        return dialogView
    }



}