package com.kulnois.notesapp.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
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

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    view?.let {
        activity?.hideKeyboard(it)
    }
}