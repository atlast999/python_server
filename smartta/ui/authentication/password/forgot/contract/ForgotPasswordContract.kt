package vn.com.vti.smartta.ui.authentication.password.forgot.contract

import androidx.lifecycle.MutableLiveData
import vn.com.vti.common.viewmodel.AbsViewModel

interface ForgotPasswordContract {
    interface ViewModel : AbsViewModel {
        fun onBack()
        fun onClickConfirm()
        fun getEmail(): MutableLiveData<String>
    }

}