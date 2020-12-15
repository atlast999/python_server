package vn.com.vti.smartta.ui.zone.contract

import android.app.Application
import vn.com.vti.smartta.base.viewmodel.SmartTaViewModel
import vn.com.vti.smartta.interactor.accesszone.AccessZoneUseCase
import vn.com.vti.smartta.model.pojo.accesszone.AccessZone
import vn.com.vti.smartta.model.pojo.accesszone.AccessZoneRequest
import vn.com.vti.smartta.ui.zone.adapter.AccessibleZoneAdapter
import vn.com.vti.smartta.ui.zone.adapter.DetailAccessZoneAdapter
import javax.inject.Inject

class AccessibleZoneViewModel @Inject constructor(application: Application) :
    SmartTaViewModel(application), AccessibleZoneContract.ViewModel {
    @Inject
    lateinit var accessUseCase: AccessZoneUseCase

    private val accessZoneAdapter: AccessibleZoneAdapter = AccessibleZoneAdapter()
    private val detailAccessZoneAdapter: DetailAccessZoneAdapter = DetailAccessZoneAdapter()

    override fun getAccessZoneAdapter(): AccessibleZoneAdapter = accessZoneAdapter

    override fun onReady() {
        super.onReady()
        loadMasterAccessZone()

    }

    private fun loadMasterAccessZone() {
        fetch(
            accessUseCase,
            object : BaseCallback<List<AccessZone>>() {
                override fun onNext(result: List<AccessZone>) {
                    accessZoneAdapter.setData(result)
                    detailAccessZoneAdapter.apply {
                        setData(result[0].accessRoomList)
                    }
                    detailAccessZoneAdapter.notifyDataSetChanged()
                    accessZoneAdapter.notifyDataSetChanged()
                }
            }, AccessZoneRequest("")
        )
    }

}