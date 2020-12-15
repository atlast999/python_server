package vn.com.vti.smartta.ui.timesheet.contract

import androidx.lifecycle.LiveData
import vn.com.vti.common.ui.list.IListController
import vn.com.vti.common.viewmodel.AbsViewModel
import vn.com.vti.smartta.model.data.IDatePeriod

interface TimesheetContract {

    object Args {
        const val KEY_TIMESHEET_TYPE = "KEY_TIMESHEET_TYPE"
        const val KEY_TIMESHEET_ID = "KEY_TIMESHEET_ID"
    }

    object Action {
        const val ACTION_SORT = 1
    }

    interface ViewModel : AbsViewModel {

        fun getListController(): IListController

        fun onSortClick()

        fun getFormattedDatePeriod(): LiveData<String>

        fun getTypeSort(): LiveData<String>

        fun onDatePeriodChanged(datePeriod: IDatePeriod)

        fun isAbleToShiftPeriod(): LiveData<Boolean>

        fun onNextDate()

        fun onPreviousDate()

        fun setTypeFilter(typeFilter: Int)
    }
}