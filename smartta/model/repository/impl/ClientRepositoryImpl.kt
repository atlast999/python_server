package vn.com.vti.smartta.model.repository.impl

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import vn.com.vti.smartta.model.const.QueryScope
import vn.com.vti.smartta.model.pojo.BaseResponse
import vn.com.vti.smartta.model.pojo.authentication.LoginRequest
import vn.com.vti.smartta.model.pojo.authentication.LoginResponse
import vn.com.vti.smartta.model.pojo.build
import vn.com.vti.smartta.model.pojo.schedule.request.ScheduleRequest
import vn.com.vti.smartta.model.pojo.schedule.response.EventSchedule
import vn.com.vti.smartta.model.pojo.timesheet.*
import vn.com.vti.smartta.model.pojo.user.User
import vn.com.vti.smartta.model.repository.ClientRepository
import vn.com.vti.smartta.module.restful.service.PrimaryApiService
import vn.com.vti.smartta.module.restful.service.PrimaryApiServiceUtil.withFailureMapper
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(private val primaryApiService: PrimaryApiService) :
    ClientRepository {

    override fun login(loginRequest: LoginRequest): Single<BaseResponse<LoginResponse>> =
        primaryApiService.login(loginRequest.method, loginRequest).withFailureMapper()

    override fun logout(): Completable = primaryApiService.logout().withFailureMapper()

    override fun fetchMyProfile(): Single<BaseResponse<User>> =
        primaryApiService.fetchMyProfile().withFailureMapper()

    override fun fetchDepartmentTimesheet(timesheetRequest: TimesheetRequest): Single<BaseResponse<DepartmentTimesheet>> {
        return timesheetRequest.build().let {
            val scope =
                if (timesheetRequest.id == null) QueryScope.MY_DEPARTMENT else QueryScope.DEPARTMENT
            primaryApiService.fetchDepartmentTimesheet(scope, it).withFailureMapper()
        }
    }

    override fun fetchUserTimesheet(timesheetRequest: TimesheetRequest): Single<BaseResponse<UserTimesheet>> {
        return timesheetRequest.build().let {
            val scope =
                if (timesheetRequest.id == null) QueryScope.ME else QueryScope.USER
            primaryApiService.fetchUserTimesheet(scope, it).withFailureMapper()
        }
    }

    override fun fetchEventSchedule(request: ScheduleRequest): Single<BaseResponse<List<EventSchedule>>> =
        primaryApiService.fetchEventSchedule(request.build()).withFailureMapper()

    override fun fetchAttendaceLogs(
        detailTimeSheetRequest: DetailTimeSheetRequest
    ): Single<BaseResponse<UserAttendaceLogs>> = detailTimeSheetRequest.run {
        primaryApiService.fetchAttendaceLogs(type, id, date).withFailureMapper()
    }
}