package vn.com.vti.smartta.model.pojo.schedule.response

import android.graphics.Color
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import vn.com.vti.smartta.model.const.apiDateTimeToMillis
import vn.com.vti.smartta.model.pojo.schedule.IEventSchedule

data class EventSchedule(
    @SerializedName("id") @Expose val _id: Int,
    @SerializedName("title") @Expose val _title: String,
    @SerializedName("start") @Expose val start: String,
    @SerializedName("end") @Expose val end: String,
    @SerializedName("location") @Expose val location: String,
    @SerializedName("url") @Expose val url: String,
    @SerializedName("allday") @Expose val allday: Boolean,
    @SerializedName("color") @Expose val bgColor: String? = null,
    @SerializedName("textColor") @Expose val textColor: String? = null,

    ) : IEventSchedule {

    private var startMillis: Long? = null

    private var endMillis: Long? = null

    private var bgColorInt: Int? = null

    private var textColorInt: Int? = null

    @Volatile
    var flagFirstEventOfDay = false

    override fun getColor(): Int = bgColorInt ?: Color.parseColor(bgColor ?: "#483D8B").also {
        bgColorInt = it
    }

    override fun getTextColor() = textColorInt ?: Color.parseColor(textColor ?: "#FFFFFF").also {
        textColorInt = it
    }

    override fun getStartTimeInMillis(): Long = startMillis ?: apiDateTimeToMillis(start).also {
        startMillis = it
    }

    override fun getEndTimeInMillis(): Long = endMillis ?: apiDateTimeToMillis(end).also {
        endMillis = it
    }

    override fun getTitle(): String = _title

    override fun getSubTitle(): String = location

    override fun isFirstEventOfDay(): Boolean = flagFirstEventOfDay

}