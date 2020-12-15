package vn.com.vti.smartta.model.data

import vn.com.vti.common.util.extension.DateTimeXs
import vn.com.vti.common.util.extension.DateTimeXs.previousStartDayOfWeek
import vn.com.vti.common.util.extension.DateTimeXs.toFirstMillisOfDay
import java.text.SimpleDateFormat
import java.util.*

interface IPeriodShiftable {

    fun prevShift(): IDatePeriod

    fun nextShift(): IDatePeriod
}

interface IDatePeriod {

    fun getStart(): Long

    fun getEnd(): Long

    fun format(pattern: String, delimiter: String = " "): String {
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        val builder = StringBuilder()
        val start = getStart()
        if (start > 0L) {
            builder.append(simpleDateFormat.format(start))
        }
        val end = getEnd()
        if (end > 0L) {
            builder.append(delimiter).append(simpleDateFormat.format(end))
        }
        return builder.toString()
    }
}

open class FreeDatePeriod(private val start: Long, private val end: Long) : IDatePeriod {

    override fun getStart() = start

    override fun getEnd() = end
}

class SingleDay(millis: Long = System.currentTimeMillis()) : IDatePeriod,
    IPeriodShiftable {

    private val startMillis: Long = Calendar.getInstance().apply {
        timeInMillis = millis
        toFirstMillisOfDay()
    }.timeInMillis

    override fun format(pattern: String, delimiter: String): String {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(startMillis)
    }

    override fun prevShift() = SingleDay(startMillis - DateTimeXs.DAY)

    override fun nextShift() = SingleDay(startMillis + DateTimeXs.DAY)

    override fun getStart() = startMillis

    override fun getEnd() = startMillis + DateTimeXs.DAY - 1
}

class WeekPeriod(millis: Long) : IDatePeriod, IPeriodShiftable {

    private val startMillis: Long = Calendar.getInstance().apply {
        timeInMillis = millis
        timeInMillis = previousStartDayOfWeek(Calendar.MONDAY)
    }.timeInMillis

    override fun prevShift() = WeekPeriod(startMillis - DateTimeXs.WEEK)

    override fun nextShift() = WeekPeriod(startMillis + DateTimeXs.WEEK)

    override fun getStart() = startMillis

    override fun getEnd() = startMillis + DateTimeXs.WEEK - 1
}

class MonthPeriod(millis: Long) : IDatePeriod, IPeriodShiftable {

    private val startMillis: Long
    private val endMillis: Long

    init {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = millis
            set(Calendar.DAY_OF_MONTH, 1)
            toFirstMillisOfDay()
        }
        startMillis = calendar.timeInMillis
        calendar.add(Calendar.MONTH, 1)
        endMillis = calendar.timeInMillis - 1
    }

    override fun prevShift(): IDatePeriod {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = startMillis
            set(Calendar.DAY_OF_MONTH, 1)
            toFirstMillisOfDay()
            add(Calendar.MONTH, -1)
        }
        return MonthPeriod(calendar.timeInMillis)
    }

    override fun nextShift(): IDatePeriod {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = startMillis
            set(Calendar.DAY_OF_MONTH, 1)
            toFirstMillisOfDay()
            add(Calendar.MONTH, 1)
        }
        return MonthPeriod(calendar.timeInMillis)
    }

    override fun getStart() = startMillis

    override fun getEnd() = endMillis

}
