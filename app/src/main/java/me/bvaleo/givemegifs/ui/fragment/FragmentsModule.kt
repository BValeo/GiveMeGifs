package me.bvaleo.givemegifs.ui.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module()
internal abstract class FragmentsModule {
    @ContributesAndroidInjector
    internal abstract fun contributeTrendigFragment(): TrendingFragment

    @ContributesAndroidInjector
    internal abstract fun contributeSearchFragment(): SearchFragment
}