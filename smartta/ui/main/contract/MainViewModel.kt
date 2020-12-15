package vn.com.vti.smartta.ui.main.contract

import android.app.Application
import vn.com.vti.smartta.base.viewmodel.SmartTaViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(application: Application) :
    SmartTaViewModel(application),
    MainContract.ViewModel