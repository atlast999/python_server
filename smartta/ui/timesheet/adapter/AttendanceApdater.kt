package vn.com.vti.smartta.ui.timesheet.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import vn.com.vti.common.adapter.binding.BindingArrayAdapter
import vn.com.vti.common.adapter.binding.BindingHolder
import vn.com.vti.smartta.R
import vn.com.vti.smartta.databinding.ItemAttendanceLogBinding
import vn.com.vti.smartta.model.pojo.timesheet.Attendance

class AttendanceApdater : BindingArrayAdapter<Attendance>() {

    override fun onCreateHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingHolder<out ViewDataBinding, Attendance> = TimeSheetHolder(parent)

    inner class TimeSheetHolder(parent: ViewGroup) :
        BindingHolder<ItemAttendanceLogBinding, Attendance>(
            parent,
            R.layout.item_attendance_log
        ) {
        init {
            registerRootViewItemClickEvent(mItemClickListener)
        }

        override fun onBind(position: Int, model: Attendance?) {
            super.onBind(position, model)
            binder.run {
                attendance = model
                index = position
                executePendingBindings()
            }
        }
    }
}
