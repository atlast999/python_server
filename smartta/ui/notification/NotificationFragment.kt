package vn.com.vti.smartta.ui.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import vn.com.vti.smartta.BR
import vn.com.vti.smartta.R
import vn.com.vti.smartta.base.scene.SmartTaFragment
import vn.com.vti.smartta.ui.notification.contract.NotificationContract
import vn.com.vti.smartta.ui.notification.contract.NotificationViewModel

class NotificationFragment : SmartTaFragment<NotificationContract.ViewModel>() {

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false)

    override fun getViewModelClass() = NotificationViewModel::class.java

    override fun getViewModelVariableId() = BR.vm

}