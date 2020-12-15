package vn.com.vti.smartta.module.restful.auth

import vn.com.vti.common.network.interceptor.AuthCredential
import vn.com.vti.common.network.interceptor.AuthenticationInterceptor
import vn.com.vti.common.network.interceptor.BearerAuth
import vn.com.vti.smartta.module.credential.CredentialManager

class ClientServiceAuthenticator(private val credentialManager: CredentialManager) : AuthenticationInterceptor{

    private val bearerAuth by lazy {
        BearerAuth("fake-token")
    }

    override fun provideAuthCredential(): AuthCredential = bearerAuth

}