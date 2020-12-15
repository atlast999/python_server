package vn.com.vti.smartta.base.scene

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vn.com.vti.common.scene.BaseActivity
import vn.com.vti.common.scene.BaseBottomSheetDialogFragment
import vn.com.vti.common.scene.BaseDialogFragment
import vn.com.vti.common.scene.BaseVmFragment
import vn.com.vti.common.viewmodel.AbsViewModel
import vn.com.vti.smartta.BR
import javax.inject.Inject

abstract class SmartTaActivity<VIEWMODEL : AbsViewModel> : BaseActivity<VIEWMODEL>() {

    @Inject
    lateinit var factory: ViewModelProvider.AndroidViewModelFactory

    override fun <VM : ViewModel> createViewModel(vmClass: Class<VM>) = factory.create(vmClass)

    override fun getViewModelVariableId() = BR.vm

}

abstract class SmartTaFragment<VIEWMODEL : AbsViewModel> : BaseVmFragment<VIEWMODEL>() {

    @Inject
    lateinit var factory: ViewModelProvider.AndroidViewModelFactory

    override fun <VM : ViewModel> createViewModel(vmClass: Class<VM>) = factory.create(vmClass)

    override fun getViewModelVariableId() = BR.vm

}

abstract class SmartTaDialogFragment<VIEWMODEL : AbsViewModel> : BaseDialogFragment<VIEWMODEL>() {

    @Inject
    lateinit var factory: ViewModelProvider.AndroidViewModelFactory

    override fun <VM : ViewModel> createViewModel(vmClass: Class<VM>) = factory.create(vmClass)

    override fun getViewModelVariableId() = BR.vm

}

abstract class SmartTaBottomSheetDialogFragment<VIEWMODEL : AbsViewModel> :
    BaseBottomSheetDialogFragment<VIEWMODEL>() {

    @Inject
    lateinit var factory: ViewModelProvider.AndroidViewModelFactory

    override fun <VM : ViewModel> createViewModel(vmClass: Class<VM>) = factory.create(vmClass)

    override fun getViewModelVariableId() = BR.vm

}