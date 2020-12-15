package vn.com.vti.smartta.ui.dialog.selection.contract

import android.app.Application
import android.widget.RadioGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.com.vti.common.adapter.RadioAdapter
import vn.com.vti.common.viewmodel.Finish
import vn.com.vti.common.viewmodel.impl.BaseAndroidViewModel
import vn.com.vti.smartta.ui.dialog.selection.adapter.ISingleChoice
import vn.com.vti.smartta.ui.dialog.selection.adapter.SingleChoiceRadioAdapter
import javax.inject.Inject

class SingleChoiceRadioViewModel @Inject constructor(application: Application) :
    BaseAndroidViewModel(application), SingleChoiceRadioContract.ViewModel {

    private val liveTitle = MutableLiveData<String>()
    private val liveAdapter = MutableLiveData<RadioAdapter>()
    private val liveSelected = MutableLiveData<ISingleChoice>()
    private val radioAdapter = SingleChoiceRadioAdapter().apply {
        setOnCheckedChangedListener { group: RadioGroup, checkedId: Int, position: Int ->
            liveSelected.value = getItem(position)
        }
    }

    override fun onAttachBundle(bundle: SingleChoiceRadioContract.ChoiceBundle) {
        liveTitle.value = bundle.title
        radioAdapter.setData(bundle.data)
        bundle.selected?.let {
            radioAdapter.selectedPosition = it
        }
        liveAdapter.value = radioAdapter
    }

    override fun getAdapter(): LiveData<RadioAdapter> = liveAdapter

    override fun getTitle(): LiveData<String> = liveTitle

    override fun onOptionSubmit() {
        liveSelected.value?.let {
            redirect(SingleChoiceRadioContract.SubmitDirection(it))
        }
    }

    override fun onOptionCanceled() {
        redirect(Finish)
    }

}