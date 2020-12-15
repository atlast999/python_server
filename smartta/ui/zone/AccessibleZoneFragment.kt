package vn.com.vti.smartta.ui.zone

import android.view.*
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import vn.com.vti.common.util.extension.inflateBinding
import vn.com.vti.smartta.R
import vn.com.vti.smartta.base.scene.SmartTaFragment
import vn.com.vti.smartta.ui.zone.contract.AccessibleZoneContract
import vn.com.vti.smartta.ui.zone.contract.AccessibleZoneViewModel

class AccessibleZoneFragment : SmartTaFragment<AccessibleZoneContract.ViewModel>() {
    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding = inflater.inflateBinding(R.layout.fragment_accesible_zone, container)

    override fun getViewModelClass(): Class<out ViewModel> = AccessibleZoneViewModel::class.java

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_schedule, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_calendar) {

        }
        return super.onOptionsItemSelected(item)
    }

}