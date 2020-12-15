package vn.com.vti.smartta.module.restful.service

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import okhttp3.Request
import retrofit2.HttpException
import retrofit2.http.*
import timber.log.Timber
import vn.com.vti.common.network.PreRequestInterceptor
import vn.com.vti.common.network.Retrofits
import vn.com.vti.smartta.model.const.QueryKey
import vn.com.vti.smartta.model.pojo.BaseResponse
import vn.com.vti.smartta.model.pojo.FailureResponse
import vn.com.vti.smartta.model.pojo.FailureResponseExecption
import vn.com.vti.smartta.model.pojo.authentication.LoginRequest
import vn.com.vti.smartta.model.pojo.authentication.LoginResponse
import vn.com.vti.smartta.model.pojo.schedule.response.EventSchedule
import vn.com.vti.smartta.model.pojo.timesheet.DepartmentTimesheet
import vn.com.vti.smartta.model.pojo.timesheet.UserAttendaceLogs
import vn.com.vti.smartta.model.pojo.timesheet.UserTimesheet
import vn.com.vti.smartta.model.pojo.user.User
import java.util.*

interface PrimaryApiService {

    //### AUTHENTICATION ###
    @POST("auth/login")
    @Headers(Retrofits.HEADER_NO_AUTH)
    fun login(
        @Query("by") method: String,
        @Body loginRequest: LoginRequest
    ): Single<BaseResponse<LoginResponse>>

    @POST("auth/logout")
    fun logout(): Completable

    //### USER MANAGEMENT
    //TODO confused `v1` path

    @GET("v1/hr/my-profile")
    fun fetchMyProfile(): Single<BaseResponse<User>>

    //### TIMESHEET
    @GET("v1/att/timesheet")
    fun fetchDepartmentTimesheet(
        @Query(QueryKey.TARGET) type: String,
        @QueryMap params: Map<String, String>
    ): Single<BaseResponse<DepartmentTimesheet>>

    @GET("v1/att/timesheet")
    fun fetchUserTimesheet(
        @Query(QueryKey.TARGET) type: String,
        @QueryMap params: Map<String, String>
    ): Single<BaseResponse<UserTimesheet>>

    //### SCHEDULE
    @GET("v1/sched/schedule")
    fun fetchEventSchedule(@QueryMap params: Map<String, String>): Single<BaseResponse<List<EventSchedule>>>

    //### ATTENDANCELOGS
    @GET("v1/att/attendance-logs")
    fun fetchAttendaceLogs(
        @Query(QueryKey.TARGET) type: String,
        @Query(QueryKey.ID) id: Int,
        @Query(QueryKey.DATE) date: String,
    ): Single<BaseResponse<UserAttendaceLogs>>
}


class PrimaryServiceHeaderPreInterceptor : PreRequestInterceptor {

    private val locale: Locale =
        ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]
    private val userAgent = System.getProperty("http.agent")

    override fun onPreRequestIntercept(origin: Request, builder: Request.Builder) {
        builder.apply {
            addHeader("Accept-Language", locale.language)
            addHeader("Accept", "application/json")
            addHeader("User-Agent", userAgent)
        }
    }
}

object PrimaryApiServiceUtil {
    fun <T, R : BaseResponse<T>> Single<T>.withFailureMapper(): Single<T> {
        return this.onErrorResumeNext {
            Single.error(it.asFailureResponseException() ?: it)
        }
    }

    fun <T, R : BaseResponse<T>> Observable<T>.withFailureMapper(): Observable<T> {
        return this.onErrorResumeNext {
            Observable.error(it.asFailureResponseException() ?: it)
        }
    }

    fun <T, R : BaseResponse<T>> Flowable<T>.withFailureMapper(): Flowable<T> {
        return this.onErrorResumeNext {
            Flowable.error(it.asFailureResponseException() ?: it)
        }
    }

    fun Completable.withFailureMapper(): Completable {
        return this.onErrorResumeNext {
            Completable.error(it.asFailureResponseException() ?: it)
        }
    }

    fun Throwable.asFailureResponseException(): FailureResponseExecption? =
        (this as? HttpException)?.let {
            it.response()?.errorBody()?.string()?.let {
                GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
                    .runCatching {
                        fromJson(it, FailureResponse::class.java)
                    }.fold({ response ->
                        FailureResponseExecption(
                            response.message,
                            this@asFailureResponseException,
                            response
                        )
                    }, { throwable ->
                        FailureResponseExecption(
                            throwable.message ?: "Unknown error",
                            this@asFailureResponseException
                        )
                    })
            } ?: run {
                Timber.e("Http exception empty body!!!")
                null
            }
        }
}

