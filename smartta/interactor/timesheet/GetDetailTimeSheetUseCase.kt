package vn.com.vti.smartta.interactor.timesheet

import io.reactivex.rxjava3.core.Single
import vn.com.vti.common.domain.usecase.rx.SingleUseCase
import vn.com.vti.smartta.model.pojo.BaseResponse
import vn.com.vti.smartta.model.pojo.timesheet.DetailTimeSheetRequest
import vn.com.vti.smartta.model.pojo.timesheet.UserAttendaceLogs
import vn.com.vti.smartta.model.repository.ClientRepository
import vn.com.vti.smartta.module.credential.CredentialManager
import javax.inject.Inject

class GetDetailTimeSheetUseCase @Inject constructor() :
    SingleUseCase<BaseResponse<UserAttendaceLogs>, DetailTimeSheetRequest>() {

    @Inject
    lateinit var repository: ClientRepository

    @Inject
    lateinit var credentialManager: CredentialManager

    override fun create(params: DetailTimeSheetRequest?): Single<out BaseResponse<UserAttendaceLogs>> {
        return  params?.let {
            repository.fetchAttendaceLogs(params)
        } ?: errorParamsSingle()

}
}