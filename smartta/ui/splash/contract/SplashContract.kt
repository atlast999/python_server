package vn.com.vti.smartta.ui.splash.contract

import androidx.lifecycle.LiveData
import vn.com.vti.common.viewmodel.AbsViewModel

interface SplashContract {

    interface ViewModel : AbsViewModel {

        fun getGreetings(): LiveData<String>

        fun getVersion(): LiveData<String>
    }
}