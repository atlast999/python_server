package vn.com.vti.smartta.model.pojo.timesheet

import android.os.Parcelable
import androidx.annotation.IntDef
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import vn.com.vti.common.util.AppResources
import vn.com.vti.common.util.extension.DateTimeXs
import vn.com.vti.common.util.extension.DateTimeXs.toTimeString
import vn.com.vti.smartta.R
import vn.com.vti.smartta.model.const.API_PATTERN_DATETIME_PARAM_GET
import vn.com.vti.smartta.model.data.IDatePeriod
import vn.com.vti.smartta.model.data.MonthPeriod
import vn.com.vti.smartta.model.data.SingleDay
import vn.com.vti.smartta.model.data.WeekPeriod
import vn.com.vti.smartta.model.pojo.IParamRequests
import vn.com.vti.smartta.ui.dialog.selection.adapter.ISingleChoice
import vn.com.vti.smartta.ui.dialog.selection.adapter.SingleChoice
import java.io.Serializable

data class TimesheetRequest(
    @SerializedName("startDate") @Expose val startDate: Long,
    @SerializedName("endDate") @Expose val endDate: Long,
    @SerializedName("typeFilter") @Expose val typeFilter: Int? = null,
    @SerializedName("id") @Expose val id: Int? = null
) : IParamRequests {
    override fun buildInto(bundle: MutableMap<String, String>) {
        bundle.apply {
            val formatter = DateTimeXs.getSimpleDateFormat(API_PATTERN_DATETIME_PARAM_GET)
            put("from", startDate.toTimeString(formatter))
            put("to", endDate.toTimeString(formatter))
        }
    }
}

@Parcelize
data class UserTimesheet(
    @SerializedName("userInfo") @Expose val userInfo: MemberInfor?,
    @SerializedName("workingStats") @Expose val workingStats: WorkingStats,
    @SerializedName("attendances") @Expose val attendances: List<Attendance>,
    @Volatile var isExpandTimeSheet: Boolean = false
) : Parcelable

@Parcelize
data class DepartmentTimesheet(
    @SerializedName("departmentId") @Expose val departmentId: Int,
    @SerializedName("departmentCode") @Expose val departmentCode: String,
    @SerializedName("departmentName") @Expose val departmentName: String,
    @SerializedName("userAttendances") @Expose val userAttendances: List<UserTimesheet>,
) : Serializable, Parcelable

@IntDef(
    TimesheetFilter.EARLY,
    TimesheetFilter.LATE,
    TimesheetFilter.ABSENT,
    TimesheetFilter.OVER_TIME
)
@Retention(AnnotationRetention.SOURCE)
annotation class TimesheetFilter {

    companion object {
        const val EARLY = 3
        const val LATE = 4
        const val OVER_TIME = 5
        const val ABSENT = 6

        fun buildSingleChoiceOptions(): List<ISingleChoice> = listOf(
            SingleChoice(EARLY, AppResources.getString(R.string.go_early)),
            SingleChoice(LATE, AppResources.getString(R.string.late)),
            SingleChoice(OVER_TIME, AppResources.getString(R.string.ot_hours)),
            SingleChoice(ABSENT, AppResources.getString(R.string.absent)),
        )
    }
}

@IntDef(TimesheetSort.NONE, TimesheetSort.DAY, TimesheetSort.WEEK, TimesheetSort.MONTH)
@Retention(AnnotationRetention.SOURCE)
annotation class TimesheetSort {

    companion object {
        const val NONE = 3
        const val DAY = 0
        const val WEEK = 1
        const val MONTH = 2

        fun buildSingleChoiceOptions(): List<ISingleChoice> = listOf(
            SingleChoice(DAY, AppResources.getString(R.string.day)),
            SingleChoice(WEEK, AppResources.getString(R.string.week)),
            SingleChoice(MONTH, AppResources.getString(R.string.month)),

            )

        fun createTimePeriod(@TimesheetSort sort: Int): IDatePeriod {
            return when (sort) {
                DAY -> SingleDay()
                WEEK -> WeekPeriod(System.currentTimeMillis())
                MONTH -> MonthPeriod(System.currentTimeMillis())
                else -> SingleDay()
            }
        }
    }

}
