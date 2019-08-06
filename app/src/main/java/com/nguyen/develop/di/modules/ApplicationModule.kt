package com.nguyen.develop.di.modules

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nguyen.develop.AndroidApplication
import com.nguyen.develop.di.ViewModelFactory
import com.nguyen.develop.di.ViewModelMapKey
import com.nguyen.develop.network.NetWorkRequest
import com.nguyen.develop.network.VolleyHelper
import com.nguyen.develop.viewmodel.NetWorkViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AndroidApplication) {

    @Singleton
    @Provides
    internal fun provideContext(): Context = application.applicationContext

    @Singleton
    @Provides
    internal fun provideNetWorkRequest(
            netWorkRequest: NetWorkRequest): VolleyHelper = netWorkRequest

    @Provides
    @IntoMap
    @ViewModelMapKey(NetWorkViewModel::class)
    internal fun bindNetWorkViewModel(
            netWorkViewModel: NetWorkViewModel): ViewModel = netWorkViewModel

    @Provides
    internal fun bindViewModelFactory(
            viewModelFactory: ViewModelFactory): ViewModelProvider.Factory = viewModelFactory

}