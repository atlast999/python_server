package vn.com.vti.smartta.ui.profile.avatar.contract

import vn.com.vti.common.viewmodel.AbsViewModel

interface AvatarContract {

    interface ViewModel : AbsViewModel {
        fun onClickClose()
    }
}