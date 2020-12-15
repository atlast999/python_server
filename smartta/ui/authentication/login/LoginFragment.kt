package vn.com.vti.smartta.ui.authentication.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import vn.com.vti.smartta.R
import vn.com.vti.smartta.base.scene.SmartTaFragment
import vn.com.vti.smartta.ui.authentication.login.contract.LoginContract
import vn.com.vti.smartta.ui.authentication.login.contract.LoginViewModel

class LoginFragment : SmartTaFragment<LoginContract.ViewModel>() {
    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

    override fun getViewModelClass(): Class<out ViewModel> = LoginViewModel::class.java
}
