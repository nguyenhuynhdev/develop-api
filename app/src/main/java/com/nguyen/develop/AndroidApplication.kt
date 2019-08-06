package com.nguyen.develop

import android.app.Application
import com.nguyen.develop.di.components.ApplicationComponent
import com.nguyen.develop.di.components.DaggerApplicationComponent
import com.nguyen.develop.di.modules.ApplicationModule

class AndroidApplication : Application() {

    companion object {

        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        buildDependencies()
    }

    private fun buildDependencies(){
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}