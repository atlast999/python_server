package vn.com.vti.smartta.module.credential.impl

import android.content.Context
import vn.com.vti.common.serializer.Serializer
import vn.com.vti.common.util.AppSharedPreferences
import vn.com.vti.smartta.model.pojo.authentication.LoginResponse
import vn.com.vti.smartta.module.credential.CredentialManager
import javax.inject.Inject

class CredentialManagerImpl @Inject constructor(context: Context, serializer: Serializer) :
    CredentialManager {

    companion object {
        private const val KEY_AUTH_CREDENTIAL = "auth_credentials"
    }

    private var authCredential: LoginResponse? = null
    private var token: String? = null

    private val preferences by lazy {
        AppSharedPreferences(context, "CredentialManager", serializer)
    }

    init {
        authCredential = preferences.getSerializable(KEY_AUTH_CREDENTIAL)
        token = authCredential?.token
    }

    override fun saveAuthenticationCredential(authCredential: LoginResponse?) {
        preferences.putSerializable(KEY_AUTH_CREDENTIAL, authCredential)
        this.authCredential = authCredential
        this.token = authCredential?.token

    }

    override fun getAuthToken() = token

    override fun getAuthenticationCredential(): LoginResponse? = authCredential

}