package vn.com.vti.smartta.ui.authentication.login.contract

import android.app.Application
import androidx.lifecycle.MutableLiveData
import vn.com.vti.common.viewmodel.NavGraphDirection
import vn.com.vti.common.viewmodel.toIntentDirection
import vn.com.vti.smartta.R
import vn.com.vti.smartta.base.viewmodel.SmartTaViewModel
import vn.com.vti.smartta.exception.InvalidFieldInputException
import vn.com.vti.smartta.interactor.authentication.LoginUseCase
import vn.com.vti.smartta.model.const.AuthEntity
import vn.com.vti.smartta.model.pojo.authentication.LoginRequest
import vn.com.vti.smartta.ui.main.MainActivity
import vn.com.vti.smartta.util.isEmail
import javax.inject.Inject

class LoginViewModel @Inject constructor(application: Application) :
    SmartTaViewModel(application), LoginContract.ViewModel {

    @Inject
    lateinit var loginUseCase: LoginUseCase

    private val emailAddress = MutableLiveData<String>()
    private val userPassword = MutableLiveData<String>()


    override fun getEmail(): MutableLiveData<String> = emailAddress

    override fun getPassword(): MutableLiveData<String> = userPassword

    override fun onClickLogin() {
        val email = emailAddress.value ?: ""
        val password = userPassword.value ?: ""
        if (email.isEmail()) {
            requestLogin(
                LoginRequest(
                    method = AuthEntity.EMAIL,
                    account = email,
                    password = password
                )
            )
        } else {
            requestLogin(
                LoginRequest(
                    method = AuthEntity.USERNAME,
                    account = email,
                    password = password
                )
            )
        }

    }

    override fun onClickForgotPass() {
        redirect(NavGraphDirection(R.id.toForgotPassword))
    }

    private fun requestLogin(loginRequest: LoginRequest) {
        fetch(
            loginUseCase,
            params = loginRequest,
            foldSuccess = {
                if (it.result.token.isNotEmpty()) {
                    redirect(MainActivity::class.java.toIntentDirection(finish = false))
                } else {
                    toast(it.message)
                }
            },
            foldError = { throwable ->
                (throwable as? InvalidFieldInputException)?.let { invalidFieldInputException ->
                    invalidFieldInputException.message?.let { toast(it) }
                    true
                } ?: false
            },
            blocking = true
        )
    }
}