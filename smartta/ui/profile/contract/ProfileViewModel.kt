package vn.com.vti.smartta.ui.profile.contract

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.com.vti.common.util.extension.toggle
import vn.com.vti.common.util.livedata.NonNullMutableLiveData
import vn.com.vti.common.viewmodel.ActionDirection
import vn.com.vti.smartta.base.viewmodel.SmartTaViewModel
import vn.com.vti.smartta.interactor.authentication.LogoutUseCase
import vn.com.vti.smartta.interactor.user.GetUserInfoUseCase
import vn.com.vti.smartta.model.pojo.user.User
import vn.com.vti.smartta.model.pojo.user.UserInfoRequest
import javax.inject.Inject


class ProfileViewModel @Inject constructor(application: Application) :
    SmartTaViewModel(application), ProfileContract.ViewModel {

    @Inject
    lateinit var getUserInfoUseCase: GetUserInfoUseCase

    @Inject
    lateinit var logoutUseCase: LogoutUseCase


    private val liveDataUser = MutableLiveData<User>()
    private val allowEdit = NonNullMutableLiveData(false)

    override fun getUser(): LiveData<User> = liveDataUser

    override fun editProfile(): LiveData<Boolean> = allowEdit

    override fun onClickEdit() {
        allowEdit.toggle()
    }

    override fun onClickAvatar() {
        if (allowEdit.value) {
            redirect(ActionDirection(ProfileContract.Action.ACTION_CHANGE_AVT, finish = false))
        }
    }

    override fun onClickUploadPhoto() {
        redirect(ActionDirection(ProfileContract.Action.ACTION_UPLOAD_PHOTO, finish = false))
    }

    override fun onClickLogout() {
        fetch(
            logoutUseCase, foldSuccess = {
                redirect(ActionDirection(ProfileContract.Action.ACTION_LOGOUT, finish = false))
            })
    }

    override fun onReady() {
        super.onReady()
        getUserInfo()
    }

    private fun getUserInfo() {
        fetch(getUserInfoUseCase,
            params = UserInfoRequest(),
            foldSuccess = {
                liveDataUser.value = it.result
            })
    }

}

