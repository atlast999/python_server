@file:Suppress("unused")

package vn.com.vti.smartta.injection

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.disposables.Disposable
import vn.com.vti.common.domain.fetcher.UseCaseFetcher
import vn.com.vti.common.domain.fetcher.impl.RxUseCaseFetcherImpl
import vn.com.vti.common.serializer.Serializer
import vn.com.vti.common.serializer.impl.GsonSerializer
import vn.com.vti.smartta.App
import vn.com.vti.smartta.model.repository.RepositoryModule
import vn.com.vti.smartta.module.credential.CredentialManager
import vn.com.vti.smartta.module.credential.impl.CredentialManagerImpl
import vn.com.vti.smartta.module.restful.RestfulApiModule
import javax.inject.Singleton

@Module(
    includes = [
        BindsDependencies::class,
        ProvidesDependencies::class,
        RestfulApiModule::class,
        RepositoryModule::class,
    ]
)
interface AppModules

@Module
interface BindsDependencies {

    @Binds
    fun bindContext(application: App): Context

    @Binds
    fun bindApplication(application: App): Application

    @Binds
    fun bindSerializer(serializer: GsonSerializer): Serializer

    @Binds
    fun bindDisposablesUseCaseFetcher(fetcher: RxUseCaseFetcherImpl): UseCaseFetcher<Disposable>

}

@Module
object ProvidesDependencies {

    @Provides
    @Singleton
    fun provideCredentialManager(context: Context, serializer: Serializer): CredentialManager {
        return CredentialManagerImpl(context, serializer)
    }
}