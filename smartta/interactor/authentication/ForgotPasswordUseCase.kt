package vn.com.vti.smartta.interactor.authentication

import io.reactivex.rxjava3.core.Single
import vn.com.vti.common.domain.usecase.rx.SingleUseCase
import vn.com.vti.smartta.model.pojo.authentication.ForgotPasswordRequest
import vn.com.vti.smartta.model.pojo.authentication.ForgotPasswordResponse
import vn.com.vti.smartta.model.repository.ClientRepository
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor() :
    SingleUseCase<ForgotPasswordResponse, ForgotPasswordRequest>() {

    @Inject
    lateinit var repository: ClientRepository

    override fun create(params: ForgotPasswordRequest?): Single<out ForgotPasswordResponse> {
        params.let {
            return ForgotPasswordResponse("resetPasswordTransactionId").let { Single.just(it) }
        }

    }
}