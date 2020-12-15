package vn.com.vti.smartta.interactor.authentication

import io.reactivex.rxjava3.core.Single
import vn.com.vti.common.domain.usecase.rx.SingleUseCase
import vn.com.vti.common.util.extension.DateTimeXs.toDate
import vn.com.vti.smartta.model.const.API_PATTERN_DATETIME
import vn.com.vti.smartta.model.const.AuthState
import vn.com.vti.smartta.module.credential.CredentialManager
import javax.inject.Inject

class FetchAuthenticationStateUseCase @Inject constructor() : SingleUseCase<String, Unit>() {

    @Inject
    lateinit var credentialManager: CredentialManager

    override fun create(params: Unit?): Single<out String> {
        return Single.defer {
            credentialManager.getAuthenticationCredential()?.run {
                if (token.isNotEmpty()) {
                    val tokenExpiredAt =
                        tokenExpiredAt.toDate(API_PATTERN_DATETIME)?.time ?: 0L
                    if (tokenExpiredAt >= System.currentTimeMillis()) {
                        Single.just(AuthState.AUTHENTICATED)
                    } else {
                        Single.just(AuthState.TOKEN_EXPIRED)
                    }
                } else Single.just(AuthState.GUEST)
            } ?: Single.just(AuthState.GUEST)
        }
    }
}