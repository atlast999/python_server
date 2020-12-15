package vn.com.vti.smartta.model.pojo.schedule.response

import androidx.annotation.IntRange
import vn.com.vti.smartta.model.pojo.schedule.IWeekSchedule

data class WeekSchedule(
    @IntRange(from = 0) val startWeekMillis: Long,
    val eventMap: Array<Boolean>
) :
    IWeekSchedule {

    override fun getStartMillis(): Long = startWeekMillis

    override fun hasEvent(dayOfWeek: Int): Boolean = eventMap[dayOfWeek - 1]

}