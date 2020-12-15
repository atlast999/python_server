package vn.com.vti.smartta.ui.schedule.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import vn.com.vti.common.adapter.binding.BindingHolder
import vn.com.vti.common.adapter.binding.LoadmoreArrayAdapter
import vn.com.vti.common.util.loadmore.HeadItemListener
import vn.com.vti.smartta.R
import vn.com.vti.smartta.databinding.ItemScheduleEventBinding
import vn.com.vti.smartta.model.pojo.schedule.IEventSchedule

class EventScheduleAdapter(private val liveFocusEventSchedule: MutableLiveData<IEventSchedule>) :
    LoadmoreArrayAdapter<IEventSchedule>() {

    private var firstVisibleItemFinder: HeadItemListener? = null

    override fun onCreateContentViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingHolder<out ViewDataBinding, IEventSchedule> = Holder(parent)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        firstVisibleItemFinder?.let {
            recyclerView.removeOnScrollListener(it)
        }
        recyclerView.layoutManager?.let {
            recyclerView.addOnScrollListener(object : HeadItemListener(it) {
                override fun onHeadItemIndexChanged(newIndex: Int) {
                    liveFocusEventSchedule.value = getItem(newIndex)
                }
            }.also {
                firstVisibleItemFinder = it
            })
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        firstVisibleItemFinder?.let {
            recyclerView.removeOnScrollListener(it)
        }
    }

    inner class Holder(parent: ViewGroup) : BindingHolder<ItemScheduleEventBinding, IEventSchedule>(
        parent,
        R.layout.item_schedule_event
    ) {

        override fun onBind(position: Int, model: IEventSchedule?) {
            super.onBind(position, model)
            binder.apply {
                event = model
                executePendingBindings()
            }
        }
    }

}