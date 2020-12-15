package vn.com.vti.smartta.model.pojo.timesheet

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import vn.com.vti.common.util.extension.DateTimeXs.convertTimeString
import vn.com.vti.smartta.model.const.API_PATTERN_DATETIME
import java.io.Serializable

interface IAttendance {

    fun getAttDate(pattern: String): String

    fun getCheckIn(pattern: String): String

    fun getCheckOut(pattern: String): String

    fun getWorkingHours(): Float
}

data class Attendance(
    @SerializedName("id") @Expose val id: String,
    @SerializedName("date") @Expose val date: String,
    @SerializedName("checkInTime") @Expose val checkinTime: String?,
    @SerializedName("checkOutTime") @Expose val checkoutTime: String?,
    @SerializedName("late") @Expose val isLate: Boolean,
    @SerializedName("earlyLeave") @Expose val isEarlyLeave: Boolean,
    @SerializedName("ot") @Expose val isOT: Boolean,
    @SerializedName("absent") @Expose val isAbsent: Boolean,
    @SerializedName("totalHours") @Expose val totalHours: Float
) : Serializable, IAttendance {

    private var formattedAttDate: String? = null

    private var formattedCheckIn: String? = null

    private var formattedCheckOut: String? = null

    override fun getAttDate(pattern: String): String {
        return formattedAttDate ?: (date.convertTimeString(API_PATTERN_DATETIME, pattern) ?: date)
            .also { formattedAttDate = it }
    }

    override fun getCheckIn(pattern: String): String {
        return formattedCheckIn ?: (checkinTime?.convertTimeString(API_PATTERN_DATETIME, pattern)
            ?: "")
            .also { formattedCheckIn = it }
    }

    override fun getCheckOut(pattern: String): String {
        return formattedCheckOut ?: (checkoutTime?.convertTimeString(API_PATTERN_DATETIME, pattern)
            ?: "")
            .also { formattedCheckOut = it }
    }

    override fun getWorkingHours(): Float = totalHours

}