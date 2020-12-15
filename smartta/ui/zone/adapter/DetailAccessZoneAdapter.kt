package vn.com.vti.smartta.ui.zone.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import vn.com.vti.common.adapter.binding.BindingArrayAdapter
import vn.com.vti.common.adapter.binding.BindingHolder
import vn.com.vti.smartta.R
import vn.com.vti.smartta.databinding.ItemDetailAccessZoneBinding
import vn.com.vti.smartta.model.pojo.accesszone.AccessRoom

class DetailAccessZoneAdapter : BindingArrayAdapter<AccessRoom>() {

    override fun onCreateHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingHolder<out ViewDataBinding, AccessRoom> {
        return ViewHolder(parent)
    }

    inner class ViewHolder(parent: ViewGroup) :
        BindingHolder<ItemDetailAccessZoneBinding, AccessRoom>(
            parent,
            R.layout.item_detail_access_zone
        ) {
        override fun onBind(position: Int, model: AccessRoom?) {
            super.onBind(position, model)
            binder.run {
                location = model
                executePendingBindings()
            }
        }
    }
}
