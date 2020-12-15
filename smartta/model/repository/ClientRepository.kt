package vn.com.vti.smartta.model.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import vn.com.vti.smartta.model.pojo.BaseResponse
import vn.com.vti.smartta.model.pojo.authentication.LoginRequest
import vn.com.vti.smartta.model.pojo.authentication.LoginResponse
import vn.com.vti.smartta.model.pojo.schedule.request.ScheduleRequest
import vn.com.vti.smartta.model.pojo.schedule.response.EventSchedule
import vn.com.vti.smartta.model.pojo.timesheet.*
import vn.com.vti.smartta.model.pojo.user.User

interface ClientRepository {

    fun login(loginRequest: LoginRequest): Single<BaseResponse<LoginResponse>>

    fun logout(): Completable

    fun fetchMyProfile(): Single<BaseResponse<User>>

    @GET("v1/att/timesheet")
    fun fetchDepartmentTimesheet(
        timesheetRequest: TimesheetRequest
    ): Single<BaseResponse<DepartmentTimesheet>>

    @GET("v1/att/timesheet")
    fun fetchUserTimesheet(
        timesheetRequest: TimesheetRequest
    ): Single<BaseResponse<UserTimesheet>>

    fun fetchEventSchedule(request: ScheduleRequest): Single<BaseResponse<List<EventSchedule>>>

    fun fetchAttendaceLogs(
        detailTimeSheetRequest: DetailTimeSheetRequest
    ): Single<BaseResponse<UserAttendaceLogs>>


}