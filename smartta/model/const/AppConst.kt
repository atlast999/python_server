package vn.com.vti.smartta.model.const

import androidx.annotation.StringDef

@StringDef(AuthState.AUTHENTICATED, AuthState.TOKEN_EXPIRED, AuthState.GUEST)
@Retention(AnnotationRetention.SOURCE)
annotation class AuthState {

    companion object {
        const val AUTHENTICATED = "authenticated"
        const val TOKEN_EXPIRED = "token_expired"
        const val GUEST = "guest"
    }
}