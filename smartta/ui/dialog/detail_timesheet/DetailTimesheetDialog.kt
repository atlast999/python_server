package vn.com.vti.smartta.ui.dialog.detail_timesheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import vn.com.vti.common.extension.inflateBinding
import vn.com.vti.smartta.R
import vn.com.vti.smartta.base.scene.SmartTaDialogFragment
import vn.com.vti.smartta.ui.dialog.detail_timesheet.contract.DetailTimeSheetContract
import vn.com.vti.smartta.ui.dialog.detail_timesheet.contract.DetailTimeSheetViewModel

class DetailTimesheetDialog : SmartTaDialogFragment<DetailTimeSheetContract.ViewModel>() {

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding = inflater.inflateBinding(R.layout.dialog_detail_timesheet, container)

    override fun getViewModelClass(): Class<out ViewModel> = DetailTimeSheetViewModel::class.java
}