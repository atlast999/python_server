package vn.com.vti.smartta.ui.main.navslider.contract

import androidx.lifecycle.LiveData
import vn.com.vti.common.ui.list.IListController
import vn.com.vti.common.viewmodel.AbsViewModel
import vn.com.vti.smartta.model.pojo.user.User

interface MainNavSliderContract {

    interface ViewModel : AbsViewModel {

        fun getListController(): IListController

        fun getUser(): LiveData<User>

        fun onClickProfile()
    }
}