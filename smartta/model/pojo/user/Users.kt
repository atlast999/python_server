package vn.com.vti.smartta.model.pojo.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
        @SerializedName("userId") @Expose val userId: Int?,
        @SerializedName("avatar") @Expose val avatar: String?,
        @SerializedName("displayName") @Expose val displayName: String,
        @SerializedName("accountName") @Expose val accountName: String,
        @SerializedName("departmentName") @Expose val departmentName: String,
        @SerializedName("departmentCode") @Expose val departmentCode: String,
        @SerializedName("email") @Expose val email: String,
        @SerializedName("mobile") @Expose val mobile: String,
        @SerializedName("workingLocation") @Expose val workingLocation: String?,
        @SerializedName("workingStats") @Expose val workingStats: WorkingStats?,
) : Serializable

data class WorkingStats(
        @SerializedName("totalWorkingHours") @Expose val totalWorkingHours: Float?,
        @SerializedName("totalOTHours") @Expose val totalOTHours: Float?,
        @SerializedName("lateCheckInCount") @Expose val lateCheckInCount: Int,
        @SerializedName("earlyCheckOutCount") @Expose val earlyCheckOutCount: Int,
) : Serializable

open class UserInfoRequest

