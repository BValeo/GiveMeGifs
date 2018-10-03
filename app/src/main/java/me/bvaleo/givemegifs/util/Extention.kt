package me.bvaleo.givemegifs.util

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity

inline fun <reified T : ViewModel> getViewModel(activity: FragmentActivity, factory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(activity, factory)[T::class.java]
}