package vn.com.vti.smartta.ui.dialog.selection.adapter

import android.view.LayoutInflater
import android.view.View
import android.widget.RadioGroup
import vn.com.vti.common.adapter.RadioAdapter
import vn.com.vti.common.extension.inflateBinding
import vn.com.vti.smartta.R
import vn.com.vti.smartta.databinding.ItemRadioOptionBinding

class SingleChoiceRadioAdapter : RadioAdapter() {

    private val data: MutableList<ISingleChoice> = mutableListOf()

    override fun getItemCount(): Int = data.size

    override fun onCreateRadioButton(
        parent: RadioGroup,
        inflater: LayoutInflater,
        position: Int
    ): View =
        inflater.inflateBinding<ItemRadioOptionBinding>(R.layout.item_radio_option, parent, false)
            .apply {
                option = data[position]
            }.root

    fun setData(list: List<ISingleChoice>?) {
        data.clear()
        list?.let {
            data.addAll(it)
        }
    }

    fun getItem(position: Int) = data[position]
}