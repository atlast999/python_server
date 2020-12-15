package vn.com.vti.smartta.model.pojo.authentication

import android.os.Build
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import vn.com.vti.smartta.model.const.AuthEntity

data class LoginRequest(
    val method: String = AuthEntity.EMAIL,
    @SerializedName("account") @Expose val account: String,
    @SerializedName("password") @Expose val password: String,
    @SerializedName("rememberMe") @Expose val rememberMe: Boolean = true,
    @SerializedName("deviceName") @Expose val deviceName: String = Build.BRAND,
    @SerializedName("deviceInfo") @Expose val deviceInfo: String = Build.PRODUCT,
    @SerializedName("deviceType") @Expose val deviceType: String = "MOBILE",
    @SerializedName("deviceToken") @Expose val deviceToken: String? = null,
    @SerializedName("locationInfo") @Expose val locationInfo: String = "UNKNOWN"
)

data class LoginResponse(
    @SerializedName("account") @Expose val account: String,
    @SerializedName("token") @Expose val token: String,
    @SerializedName("tokenExpiredAt") @Expose val tokenExpiredAt: String,
    @SerializedName("refreshToken") @Expose val refreshToken: String,
    @SerializedName("deviceToken") @Expose val deviceToken: String,
)