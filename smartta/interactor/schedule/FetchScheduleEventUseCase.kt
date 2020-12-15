package vn.com.vti.smartta.interactor.schedule

import io.reactivex.rxjava3.core.Single
import vn.com.vti.common.domain.usecase.rx.SingleUseCase
import vn.com.vti.common.util.extension.DateTimeXs
import vn.com.vti.smartta.model.pojo.BaseResponse
import vn.com.vti.smartta.model.pojo.schedule.request.ScheduleRequest
import vn.com.vti.smartta.model.pojo.schedule.response.EventSchedule
import vn.com.vti.smartta.model.repository.ClientRepository
import javax.inject.Inject

class FetchScheduleEventUseCase @Inject constructor() :
    SingleUseCase<BaseResponse<List<EventSchedule>>, ScheduleRequest>() {

    @Inject
    lateinit var repository: ClientRepository

    override fun create(params: ScheduleRequest?): Single<out BaseResponse<List<EventSchedule>>> =
        params?.let { request ->
            repository.fetchEventSchedule(request)
                .map { res ->
                    res.apply {
                        var startDay: Long? = null
                        result.forEach { event ->
                            (event.getStartTimeInMillis() / DateTimeXs.DAY).let {
                                if (it != startDay) {
                                    startDay = it
                                    event.flagFirstEventOfDay = true
                                }
                            }
                        }
                    }
                }
        } ?: errorParamsSingle()

}