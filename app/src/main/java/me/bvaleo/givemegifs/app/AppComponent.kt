package me.bvaleo.givemegifs.app

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import me.bvaleo.givemegifs.net.NetModule
import me.bvaleo.givemegifs.ui.activity.MainActivityModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, MainActivityModule::class, NetModule::class])
interface AppComponent : AndroidInjector<App>{
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}