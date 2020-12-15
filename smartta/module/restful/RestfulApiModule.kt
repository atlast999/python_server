package vn.com.vti.smartta.module.restful

import android.content.Context
import dagger.Module
import dagger.Provides
import vn.com.vti.common.network.Retrofits
import vn.com.vti.common.network.interceptor.ConnectivityPreInterceptor
import vn.com.vti.smartta.BuildConfig
import vn.com.vti.smartta.module.credential.CredentialManager
import vn.com.vti.smartta.module.restful.auth.ClientServiceAuthenticator
import vn.com.vti.smartta.module.restful.service.PrimaryApiService
import vn.com.vti.smartta.module.restful.service.PrimaryServiceHeaderPreInterceptor
import javax.inject.Singleton

@Module
object RestfulApiModule {

    @Singleton
    @Provides
    fun provideClientService(
        context: Context,
        credentialManager: CredentialManager
    ): PrimaryApiService =
        Retrofits.newClient(
            domain = BuildConfig.PRIMARY_API_GATEWAY,
            preRequestInterceptor = listOf(
                ConnectivityPreInterceptor(context),
                ClientServiceAuthenticator(credentialManager),
                PrimaryServiceHeaderPreInterceptor()
            )
        ).create(PrimaryApiService::class.java)
}
