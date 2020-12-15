package vn.com.vti.smartta.module.credential

import vn.com.vti.smartta.model.pojo.authentication.LoginResponse

interface CredentialManager {

    fun saveAuthenticationCredential(authCredential: LoginResponse?)

    fun getAuthToken(): String?

    fun getAuthenticationCredential(): LoginResponse?
}