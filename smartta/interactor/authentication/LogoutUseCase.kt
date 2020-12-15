package vn.com.vti.smartta.interactor.authentication

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import vn.com.vti.common.domain.usecase.rx.SingleUseCase
import vn.com.vti.smartta.model.repository.ClientRepository
import vn.com.vti.smartta.module.credential.CredentialManager
import javax.inject.Inject

class LogoutUseCase @Inject constructor() : SingleUseCase<Completable, Unit>() {

    @Inject
    lateinit var repository: ClientRepository

    @Inject
    lateinit var credentialManager: CredentialManager
    override fun create(params: Unit?): Single<out Completable> {
        return Single.just(repository.logout().doFinally {
            credentialManager.saveAuthenticationCredential(null)
        })
    }


}