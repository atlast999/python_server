package vn.com.vti.smartta.ui.photocapture.contract

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber
import vn.com.vti.smartta.base.viewmodel.SmartTaViewModel
import vn.com.vti.smartta.interactor.user.UploadPhotoUseCase
import vn.com.vti.smartta.ui.photocapture.adapter.PhotoAdapter
import vn.com.vti.smartta.ui.photocapture.helper.ICaptureHelper
import vn.com.vti.smartta.ui.photocapture.helper.ImageCaptureHelper
import javax.inject.Inject

class PhotoCaptureViewModel @Inject constructor(application: Application) :
    SmartTaViewModel(application), PhotoCaptureContract.ViewModel {

    @Inject
    lateinit var uploadPhotoUseCase: UploadPhotoUseCase

    companion object {
        const val KEY_PATH = "PATH"
    }

    private val takePhotoVisibility = MutableLiveData(false)

    private val imageCaptureHelper = ImageCaptureHelper(application) {
        takePhotoVisibility.value = it
    }

    private val adapter = PhotoAdapter()

    override fun observeCamera(lifecycle: LifecycleOwner) {
        imageCaptureHelper.startObservingCamera(lifecycle)
    }

    override fun getCaptureHelper(): ICaptureHelper {
        return imageCaptureHelper
    }

    override fun onReady() {
        super.onReady()
        getData()
    }

    private fun getData() {
        repeat(6) {
            adapter.addData(KEY_PATH)
        }
    }

    override fun takePhoto() {
        adapter.getData().forEachIndexed { index, s ->
            if (s == KEY_PATH || adapter.selectedIndex == index) {
                imageCaptureHelper.takePicture(index) {
                    adapter.update(index, it)
                }
                return
            }
        }
    }

    override fun uploadPhotos() {
        fetch(uploadPhotoUseCase, object : BaseCallback<String>() {
            override fun onNext(result: String) {
                Timber.d(result)
            }

        }, adapter.dataList)
    }

    override fun getAdapterPhoto(): PhotoAdapter {
        return adapter
    }

    override fun getTakePhotoVisibility(): LiveData<Boolean> {
        return takePhotoVisibility
    }

    override fun getButtonDoneVisibility(): Boolean {
        adapter.getData().forEachIndexed { index, s ->
            if (s == KEY_PATH) {
                return false
            }

        }
        return true
    }

    override fun onUnbind() {
        super.onUnbind()
        imageCaptureHelper.deleteFile()
    }

}