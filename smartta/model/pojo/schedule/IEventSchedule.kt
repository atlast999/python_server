package vn.com.vti.smartta.model.pojo.schedule

import androidx.annotation.ColorInt

interface IEventSchedule {

    @ColorInt
    fun getColor(): Int?

    @ColorInt
    fun getTextColor(): Int?

    fun getStartTimeInMillis(): Long

    fun getEndTimeInMillis(): Long

    fun getTitle(): String

    fun getSubTitle(): String

    fun isFirstEventOfDay(): Boolean
}