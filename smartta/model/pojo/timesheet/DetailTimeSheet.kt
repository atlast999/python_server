package vn.com.vti.smartta.model.pojo.timesheet

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import vn.com.vti.common.util.extension.DateTimeXs.convertTimeString
import vn.com.vti.smartta.model.const.API_TEMP_PATTERN_DATETIME
import java.io.Serializable

interface IAttendanceLogs {
    fun getAttDateTime(pattern: String): String
}

data class UserAttendaceLogs(
    @SerializedName("userId") @Expose val userId: Int,
    @SerializedName("fullName") @Expose val fullName: String,
    @SerializedName("account") @Expose val account: String,
    @SerializedName("department") @Expose val department: String,
    @SerializedName("attendanceLogs") @Expose val attendanceLogs: List<AttendanceLogs>,
) : Serializable

data class AttendanceLogs(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("verifyType") @Expose val verifyType: String,
    @SerializedName("device") @Expose val device: String,
    @SerializedName("time") @Expose val time: String?,
    @SerializedName("accessType") @Expose val accessType: String,
    @SerializedName("room") @Expose val room: String,
    @SerializedName("temperature") @Expose val temperature: Float,
    @SerializedName("result") @Expose val result: String,
    @SerializedName("secureRoom") @Expose val secureRoom: Boolean,
    @SerializedName("abnormal") @Expose val abnormal: Boolean
) : IAttendanceLogs {
    private var formattedAttDateTime: String? = null

    override fun getAttDateTime(pattern: String): String {
        return formattedAttDateTime ?: (time!!.convertTimeString(API_TEMP_PATTERN_DATETIME, pattern)
            ?: time)
            .also { formattedAttDateTime = it }
    }
}

data class DetailTimeSheetRequest(
    @SerializedName("date") @Expose val date: String,
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("type") @Expose val type: String,
)