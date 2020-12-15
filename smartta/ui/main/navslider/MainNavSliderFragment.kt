package vn.com.vti.smartta.ui.main.navslider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import vn.com.vti.common.scene.BaseActivity
import vn.com.vti.common.util.extension.inflateBinding
import vn.com.vti.common.viewmodel.Direction
import vn.com.vti.smartta.R
import vn.com.vti.smartta.base.scene.SmartTaFragment
import vn.com.vti.smartta.ui.main.navslider.contract.MainNavSliderContract
import vn.com.vti.smartta.ui.main.navslider.contract.MainNavSliderViewModel

class MainNavSliderFragment : SmartTaFragment<MainNavSliderContract.ViewModel>() {

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding =
        inflater.inflateBinding(R.layout.fragment_main_nav_slider, container)

    override fun getViewModelClass(): Class<out ViewModel> = MainNavSliderViewModel::class.java

    override fun onDispatchDirectionEvent(direction: Direction): Boolean {
        return (activity as? BaseActivity<*>)?.let {
            it.dispatchDelegationDirectionEvent(direction)
            true
        } ?: super.onDispatchDirectionEvent(direction)
    }
}