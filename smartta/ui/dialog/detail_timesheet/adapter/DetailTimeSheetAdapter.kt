package vn.com.vti.smartta.ui.dialog.detail_timesheet.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import vn.com.vti.common.adapter.binding.BindingArrayAdapter
import vn.com.vti.common.adapter.binding.BindingHolder
import vn.com.vti.smartta.R
import vn.com.vti.smartta.databinding.ItemInfoTimesheetBinding
import vn.com.vti.smartta.model.pojo.timesheet.AttendanceLogs

class DetailTimeSheetAdapter : BindingArrayAdapter<AttendanceLogs>() {
    override fun onCreateHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingHolder<out ViewDataBinding, AttendanceLogs> = DetailTimeSheetHolder(parent)

    inner class DetailTimeSheetHolder(parent: ViewGroup) :
        BindingHolder<ItemInfoTimesheetBinding, AttendanceLogs>(
            parent,
            R.layout.item_info_timesheet
        ) {
        override fun onBind(position: Int, model: AttendanceLogs?) {
            super.onBind(position, model)
            binder.run {
                attendaceLogs = model
                index = position + 1
                executePendingBindings()
            }
        }

    }
}