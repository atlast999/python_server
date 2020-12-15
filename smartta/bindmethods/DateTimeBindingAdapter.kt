package vn.com.vti.smartta.bindmethods

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import vn.com.vti.common.util.extension.DateTimeXs.toTimeString
import vn.com.vti.smartta.model.pojo.schedule.IEventSchedule
import java.text.SimpleDateFormat
import java.util.*

object DateTimeBindingAdapter {

    @BindingAdapter(
        value = ["textAsCalendarField", "timeInMillis", "textAsCalendarFieldOffset"],
        requireAll = false
    )
    @JvmStatic
    fun textAsCalendarFields(view: TextView, field: Int, millis: Long?, offset: Int) {
        view.text = millis?.let {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            (calendar[field] + offset).toString()
        }
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter(
        value = ["textAsWeekRangeStart", "textAsWeekRangeEnd"],
        requireAll = true
    )
    @JvmStatic
    fun textAsDateRange(view: TextView, start: Long?, end: Long?) {
        if (start == null || end == null) {
            view.text = null
        } else {
            val calStart = Calendar.getInstance().apply {
                timeInMillis = start
            }
            val calEnd = Calendar.getInstance().apply {
                timeInMillis = end
            }
            if (calStart[Calendar.YEAR] == calEnd[Calendar.YEAR]) {
                val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
                view.text =
                    "${dateFormat.format(calStart.time)} - ${dateFormat.format(calEnd.time)} ${calStart[Calendar.YEAR]}"
            } else {
                val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                view.text =
                    "${dateFormat.format(calStart.time)} - ${dateFormat.format(calEnd.time)}"
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["textAsDateTime", "dateTimePattern"], requireAll = true)
    fun textAsDateTimeString(view: TextView, millis: Long?, pattern: String?) {
        if (millis == null || millis == 0L || pattern.isNullOrEmpty()) {
            view.text = null
        } else {
            view.text = millis.toTimeString(pattern)
        }
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter("textAsEventTimeRange")
    @JvmStatic
    fun textAsEventTimeRange(view: TextView, event: IEventSchedule?) {
        if (event == null) {
            view.text = null
        } else {
            val calStart = Calendar.getInstance().apply {
                timeInMillis = event.getStartTimeInMillis()
            }
            val calEnd = Calendar.getInstance().apply {
                timeInMillis = event.getEndTimeInMillis()
            }
            view.text =
                if (calStart[Calendar.DATE] == calEnd[Calendar.DATE]) {
                    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                    "${dateFormat.format(calStart.time)} - ${dateFormat.format(calEnd.time)}"
                } else {
                    val startFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                    val endFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                    "${startFormat.format(calStart.time)} - ${endFormat.format(calEnd.time)}"
                }
        }
    }
}