package vn.com.vti.smartta.interactor.authentication

import io.reactivex.rxjava3.core.Single
import vn.com.vti.common.domain.usecase.rx.SingleUseCase
import vn.com.vti.smartta.R
import vn.com.vti.smartta.exception.InvalidFieldInputException
import vn.com.vti.smartta.exception.ValidationCode
import vn.com.vti.smartta.model.const.AuthEntity
import vn.com.vti.smartta.model.pojo.BaseResponse
import vn.com.vti.smartta.model.pojo.authentication.LoginRequest
import vn.com.vti.smartta.model.pojo.authentication.LoginResponse
import vn.com.vti.smartta.model.repository.ClientRepository
import vn.com.vti.smartta.module.credential.CredentialManager
import vn.com.vti.smartta.util.isEmail
import vn.com.vti.smartta.util.isValidFullName
import vn.com.vti.smartta.util.isValidPassword
import javax.inject.Inject

class LoginUseCase @Inject constructor() :
    SingleUseCase<BaseResponse<LoginResponse>, LoginRequest>() {
    @Inject
    lateinit var credentialManager: CredentialManager

    @Inject
    lateinit var repository: ClientRepository

    override fun create(params: LoginRequest?): Single<out BaseResponse<LoginResponse>> {
        return params?.let {
            Single.create<LoginRequest> { emitter ->
                if (it.method == AuthEntity.EMAIL && !it.account.isEmail()) {
                    emitter.onError(
                        InvalidFieldInputException(
                            ValidationCode.ERROR_EMAIL_INVALID,
                            R.string.error_email_invalid
                        )
                    )
                }
                else if (it.method == AuthEntity.USERNAME && !it.account.isValidFullName()) {
                    emitter.onError(
                        InvalidFieldInputException(
                            ValidationCode.ERROR_NAME_INVALID,
                            R.string.error_usename
                        )
                    )
                }else if (!it.password.isValidPassword()) {
                    emitter.onError(
                        InvalidFieldInputException(
                            ValidationCode.ERROR_PASSWORD_INVALID,
                            R.string.error_password_invalid
                        )
                    )
                } else {
                    emitter.onSuccess(params)
                }
            }.flatMap {
                repository.login(it)
            }.doOnSuccess {
                credentialManager.saveAuthenticationCredential(it.result)
            }
        } ?: errorParamsSingle()
    }
}