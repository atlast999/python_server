package vn.com.vti.smartta.model.pojo.schedule

import vn.com.vti.common.util.extension.DateTimeXs

interface IWeekSchedule {

    fun getStartMillis(): Long

    fun getEndMillis(): Long = getStartMillis() + DateTimeXs.WEEK

    /**
     * dayOfWeek should be one of [java.util.Calendar.DAY_OF_WEEK]
     */
    fun hasEvent(dayOfWeek: Int): Boolean
}
