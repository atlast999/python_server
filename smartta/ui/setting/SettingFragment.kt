package vn.com.vti.smartta.ui.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import vn.com.vti.smartta.BR
import vn.com.vti.smartta.R.layout
import vn.com.vti.smartta.base.scene.SmartTaFragment
import vn.com.vti.smartta.ui.setting.contract.SettingViewModel

class SettingFragment : SmartTaFragment<SettingViewModel>() {

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding =
        DataBindingUtil.inflate(inflater, layout.fragment_setting, container, false)

    override fun getViewModelClass() = SettingViewModel::class.java

    override fun getViewModelVariableId() = BR.vm
}