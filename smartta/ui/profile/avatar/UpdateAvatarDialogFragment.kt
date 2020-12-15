package vn.com.vti.smartta.ui.profile.avatar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import vn.com.vti.smartta.R
import vn.com.vti.smartta.base.scene.SmartTaBottomSheetDialogFragment
import vn.com.vti.smartta.ui.profile.avatar.contract.AvatarContract
import vn.com.vti.smartta.ui.profile.avatar.contract.AvatarViewModel

class UpdateAvatarDialogFragment : SmartTaBottomSheetDialogFragment<AvatarContract.ViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_BottomSheet)
    }

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): ViewDataBinding =
        DataBindingUtil.inflate(inflater, R.layout.dialog_update_avatar, container, false)

    override fun getViewModelClass(): Class<out ViewModel> = AvatarViewModel::class.java

    override fun onSceneReady() {
        super.onSceneReady()
        isCancelable = true
    }
}