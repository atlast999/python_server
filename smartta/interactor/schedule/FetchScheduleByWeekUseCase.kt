package vn.com.vti.smartta.interactor.schedule

import io.reactivex.rxjava3.core.Single
import vn.com.vti.common.domain.usecase.rx.SingleUseCase
import vn.com.vti.common.util.extension.DateTimeXs
import vn.com.vti.common.util.extension.DateTimeXs.previousStartDayOfWeek
import vn.com.vti.smartta.model.pojo.schedule.request.ScheduleRequest
import vn.com.vti.smartta.model.pojo.schedule.response.WeekSchedule
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class FetchScheduleByWeekUseCase @Inject constructor() :
    SingleUseCase<List<WeekSchedule>, ScheduleRequest>() {

    override fun create(params: ScheduleRequest?): Single<out List<WeekSchedule>> {
        return Single.defer {
            params?.let {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = it.fromMillis
                var startWeek = calendar.previousStartDayOfWeek(Calendar.MONDAY)
                calendar.timeInMillis = it.toMillis
                val endWeek = calendar.previousStartDayOfWeek(Calendar.MONDAY)
                val listOfWeek = ArrayList<WeekSchedule>()
                while (startWeek <= endWeek) {
                    listOfWeek.add(
                        WeekSchedule(
                            startWeekMillis = startWeek,
                            eventMap = arrayOf(true, true, true, true, true, true, true)
                        )
                    )
                    startWeek += DateTimeXs.WEEK
                }
                Single.just(listOfWeek)
            } ?: Single.error(IllegalArgumentException())
        }
    }
}