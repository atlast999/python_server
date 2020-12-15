package vn.com.vti.smartta.model.pojo.timesheet

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkingStats(
    @SerializedName("checkinCount") @Expose val checkinCount: Int,
    @SerializedName("checkOutCount") @Expose val checkOutCount: Int,
    @SerializedName("totalWorkingHours") @Expose val totalWorkingHours: Float,
    @SerializedName("totalOTHours") @Expose val totalOTHours: Float,
    @SerializedName("lateCheckInCount") @Expose val lateCheckInCount: Int,
    @SerializedName("earlyCheckOutCount") @Expose val earlyCheckOutCount: Int,
    @SerializedName("absentCount") @Expose val absentCount: Int,
) : Parcelable