package com.kulnois.notesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kulnois.notesapp.databinding.ItemNoteBinding
import com.kulnois.notesapp.model.Note

/**
 * Created by @kulnois on 7/09/2020.
 */

class NoteAdapter (private val onClickListener: OnClickListener): ListAdapter<Note, NoteAdapter.NoteViewHolder>(DiffCallback), Filterable {

    var filteredNotes: List<Note>? = null
    var notes = mutableListOf<Note>()
    var filtered = true

    class NoteViewHolder(private var binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.note = note
            binding.executePendingBindings()
        }

    }

    override fun submitList(list: MutableList<Note>?) {
        if (list != null && filtered) {
            notes = list
        }
        super.submitList(list?.let { ArrayList(it) })
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.noteId == newItem.noteId
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteAdapter.NoteViewHolder {
        return NoteViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {
        val rickAndMorty = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(rickAndMorty)
        }
        holder.bind(rickAndMorty)
    }

    class OnClickListener(val clickListener: (rickAndMorty: Note) -> Unit) {
        fun onClick(rickAndMorty: Note) = clickListener(rickAndMorty)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                filteredNotes = if (charString.isEmpty()) notes
                else {
                    filtered = false
                    notes.filter {
                        it.title.contains(charString, true) || it.content.contains(charString, true)
                    }
                }
                val results = FilterResults()
                results.values = filteredNotes
                return results
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                val charString = charSequence.toString()
                if (charString.trim().isNotEmpty()) {
                    filteredNotes = filterResults.values as ArrayList<Note>
                    submitList(filteredNotes as ArrayList<Note>)
                } else {
                    submitList(notes)
                }
            }
        }
    }
}