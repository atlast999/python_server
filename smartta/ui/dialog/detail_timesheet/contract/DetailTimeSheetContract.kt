package vn.com.vti.smartta.ui.dialog.detail_timesheet.contract

import androidx.lifecycle.MutableLiveData
import vn.com.vti.common.ui.list.IListController
import vn.com.vti.common.viewmodel.AbsViewModel
import vn.com.vti.smartta.model.pojo.timesheet.MemberInfor
import vn.com.vti.smartta.ui.dialog.detail_timesheet.adapter.DetailTimeSheetAdapter

interface DetailTimeSheetContract {

    object Args {
        const val KEY_TIME = "KEY_TIME"
        const val KEY_TYPE = "KEY_TIME"
    }

    interface ViewModel : AbsViewModel {
        fun onDismissDialog()
        fun getListController(): IListController
        fun getAdaper(): DetailTimeSheetAdapter
        fun getMemberInfor():MutableLiveData<MemberInfor>
        fun getDateSelect(): MutableLiveData<String>
        fun setTypeUser(value:String)
    }
}