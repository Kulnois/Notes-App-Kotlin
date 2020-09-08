package com.kulnois.notesapp.util

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
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

@BindingAdapter("setFormattedDateAdd")
fun TextView.setFormattedDateAdd(date: Long) {
    println(date)
    text = if (date == 0L) {
        "Creado, Hoy " + SimpleDateFormat("hh:mm a").format(System.currentTimeMillis()).toString()
    } else {
        "Modificado, Hoy " + SimpleDateFormat("hh:mm a").format(System.currentTimeMillis()).toString()
    }
}

@BindingAdapter("isVisible")
fun View.isVisible(bool: Boolean) {
    this.isVisible = bool
}