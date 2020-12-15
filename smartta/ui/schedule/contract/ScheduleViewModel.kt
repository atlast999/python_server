package vn.com.vti.smartta.ui.schedule.contract

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.RecyclerView
import vn.com.vti.common.adapter.BaseAdapter
import vn.com.vti.common.ui.list.IListController
import vn.com.vti.common.ui.list.impl.ListController
import vn.com.vti.common.util.extension.*
import vn.com.vti.common.util.extension.DateTimeXs.previousStartDayOfWeek
import vn.com.vti.common.util.extension.DateTimeXs.toFirstMillis
import vn.com.vti.smartta.base.viewmodel.SmartTaViewModel
import vn.com.vti.smartta.interactor.schedule.FetchScheduleByWeekUseCase
import vn.com.vti.smartta.interactor.schedule.FetchScheduleEventUseCase
import vn.com.vti.smartta.model.const.QueryScope
import vn.com.vti.smartta.model.pojo.schedule.IEventSchedule
import vn.com.vti.smartta.model.pojo.schedule.IWeekSchedule
import vn.com.vti.smartta.model.pojo.schedule.request.ScheduleRequest
import vn.com.vti.smartta.ui.schedule.adapter.EventScheduleAdapter
import vn.com.vti.smartta.ui.schedule.adapter.WeekSchedulePagerAdapter
import java.util.*
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(application: Application) :
    SmartTaViewModel(application), ScheduleContract.ViewModel {

    @Inject
    lateinit var fetchScheduleByWeekUseCase: FetchScheduleByWeekUseCase

    @Inject
    lateinit var fetchScheduleEventUseCase: FetchScheduleEventUseCase

    private val weekPagerAdapter = WeekSchedulePagerAdapter()
    private val liveCurrentWeekIndex = MutableLiveData(0)
    private val liveSelectedWeek: LiveData<IWeekSchedule> =
        Transformations.map(liveCurrentWeekIndex) {
            if (it < 0 || weekPagerAdapter.itemCount <= it) null
            else weekPagerAdapter.getData()[it]
        }
    private val liveFocusEvent = MutableLiveData<IEventSchedule>()
    private val eventScheduleAdapter = EventScheduleAdapter(liveFocusEvent)
    private val liveFocusEventTime = MutableLiveData<Long>().apply {
        liveFocusEvent.observeForever { schedule ->
            if (schedule == null) {
                value = null
            } else {
                schedule.getStartTimeInMillis().let {
                    if (it / DateTimeXs.DAY != unbox() / DateTimeXs.DAY) {
                        value = it
                    }
                }
            }
        }
    }
    private val listController =
        ListController(eventScheduleAdapter, callback = object : ListController.Listener {

            override fun onContentLoadmore(totalItemsCount: Int, view: RecyclerView) {
            }

            override fun onContentRefresh() {
            }

            override fun onRequestTryAgain() {
            }

        })

    override fun getWeekPagerAdapter(): BaseAdapter<*> = weekPagerAdapter

    override fun getSelectedWeekIndex(): MutableLiveData<Int> = liveCurrentWeekIndex

    override fun getSelectedWeekSchedule(): LiveData<IWeekSchedule> = liveSelectedWeek

    override fun getCurrentFocusEventTime(): LiveData<Long> = liveFocusEventTime

    override fun getListController(): IListController = listController

    override fun onPrevWeek() {
        if (liveCurrentWeekIndex > 0) {
            liveCurrentWeekIndex -= 1
        }
    }

    override fun onNextWeek() {
        if (liveCurrentWeekIndex < weekPagerAdapter.itemCount - 1) {
            liveCurrentWeekIndex += 1
        }
    }

    override fun onSpecificDateSelected(selected: Long, reload: Boolean) {
        weekPagerAdapter.getData()
            .indexOfLast { selected >= it.getStartMillis() && selected <= it.getEndMillis() }.let {
                if (it >= 0) {
                    liveCurrentWeekIndex.value = it
                    if (reload) {
                        loadDetailsSchedule(weekPagerAdapter.getData()[it].getStartMillis())
                    }
                } else if (reload) {
                    loadMasterSchedule(selected)
                }
            }
    }

    override fun onReady() {
        super.onReady()
        loadMasterSchedule()
    }

    private fun loadMasterSchedule(selectedDateInMillis: Long? = null) {
        fetchScheduleByWeekUseCase.cancelIfRunning()
        val calendar = Calendar.getInstance()
        var selectedFirstDayOfWeek: Long? = null
        selectedDateInMillis?.let {
            calendar.timeInMillis = it
            selectedFirstDayOfWeek = calendar.previousStartDayOfWeek(Calendar.MONDAY)
            calendar.timeInMillis = it
        }
        val start = calendar.run {
            toFirstMillis(year = calendar[Calendar.YEAR])
            timeInMillis
        }
        val end = calendar.run {
            add(Calendar.YEAR, 1)
            timeInMillis
        }
        loadSummarySchedule(start, end, selectedDateInMillis)
        loadDetailsSchedule(selectedFirstDayOfWeek ?: start)
    }

    private fun loadSummarySchedule(start: Long, end: Long, selectedDateInMillis: Long?) {
        fetch(fetchScheduleByWeekUseCase,
            params = ScheduleRequest(QueryScope.ME, start, end),
            foldSuccess = {
                weekPagerAdapter.run {
                    setData(it)
                    selectedDateInMillis?.let { onSpecificDateSelected(it, false) }
                    notifyDataSetChanged()
                }
            })
    }

    private fun loadDetailsSchedule(firstDayInMillis: Long) {
        fetchScheduleEventUseCase.cancelIfRunning()
        fetch(useCaseWithController = fetchScheduleEventUseCase to listController,
            params = ScheduleRequest(
                QueryScope.ME,
                firstDayInMillis,
                firstDayInMillis + DateTimeXs.WEEK
            ),
            foldSuccess = {
                eventScheduleAdapter.apply {
                    setData(it.result)
                    notifyDataSetChanged()
                }
            })
    }
}

