package vn.com.vti.smartta.model.repository

import dagger.Module
import dagger.Provides
import vn.com.vti.smartta.model.repository.impl.ClientRepositoryImpl
import vn.com.vti.smartta.module.restful.service.PrimaryApiService
import javax.inject.Singleton

@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideClientRepository(primaryApiService: PrimaryApiService): ClientRepository {
        return ClientRepositoryImpl(primaryApiService)
    }
}