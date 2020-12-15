package vn.com.vti.smartta.model.pojo.accesszone

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.StringDef
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class AccessZone(
    @SerializedName("buildingName") @Expose val buildingName: String,
    @SerializedName("rooms") @Expose val accessRoomList: List<AccessRoom>,
    var isExpanded: Boolean = false
) : Serializable

data class AccessRoom(
    @SerializedName("roomId") @Expose val roomId: String,
    @SerializedName("roomName") @Expose val roomName: String,
    @SerializedName("buildingName") @Expose val buildingName: String,
    @SerializedName("floorNo") @Expose val floorNo: String,
    val type: String,
    val level: AccessLevel
) : Serializable

@StringDef(AccessPermission.ALLOW, AccessPermission.DENY)
@Retention(AnnotationRetention.SOURCE)
annotation class AccessPermission {
    companion object {
        const val ALLOW = "ALLOW"
        const val DENY = "DENY"
    }
}

sealed class AccessLevel(@ColorInt val color: Int)

object PublicArea : AccessLevel(Color.parseColor("#894596"))
object AreaLevel2 : AccessLevel(Color.parseColor("#faa53d"))
object AreaLevel3 : AccessLevel(Color.parseColor("#f25257"))
object AreaLevel4 : AccessLevel(Color.parseColor("#1fbb8c"))

data class AccessZoneRequest(
    @SerializedName("accountId") @Expose val accountId: String,
)
