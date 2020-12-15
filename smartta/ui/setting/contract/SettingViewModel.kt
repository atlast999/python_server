package vn.com.vti.smartta.ui.setting.contract

import android.app.Application
import vn.com.vti.common.viewmodel.impl.BaseInteractorViewModel
import javax.inject.Inject

class SettingViewModel @Inject constructor(application: Application) :
    BaseInteractorViewModel(application), SettingContract.ViewModel