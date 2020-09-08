package com.kulnois.notesapp.ui.list

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kulnois.notesapp.adapter.NoteAdapter
import com.kulnois.notesapp.database.NoteDatabase
import com.kulnois.notesapp.database.NoteDatabaseDao
import com.kulnois.notesapp.databinding.FragmentListNoteBinding
import com.kulnois.notesapp.repository.NoteRepository
import kotlinx.android.synthetic.main.fragment_list_note.*

/**
 * Created by @kulnois on 5/09/2020.
 */

class NoteListFragment : Fragment() {

    private lateinit var binding: FragmentListNoteBinding
    private lateinit var repository: NoteRepository
    private lateinit var noteDatabaseDao: NoteDatabaseDao
    private lateinit var application: Application
    private lateinit var viewModel: NoteListViewModel
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListNoteBinding.inflate(inflater)
        initUI()
        return binding.root
    }

    private fun initUI() {
        @Suppress("UNUSED_VARIABLE")
        application = requireNotNull(activity).application

        noteDatabaseDao = NoteDatabase.getInstance(application).noteDatabaseDao
        repository = NoteRepository(noteDatabaseDao)
        val viewModelFactory = NoteListViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get((NoteListViewModel::class.java))

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        initObservers()

        adapter = NoteAdapter(NoteAdapter.OnClickListener {
            println(it.title)
            viewModel.displayPropertyEdit(it)
        })

        binding.itemGrid.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter?.filter(newText)
                binding.itemGrid.adapter = adapter
                return true
            }

        })
    }

    private fun initObservers() {
        viewModel.navigateToEditor.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController().navigate(NoteListFragmentDirections
                    .actionNoteListFragmentToNoteEditorFragment())
                viewModel.navigateToEditorDone()
            }
        })
    }


}