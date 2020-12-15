package vn.com.vti.smartta.ui.timesheet

import android.os.Bundle
import android.view.*
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import vn.com.vti.common.util.AppResources
import vn.com.vti.common.viewmodel.ActionDirection
import vn.com.vti.common.viewmodel.Direction
import vn.com.vti.smartta.R
import vn.com.vti.smartta.base.scene.SmartTaFragment
import vn.com.vti.smartta.model.const.QueryScope
import vn.com.vti.smartta.model.data.FreeDatePeriod
import vn.com.vti.smartta.model.pojo.timesheet.TimesheetFilter
import vn.com.vti.smartta.model.pojo.timesheet.TimesheetSort
import vn.com.vti.smartta.ui.dialog.selection.SingleChoiceRadioDialogFragment
import vn.com.vti.smartta.ui.dialog.selection.contract.SingleChoiceRadioContract
import vn.com.vti.smartta.ui.timesheet.contract.DepartmentTimesheetViewModel
import vn.com.vti.smartta.ui.timesheet.contract.TimesheetContract
import vn.com.vti.smartta.ui.timesheet.contract.UserTimesheetViewModel


class TimesheetFragment : SmartTaFragment<TimesheetContract.ViewModel>() {
    private  var typeSort =0
    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_timesheet, container, false)

    override fun getViewModelClass(): Class<out ViewModel> =
        when (arguments?.getString(TimesheetContract.Args.KEY_TIMESHEET_TYPE)) {
            QueryScope.DEPARTMENT, QueryScope.MY_DEPARTMENT -> DepartmentTimesheetViewModel::class.java
            else -> UserTimesheetViewModel::class.java
        }

    override fun onDispatchDirectionEvent(direction: Direction): Boolean {
        return (direction as? ActionDirection)?.let { actionDirection ->
            when (actionDirection.actionId) {
                TimesheetContract.Action.ACTION_SORT -> {
                    SingleChoiceRadioDialogFragment.newInstance(
                        choices = SingleChoiceRadioContract.ChoiceBundle(
                            title = AppResources.getString(R.string.sort_by),
                            data = TimesheetSort.buildSingleChoiceOptions(),
                            selected =  typeSort
                        ),
                        listener = {
                            typeSort = it.getId()
                            viewModel.onDatePeriodChanged(TimesheetSort.createTimePeriod(typeSort))
                        }
                    ).show(childFragmentManager, "SORT")
                }
            }
            true
        } ?: super.onDispatchDirectionEvent(direction)
    }

    fun setupRangePickerDialog() {
        val builder: MaterialDatePicker.Builder<Pair<Long, Long>> = MaterialDatePicker.Builder
            .dateRangePicker()
        val constraintsBuilder = CalendarConstraints.Builder()
        try {
            builder.setCalendarConstraints(constraintsBuilder.build())
            builder.build().apply {
                addOnPositiveButtonClickListener {
                    it?.let {
                        viewModel.onDatePeriodChanged(FreeDatePeriod(it.first!!, it.second!!))
                    }
                }
                show(
                    this@TimesheetFragment.childFragmentManager,
                    MaterialDatePicker::class.java.simpleName
                )
            }
        } catch (e: IllegalArgumentException) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_timesheet, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onResume() {
        super.onResume()
        arguments?.let {
            getBaseActivity()?.run {
                when (it.getString(TimesheetContract.Args.KEY_TIMESHEET_TYPE, QueryScope.ME)) {
                    QueryScope.ME, QueryScope.USER -> setTitle(R.string.scene_my_timesheet)
                    QueryScope.DEPARTMENT, QueryScope.MY_DEPARTMENT -> setTitle(R.string.scene_department_timesheet)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_calendar -> {
                setupRangePickerDialog()
                true
            }
            R.id.action_filter -> {
                SingleChoiceRadioDialogFragment.newInstance(
                    choices = SingleChoiceRadioContract.ChoiceBundle(
                        title = AppResources.getString(R.string.filter),
                        data = TimesheetFilter.buildSingleChoiceOptions(),
                        selected = 0
                    ),
                    listener = { viewModel.setTypeFilter(it.getId()) }
                ).show(childFragmentManager, "FILTER")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}