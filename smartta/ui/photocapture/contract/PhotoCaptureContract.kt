package vn.com.vti.smartta.ui.photocapture.contract


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import vn.com.vti.common.viewmodel.AbsViewModel
import vn.com.vti.smartta.ui.photocapture.adapter.PhotoAdapter
import vn.com.vti.smartta.ui.photocapture.helper.ICaptureHelper

interface PhotoCaptureContract {

    interface ViewModel : AbsViewModel {

        fun observeCamera(lifecycle: LifecycleOwner)

        fun getCaptureHelper(): ICaptureHelper

        fun takePhoto()

        fun getAdapterPhoto(): PhotoAdapter

        fun getTakePhotoVisibility(): LiveData<Boolean>

        fun uploadPhotos()

        fun getButtonDoneVisibility(): Boolean
    }
}