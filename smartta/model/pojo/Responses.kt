package vn.com.vti.smartta.model.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BaseResponse<T>(
    @SerializedName("status") @Expose val status: Int,
    @SerializedName("message") @Expose val message: String,
    @SerializedName("result") @Expose val result: T,
)

class FailureResponse(
    @SerializedName("code") @Expose val code: Int,
    @SerializedName("message") @Expose val message: String
)

class FailureResponseExecption(
    message: String,
    cause: Throwable,
    val failureResponse: FailureResponse? = null
) : Exception(message, cause)