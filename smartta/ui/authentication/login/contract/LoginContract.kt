package vn.com.vti.smartta.ui.authentication.login.contract

import androidx.lifecycle.MutableLiveData
import vn.com.vti.common.viewmodel.AbsViewModel

interface LoginContract {
    interface ViewModel : AbsViewModel {
        fun getEmail(): MutableLiveData<String>

        fun getPassword(): MutableLiveData<String>

        fun onClickLogin()

        fun onClickForgotPass()
    }
}