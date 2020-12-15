package vn.com.vti.smartta.ui.photocapture.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import vn.com.vti.common.adapter.binding.BindingArrayAdapter
import vn.com.vti.common.adapter.binding.BindingHolder
import vn.com.vti.smartta.R
import vn.com.vti.smartta.databinding.ItemPhotoBinding

class PhotoAdapter : BindingArrayAdapter<String>() {

    companion object {
        const val KEY_PATH = "PATH"
    }

    var selectedIndex = -1
        private set

    override fun onCreateHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingHolder<out ViewDataBinding, String> {
        return ViewHolder(parent)
    }

    inner class ViewHolder(parent: ViewGroup) :
        BindingHolder<ItemPhotoBinding, String>(parent, R.layout.item_photo) {
        override fun onBind(position: Int, model: String?) {
            super.onBind(position, model)
            binder.run {
                path = model
                selected = selectedIndex == position
                executePendingBindings()
            }
        }

        init {
            registerRootViewItemClickEvent { position, _, t ->
                t?.let {
                    if (it != KEY_PATH) {
                        if (selectedIndex != position) {
                            val temp = selectedIndex
                            selectedIndex = position
                            notifyItemChanged(selectedIndex)
                            if (temp >= 0) {
                                notifyItemChanged(temp)
                            }
                        } else {
                            selectedIndex = -1
                            notifyItemChanged(position)
                        }
                    }
                }
            }
        }
    }
}