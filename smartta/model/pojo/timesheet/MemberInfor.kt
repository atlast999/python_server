package vn.com.vti.smartta.model.pojo.timesheet

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class MemberInfor(
    @SerializedName("userId") @Expose val userId: Int,
    @SerializedName("displayName") @Expose val displayName: String,
    @SerializedName("accountName") @Expose val accountName: String,
    @SerializedName("account") @Expose val account: String,
) : Serializable, Parcelable