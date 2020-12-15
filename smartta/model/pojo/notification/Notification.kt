package vn.com.vti.smartta.model.pojo.notification

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Notification(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("title") @Expose val title: String,
    @SerializedName("description") @Expose val description: String,
    @SerializedName("type") @Expose val type: String
) : Serializable

enum class NotificationType(var value: String) {
    REQUEST("REQUEST"), NORMAL("NORMAL")
}

data class NotificationResponse(
    @SerializedName("notifications") @Expose val notifications: List<Notification>,
)