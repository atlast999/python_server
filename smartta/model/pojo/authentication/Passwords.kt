package vn.com.vti.smartta.model.pojo.authentication

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ForgotPasswordRequest(
    @SerializedName("email") @Expose val email: String
) : Serializable

data class ForgotPasswordResponse(
    @SerializedName("resetPasswordTransactionId") @Expose val resetPasswordTransactionId: String
)