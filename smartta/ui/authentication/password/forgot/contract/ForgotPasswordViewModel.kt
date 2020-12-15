package vn.com.vti.smartta.ui.authentication.password.forgot.contract

import android.app.Application
import androidx.lifecycle.MutableLiveData
import vn.com.vti.common.viewmodel.Backward
import vn.com.vti.smartta.base.viewmodel.SmartTaViewModel
import vn.com.vti.smartta.interactor.authentication.ForgotPasswordUseCase
import vn.com.vti.smartta.model.pojo.authentication.ForgotPasswordRequest
import javax.inject.Inject

class ForgotPasswordViewModel @Inject constructor(application: Application) :
    SmartTaViewModel(application), ForgotPasswordContract.ViewModel {

    @Inject
    lateinit var forgotPasswordUseCase: ForgotPasswordUseCase
    private val emailUser = MutableLiveData<String>()

    override fun onBack() {
        redirect(Backward)
    }

    override fun onClickConfirm() {
        val email = emailUser.value ?: ""
        confirmResetPassword(ForgotPasswordRequest(email))
    }

    override fun getEmail(): MutableLiveData<String> = emailUser

    private fun confirmResetPassword(forgotPasswordRequest: ForgotPasswordRequest) {
        fetch(forgotPasswordUseCase,
            params = forgotPasswordRequest,
            foldSuccess = {
                // success
            })
    }

}