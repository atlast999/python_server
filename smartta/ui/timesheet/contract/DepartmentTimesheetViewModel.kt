package vn.com.vti.smartta.ui.timesheet.contract

import android.app.Application
import vn.com.vti.common.adapter.BaseAdapter
import vn.com.vti.common.viewmodel.NavActionDirection
import vn.com.vti.smartta.interactor.timesheet.FetchDepartmentTimeSheetUseCase
import vn.com.vti.smartta.model.const.QueryScope
import vn.com.vti.smartta.model.data.IDatePeriod
import vn.com.vti.smartta.model.pojo.timesheet.TimesheetRequest
import vn.com.vti.smartta.ui.timesheet.TimesheetFragmentDirections
import vn.com.vti.smartta.ui.timesheet.adapter.TimesheetAdapter
import javax.inject.Inject

class DepartmentTimesheetViewModel @Inject constructor(application: Application) :
    BaseTimesheetViewModel(application) {

    private val adapter = TimesheetAdapter(onAttendanceClick = { _, _, t ->
        t?.let {
            redirect(
                NavActionDirection(
                    TimesheetFragmentDirections.toDetailTimesheetDialog(
                        it.date, QueryScope.USER
                    )
                )
            )
        }
    })

    @Inject
    lateinit var fetchDepartmentTimeSheetUseCase: FetchDepartmentTimeSheetUseCase

    override fun onLoadMasterTimesheet(period: IDatePeriod, filter: Int, scopeId: Int?) {
        adapter.clear()
        fetch(
            useCaseWithController = fetchDepartmentTimeSheetUseCase to controller,
            params = TimesheetRequest(period.getStart(), period.getEnd(), filter, scopeId),
            foldSuccess = {
                adapter.addData(it.result.userAttendances)
                adapter.notifyDataSetChanged()
            }
        )
    }

    override fun provideTimesheetAdapter(): BaseAdapter<*> = adapter
}