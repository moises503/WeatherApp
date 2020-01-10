package com.sngular.core.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

val Fragment.requireArguments
    get() = this.arguments ?: throw IllegalStateException("Arguments should exist!")

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun ViewGroup.inflate(layoutId : Int) : View = LayoutInflater.from(context)
    .inflate(layoutId, this, false)

fun AppCompatActivity.setToolbar(toolbar : Toolbar, toolbarTitle : TextView,
                                 title : String, homeAsUpEnabled : Boolean) {
    this.setSupportActionBar(toolbar)
    this.supportActionBar?.setDisplayShowTitleEnabled(false)
    toolbarTitle.text = title
    this.supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
}

fun Date.toString(format : String) : String {
    val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
    return try {
        simpleDateFormat.format(this)
    } catch (ex : Exception) {
        ""
    }
}

fun String.toDate(format: String) : Date? {
    val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
    return try {
        simpleDateFormat.parse(this)
    } catch (ex : Exception) {
        null
    }
}