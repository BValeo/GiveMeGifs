package me.bvaleo.givemegifs.util

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.widget.Toast

inline fun <reified T : ViewModel> getViewModel(activity: FragmentActivity, factory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(activity, factory)[T::class.java]
}

fun Fragment.toast(text: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this.context, text, duration).show()
}