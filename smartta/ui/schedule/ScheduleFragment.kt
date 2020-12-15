package vn.com.vti.smartta.ui.schedule

import android.os.Bundle
import android.view.*
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import vn.com.vti.common.util.extension.inflateBinding
import vn.com.vti.smartta.BR
import vn.com.vti.smartta.R
import vn.com.vti.smartta.base.scene.SmartTaFragment
import vn.com.vti.smartta.ui.schedule.contract.ScheduleContract
import vn.com.vti.smartta.ui.schedule.contract.ScheduleViewModel

class ScheduleFragment : SmartTaFragment<ScheduleContract.ViewModel>() {

    override fun getViewModelClass(): Class<out ViewModel> = ScheduleViewModel::class.java

    override fun getViewModelVariableId(): Int = BR.vm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding = inflater.inflateBinding(R.layout.fragment_schedule_full, container)

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_schedule, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_calendar) {
            val builder: MaterialDatePicker.Builder<Long> = MaterialDatePicker.Builder.datePicker()
            val constraintsBuilder = CalendarConstraints.Builder()
            try {
                builder.setCalendarConstraints(constraintsBuilder.build())
                builder.build().apply {
                    addOnPositiveButtonClickListener {
                        viewModel.onSpecificDateSelected(it, true)
                    }
                    show(
                        this@ScheduleFragment.childFragmentManager,
                        MaterialDatePicker::class.java.simpleName
                    )
                }
            } catch (e: IllegalArgumentException) {

            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
