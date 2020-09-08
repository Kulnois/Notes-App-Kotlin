package com.kulnois.notesapp.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kulnois.notesapp.adapter.NoteAdapter
import com.kulnois.notesapp.model.Note
import java.text.SimpleDateFormat

/**
 * Created by @kulnois on 7/09/2020.
 */

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: MutableList<Note>?) {
    val adapter = recyclerView.adapter as NoteAdapter
    adapter.submitList(data)
}

@BindingAdapter("setFormattedDate")
fun TextView.setFormattedDate(date: Long) {
    text = SimpleDateFormat("MM-dd hh:mm a").format(date).toString()
}