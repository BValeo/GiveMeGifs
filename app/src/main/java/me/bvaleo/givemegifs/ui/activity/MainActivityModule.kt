package me.bvaleo.givemegifs.ui.activity

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import me.bvaleo.givemegifs.ui.fragment.FragmentsModule
import me.bvaleo.givemegifs.viewmodel.MainViewModel
import me.bvaleo.givemegifs.viewmodel.ViewModelFactory
import me.bvaleo.givemegifs.viewmodel.ViewModelKey

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentsModule::class])
    internal abstract fun mainActivity(): MainActivity

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsViewModel(viewModel: MainViewModel): ViewModel
}