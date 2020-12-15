package vn.com.vti.smartta.ui.profile.contract

import androidx.lifecycle.LiveData
import vn.com.vti.common.viewmodel.AbsViewModel
import vn.com.vti.smartta.model.pojo.user.User


interface ProfileContract {

    interface ViewModel : AbsViewModel {

        fun getUser(): LiveData<User>

        fun editProfile(): LiveData<Boolean>

        fun onClickEdit()

        fun onClickAvatar()

        fun onClickUploadPhoto()

        fun onClickLogout()

    }

    object Action {
        const val ACTION_CHANGE_AVT = 1
        const val ACTION_UPLOAD_PHOTO = 2
        const val ACTION_LOGOUT = 3
    }
}