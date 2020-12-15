package vn.com.vti.smartta.ui.profile.avatar.contract

import android.app.Application
import vn.com.vti.common.viewmodel.Finish
import vn.com.vti.common.viewmodel.impl.BaseInteractorViewModel
import javax.inject.Inject

class AvatarViewModel @Inject constructor(application: Application) :
        BaseInteractorViewModel(application), AvatarContract.ViewModel {

    override fun onClickClose() {
        redirect(Finish)
    }

}