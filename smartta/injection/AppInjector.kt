package vn.com.vti.smartta.injection

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import vn.com.vti.smartta.App
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModules::class, AppProviders::class, AndroidSupportInjectionModule::class])
interface AppInjector : AndroidInjector<App> {

    override fun inject(instance: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppInjector
    }
}