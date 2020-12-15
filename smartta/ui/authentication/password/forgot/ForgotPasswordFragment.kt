package vn.com.vti.smartta.ui.authentication.password.forgot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import vn.com.vti.common.extension.inflateBinding
import vn.com.vti.smartta.R
import vn.com.vti.smartta.base.scene.SmartTaFragment
import vn.com.vti.smartta.ui.authentication.password.forgot.contract.ForgotPasswordContract
import vn.com.vti.smartta.ui.authentication.password.forgot.contract.ForgotPasswordViewModel

class ForgotPasswordFragment : SmartTaFragment<ForgotPasswordContract.ViewModel>() {
    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding = inflater.inflateBinding(R.layout.fragment_forgot_password, container)

    override fun getViewModelClass(): Class<out ViewModel> = ForgotPasswordViewModel::class.java
}