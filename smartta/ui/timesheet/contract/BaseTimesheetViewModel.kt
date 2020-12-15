package vn.com.vti.smartta.ui.timesheet.contract

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import vn.com.vti.common.adapter.BaseAdapter
import vn.com.vti.common.ui.list.IListController
import vn.com.vti.common.ui.list.impl.ListController
import vn.com.vti.common.util.livedata.NonNullMutableLiveData
import vn.com.vti.common.viewmodel.ActionDirection
import vn.com.vti.smartta.R
import vn.com.vti.smartta.base.viewmodel.SmartTaViewModel
import vn.com.vti.smartta.model.const.QueryScope
import vn.com.vti.smartta.model.data.*
import vn.com.vti.smartta.model.pojo.timesheet.TimesheetFilter

abstract class BaseTimesheetViewModel(application: Application) :
    SmartTaViewModel(application), TimesheetContract.ViewModel {
    protected lateinit var controller: IListController
    private var typeTimeSheet: String = QueryScope.ME
    private val selectedDateRange = NonNullMutableLiveData<IDatePeriod>(SingleDay())
    private val selectDate = Transformations.map(selectedDateRange) {
        it.format("dd MMM, yyyy", " - ")
    }
    private val flagPeriodShiftable =
        Transformations.map(selectedDateRange) { it is IPeriodShiftable }
    private val nameSort = Transformations.map(selectedDateRange) {
        when (it) {
            is WeekPeriod -> resource.getString(R.string.week)
            is MonthPeriod -> resource.getString(R.string.month)
            is SingleDay -> resource.getString(R.string.day)
            else -> resource.getString(R.string.sort_by)
        }
    }
    private val typeFilter = NonNullMutableLiveData(TimesheetFilter.LATE)
    private var scopeId: Int? = null

    private fun loadMasterTimesheet() {
        onLoadMasterTimesheet(selectedDateRange.value, typeFilter.value, scopeId)
    }

    override fun onCreate() {
        super.onCreate()
        controller = ListController(provideTimesheetAdapter())
    }

    override fun onBind(args: Bundle?) {
        super.onBind(args)
        args?.let {
            typeTimeSheet =
                it.getString(TimesheetContract.Args.KEY_TIMESHEET_TYPE, QueryScope.ME)
            scopeId = it.getInt(TimesheetContract.Args.KEY_TIMESHEET_ID, 0).let {
                if (it > 0) it else null
            }
        }
    }

    override fun onReady() {
        super.onReady()
        loadMasterTimesheet()
    }

    override fun getListController(): IListController = controller

    override fun onSortClick() {
        redirect(ActionDirection(TimesheetContract.Action.ACTION_SORT, finish = false))
    }

    override fun getFormattedDatePeriod(): LiveData<String> = selectDate

    override fun getTypeSort(): LiveData<String> = nameSort

    override fun isAbleToShiftPeriod(): LiveData<Boolean> = flagPeriodShiftable

    override fun onNextDate() {
        (selectedDateRange.value as? IPeriodShiftable)?.let {
            selectedDateRange.value = it.nextShift()
        }
    }

    override fun onPreviousDate() {
        (selectedDateRange.value as? IPeriodShiftable)?.let {
            selectedDateRange.value = it.prevShift()
        }
    }

    override fun setTypeFilter(typeFilter: Int) {
        this.typeFilter.value = typeFilter
        loadMasterTimesheet()
    }

    override fun onDatePeriodChanged(datePeriod: IDatePeriod) {
        selectedDateRange.value = datePeriod
        loadMasterTimesheet()
    }

    abstract fun onLoadMasterTimesheet(period: IDatePeriod, filter: Int, scopeId: Int?)

    abstract fun provideTimesheetAdapter(): BaseAdapter<*>
}

