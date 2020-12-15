package vn.com.vti.smartta.ui.dialog.selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import vn.com.vti.common.extension.inflateBinding
import vn.com.vti.common.viewmodel.Direction
import vn.com.vti.smartta.R
import vn.com.vti.smartta.base.scene.SmartTaBottomSheetDialogFragment
import vn.com.vti.smartta.ui.dialog.selection.adapter.ISingleChoice
import vn.com.vti.smartta.ui.dialog.selection.contract.SingleChoiceRadioContract
import vn.com.vti.smartta.ui.dialog.selection.contract.SingleChoiceRadioViewModel

class SingleChoiceRadioDialogFragment :
    SmartTaBottomSheetDialogFragment<SingleChoiceRadioContract.ViewModel>() {

    private var choiceListener: ((ISingleChoice) -> Unit)? = null
    private var choiceBundle: SingleChoiceRadioContract.ChoiceBundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_BottomSheet)
    }

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding = inflater.inflateBinding(R.layout.dialog_radio_selection, container)

    override fun getViewModelClass(): Class<out ViewModel> = SingleChoiceRadioViewModel::class.java

    override fun onSceneReady() {
        super.onSceneReady()
        choiceBundle?.let {
            viewModel.onAttachBundle(it)
        }
    }

    override fun onDispatchDirectionEvent(direction: Direction): Boolean {
        return (direction as? SingleChoiceRadioContract.SubmitDirection)?.let {
            choiceListener?.invoke(it.selection)
            dismissAllowingStateLoss()
            true
        } ?: super.onDispatchDirectionEvent(direction)
    }

    companion object {
        fun newInstance(
            choices: SingleChoiceRadioContract.ChoiceBundle,
            listener: (ISingleChoice) -> Unit
        ): SingleChoiceRadioDialogFragment {
            return SingleChoiceRadioDialogFragment().apply {
                choiceBundle = choices
                choiceListener = listener
            }
        }
    }
}