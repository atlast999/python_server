package vn.com.vti.smartta.ui.zone.contract

import vn.com.vti.common.viewmodel.AbsViewModel
import vn.com.vti.smartta.ui.zone.adapter.AccessibleZoneAdapter

interface AccessibleZoneContract {

    interface ViewModel : AbsViewModel {

        fun getAccessZoneAdapter(): AccessibleZoneAdapter
    }
}