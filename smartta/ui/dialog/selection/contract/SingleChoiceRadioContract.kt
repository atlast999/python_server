package vn.com.vti.smartta.ui.dialog.selection.contract

import androidx.lifecycle.LiveData
import vn.com.vti.common.adapter.RadioAdapter
import vn.com.vti.common.viewmodel.AbsViewModel
import vn.com.vti.common.viewmodel.Direction
import vn.com.vti.smartta.ui.dialog.selection.adapter.ISingleChoice

interface SingleChoiceRadioContract {

    data class SubmitDirection(val selection: ISingleChoice) : Direction(finish = true)

    data class ChoiceBundle(
        val title: String,
        val data: List<ISingleChoice>,
        val selected: Int? = null
    )

    interface ViewModel : AbsViewModel {

        fun onAttachBundle(bundle: ChoiceBundle)

        fun getAdapter(): LiveData<RadioAdapter>

        fun getTitle(): LiveData<String>

        fun onOptionSubmit()

        fun onOptionCanceled()
    }
}