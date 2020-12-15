package vn.com.vti.smartta.interactor.timesheet

import io.reactivex.rxjava3.core.Single
import vn.com.vti.common.domain.usecase.rx.SingleUseCase
import vn.com.vti.smartta.model.pojo.BaseResponse
import vn.com.vti.smartta.model.pojo.timesheet.TimesheetRequest
import vn.com.vti.smartta.model.pojo.timesheet.UserTimesheet
import vn.com.vti.smartta.model.repository.ClientRepository
import javax.inject.Inject

class FetchUserTimeSheetUseCase @Inject constructor() :
    SingleUseCase<BaseResponse<UserTimesheet>, TimesheetRequest>() {

    @Inject
    lateinit var repository: ClientRepository

    override fun create(params: TimesheetRequest?): Single<out BaseResponse<UserTimesheet>> =
        params?.let {
            repository.fetchUserTimesheet(it)
        } ?: errorParamsSingle()
}