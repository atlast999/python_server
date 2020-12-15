package vn.com.vti.smartta.ui.timesheet.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import vn.com.vti.common.adapter.OnItemClick
import vn.com.vti.common.adapter.binding.BindingArrayAdapter
import vn.com.vti.common.adapter.binding.BindingHolder
import vn.com.vti.smartta.R
import vn.com.vti.smartta.databinding.ItemDetailTimesheetBinding
import vn.com.vti.smartta.model.pojo.timesheet.Attendance
import vn.com.vti.smartta.model.pojo.timesheet.UserTimesheet


class TimesheetAdapter(private var onAttendanceClick: OnItemClick<Attendance>? = null) :
    BindingArrayAdapter<UserTimesheet>() {
    override fun onCreateHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingHolder<out ViewDataBinding, UserTimesheet> = TimeSheetHolder(parent)

    inner class TimeSheetHolder(parent: ViewGroup) :
        BindingHolder<ItemDetailTimesheetBinding, UserTimesheet>(
            parent,
            R.layout.item_detail_timesheet
        ) {

        init {
            binder.adapter = AttendanceApdater().also {
                it.setItemClickListener(onAttendanceClick)
            }
            registerChildViewClickEvent(
                binder.viewUser
            ) { position, _, _ ->
                binder.timesheet?.apply {
                    isExpandTimeSheet = !isExpandTimeSheet
                }
                notifyItemChanged(position)
            }
        }

        override fun onBind(position: Int, model: UserTimesheet?) {
            super.onBind(position, model)
            binder.run {
                adapter?.apply {
                    setData(model?.attendances)
                    notifyDataSetChanged()
                }
                timesheet = model
                executePendingBindings()
            }
        }

    }
}