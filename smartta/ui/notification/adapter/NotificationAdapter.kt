package vn.com.vti.smartta.ui.notification.adapter

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import vn.com.vti.common.adapter.flex.Binder
import vn.com.vti.common.adapter.flex.LoadmoreFlexibleArrayAdapter
import vn.com.vti.common.extension.removeAllItemDecorations
import vn.com.vti.common.util.SwipeHelper
import vn.com.vti.common.util.toast
import vn.com.vti.smartta.R
import vn.com.vti.smartta.databinding.ItemNotificationBinding
import vn.com.vti.smartta.model.pojo.notification.Notification
import vn.com.vti.smartta.model.pojo.notification.NotificationType

class NotificationAdapter : LoadmoreFlexibleArrayAdapter<Notification>() {

    private val swipeHelper: SwipeHelper = object : SwipeHelper() {
        override fun instantiateUnderlayButton(
            context: Context,
            position: Int
        ): List<UnderlayButton> {
            val acceptButton = acceptButton(context, position)
            val declineButton = declineButton(context, position)

            var buttons = listOf<UnderlayButton>()
            if (getItem(position).type == NotificationType.REQUEST.value) {
                buttons = listOf(acceptButton, declineButton)
            }
            return buttons
        }
    }

    private val itemTouchHelper = ItemTouchHelper(swipeHelper)

    fun getItemTouchHelper() = itemTouchHelper

    private fun acceptButton(context: Context, position: Int): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            context,
            "Accept",
            14.0f,
            android.R.color.holo_green_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    context.toast("Accepted item $position")
                }
            })
    }

    private fun declineButton(context: Context, position: Int): SwipeHelper.UnderlayButton {
        return SwipeHelper.UnderlayButton(
            context,
            "Decline",
            14.0f,
            android.R.color.holo_red_light,
            object : SwipeHelper.UnderlayButtonClickListener {
                override fun onClick() {
                    context.toast("Decline item $position")
                }
            })
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        swipeHelper.attachToRecyclerView(recyclerView)
        recyclerView.removeAllItemDecorations()
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        swipeHelper.attachToRecyclerView(null)
        itemTouchHelper.attachToRecyclerView(null)
    }

    private val binder = Binder<ItemNotificationBinding, Notification> { _, view, model ->
        view.run {
            model?.let {
                imgIcon.setImageResource(R.mipmap.ic_launcher_round)
                tvTitle.text = it.title
                tvDescription.text = it.description
            } ?: run {
                imgIcon.setImageDrawable(null)
                tvTitle.text = null
                tvDescription.text = null
            }
            executePendingBindings()
        }
    }

    override fun onCreateBinder(viewType: Int) = R.layout.item_notification to binder

}