package com.kulnois.notesapp.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.kulnois.notesapp.databinding.FragmentListNoteBinding

/**
 * Created by @kulnois on 5/09/2020.
 */

class NoteListFragment : Fragment() {

    private lateinit var binding: FragmentListNoteBinding
    private val viewModel by viewModels<NoteListViewModel>()

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
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        initObservers()
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