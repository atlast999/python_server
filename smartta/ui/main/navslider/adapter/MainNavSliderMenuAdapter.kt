package vn.com.vti.smartta.ui.main.navslider.adapter

import android.view.ViewGroup
import vn.com.vti.common.adapter.binding.BindingHolder
import vn.com.vti.common.adapter.tree.ExpandableMenuAdapter
import vn.com.vti.common.adapter.tree.Node
import vn.com.vti.common.util.extension.padding
import vn.com.vti.smartta.R
import vn.com.vti.smartta.databinding.ItemMainSliderMenuBinding
import vn.com.vti.smartta.model.data.menu.MainMenuItem

class MainNavSliderMenuAdapter : ExpandableMenuAdapter<MainMenuItem>() {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int) = Holder(parent)

    override fun isSupportedLoadmoreHolder() = false

    inner class Holder(parent: ViewGroup) :
        BindingHolder<ItemMainSliderMenuBinding, Node<MainMenuItem>>(
            parent,
            R.layout.item_main_slider_menu
        ) {

        init {
            registerChildViewItemClickEvent(binder.imgIcon) { position, _, node ->
                node?.let {
                    if (it.getType() == Node.GROUP) {
                        toggle(position)
                    }
                }
            }
            registerRootViewItemClickEvent(mItemClickListener)
        }

        override fun onBind(position: Int, model: Node<MainMenuItem>?) {
            super.onBind(position, model)
            binder.apply {
                root.padding(left = model?.let { it.getLevel() * 50 } ?: 0)
                menu = model
                executePendingBindings()
            }
        }
    }
}