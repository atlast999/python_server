package vn.com.vti.smartta.ui.zone.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import vn.com.vti.common.adapter.binding.BindingArrayAdapter
import vn.com.vti.common.adapter.binding.BindingHolder
import vn.com.vti.smartta.R
import vn.com.vti.smartta.databinding.ItemAccessZoneBinding
import vn.com.vti.smartta.model.pojo.accesszone.AccessZone

class AccessibleZoneAdapter : BindingArrayAdapter<AccessZone>() {

    override fun onCreateHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingHolder<out ViewDataBinding, AccessZone> {
        return ViewHolder(parent)
    }

    inner class ViewHolder(parent: ViewGroup) :
        BindingHolder<ItemAccessZoneBinding, AccessZone>(parent, R.layout.item_access_zone) {

        private val detailAccessZoneAdapter = DetailAccessZoneAdapter()

        init {
            binder.adapter = detailAccessZoneAdapter
            registerChildViewClickEvent(binder.viewHeader) { position, _, t ->
                t?.apply {
                    isExpanded = !isExpanded
                }
                notifyItemChanged(position)
            }
        }

        override fun onBind(position: Int, model: AccessZone?) {
            super.onBind(position, model)
            binder.run {
                model?.let {
                    detailAccessZoneAdapter.setData(if (it.isExpanded) it.accessRoomList else null)
                } ?: run {
                    detailAccessZoneAdapter.setData(null)
                }
                accessZone = model
                executePendingBindings()
            }
        }
    }
}