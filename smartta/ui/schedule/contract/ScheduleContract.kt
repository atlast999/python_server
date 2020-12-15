package vn.com.vti.smartta.ui.schedule.contract

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.com.vti.common.adapter.BaseAdapter
import vn.com.vti.common.ui.list.IListController
import vn.com.vti.common.viewmodel.AbsViewModel
import vn.com.vti.smartta.model.pojo.schedule.IWeekSchedule

interface ScheduleContract {

    interface ViewModel : AbsViewModel {

        fun getWeekPagerAdapter(): BaseAdapter<*>

        fun getSelectedWeekIndex(): MutableLiveData<Int>

        fun getSelectedWeekSchedule(): LiveData<IWeekSchedule>

        fun getCurrentFocusEventTime(): LiveData<Long>

        fun getListController(): IListController

        fun onPrevWeek()

        fun onNextWeek()

        fun onSpecificDateSelected(selected: Long, reload: Boolean)
    }
}

