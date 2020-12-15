package vn.com.vti.smartta.ui.dialog.detail_timesheet.contract

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import vn.com.vti.common.ui.list.IListController
import vn.com.vti.common.ui.list.impl.ListController
import vn.com.vti.common.util.extension.DateTimeXs.convertTimeString
import vn.com.vti.common.viewmodel.Finish
import vn.com.vti.smartta.base.viewmodel.SmartTaViewModel
import vn.com.vti.smartta.interactor.timesheet.GetDetailTimeSheetUseCase
import vn.com.vti.smartta.model.const.API_PATTERN_DATETIME
import vn.com.vti.smartta.model.const.API_PATTERN_DATETIME_PARAM_GET
import vn.com.vti.smartta.model.const.QueryScope
import vn.com.vti.smartta.model.pojo.timesheet.DetailTimeSheetRequest
import vn.com.vti.smartta.model.pojo.timesheet.MemberInfor
import vn.com.vti.smartta.ui.dialog.detail_timesheet.DetailTimesheetDialogArgs
import vn.com.vti.smartta.ui.dialog.detail_timesheet.adapter.DetailTimeSheetAdapter
import javax.inject.Inject

class DetailTimeSheetViewModel @Inject constructor(application: Application) :
    SmartTaViewModel(application), DetailTimeSheetContract.ViewModel {

    private val detailTimeSheetAdapter = DetailTimeSheetAdapter()
    private val controller = ListController(adapter = detailTimeSheetAdapter)
    private var dateSelect = MutableLiveData<String>()
    private var typeUser = MutableLiveData<String>()
    private var memberInfor = MutableLiveData<MemberInfor>()

    @Inject
    lateinit var getDetailTimeSheetUseCase: GetDetailTimeSheetUseCase

    override fun onDismissDialog() {
        redirect(Finish)
    }

    override fun getListController(): IListController = controller
    override fun getAdaper(): DetailTimeSheetAdapter = detailTimeSheetAdapter
    override fun getMemberInfor(): MutableLiveData<MemberInfor> = memberInfor

    override fun getDateSelect(): MutableLiveData<String> = dateSelect

    override fun onBind(args: Bundle?) {
        super.onBind(args)
        args?.let {
            DetailTimesheetDialogArgs.fromBundle(it)
        }.let {
            dateSelect.value =
                it?.keytime?.convertTimeString(API_PATTERN_DATETIME, API_PATTERN_DATETIME_PARAM_GET)
                    ?: ""
            typeUser.value = it?.keytype ?: ""
        }
    }

    override fun onReady() {
        super.onReady()
        loadMasterDetailTimeSheet()
    }

    private fun loadMasterDetailTimeSheet() {
        var id = 0
        if (typeUser.value!! == QueryScope.USER) {
            id = 1
        }
        fetch(getDetailTimeSheetUseCase,
            params = DetailTimeSheetRequest(dateSelect.value!!, id, typeUser.value!!),
            foldSuccess = {
                detailTimeSheetAdapter.addData(it.result.attendanceLogs)
                memberInfor.value = MemberInfor(
                    it.result.userId,
                    it.result.fullName,
                    it.result.department,
                    it.result.account
                )
            })
    }
    override fun setTypeUser(value: String) {
        typeUser.value = value
        loadMasterDetailTimeSheet()
    }
}