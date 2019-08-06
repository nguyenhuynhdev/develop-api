package com.nguyen.develop.di.components

import com.nguyen.develop.di.modules.ActivityModule
import com.nguyen.develop.di.scope.ActivityScope
import com.nguyen.develop.view.fragment.NetworkFragment
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(networkFragment: NetworkFragment)

}