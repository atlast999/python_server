package vn.com.vti.smartta.ui.schedule.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import vn.com.vti.common.adapter.binding.BindingArrayAdapter
import vn.com.vti.common.adapter.binding.BindingHolder
import vn.com.vti.smartta.R
import vn.com.vti.smartta.databinding.ItemScheduleWeekBinding
import vn.com.vti.smartta.model.pojo.schedule.IWeekSchedule

class WeekSchedulePagerAdapter : BindingArrayAdapter<IWeekSchedule>() {

    override fun onCreateHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingHolder<out ViewDataBinding, IWeekSchedule> = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BindingHolder<ItemScheduleWeekBinding, IWeekSchedule>(parent, R.layout.item_schedule_week) {

        override fun onBind(position: Int, model: IWeekSchedule?) {
            super.onBind(position, model)
            binder.apply {
                data = model
                executePendingBindings()
            }
        }
    }
}