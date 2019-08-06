package com.nguyen.develop.di.components

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nguyen.develop.di.modules.ApplicationModule
import com.nguyen.develop.network.NetWorkRequest
import com.nguyen.develop.network.VolleyHelper
import com.nguyen.develop.view.activity.BaseActivity
import dagger.Component
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
@Component(modules =
[ApplicationModule::class])
interface ApplicationComponent {

    fun inject(baseActivity: BaseActivity)

    //Singleton
    val context: Context

    val volleyHelper: VolleyHelper

    val viewModel: Map<Class<out ViewModel>, Provider<ViewModel>>

    val viewModelFactory: ViewModelProvider.Factory
}