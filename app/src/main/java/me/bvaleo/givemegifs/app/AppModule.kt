package me.bvaleo.givemegifs.app

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module()
class AppModule {

    @Provides
    @Singleton
    fun provideContextApp(app: App) = app.applicationContext

}