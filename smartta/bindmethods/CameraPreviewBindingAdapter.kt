@file:Suppress("unused")

package vn.com.vti.smartta.bindmethods

import android.widget.ImageView
import androidx.camera.view.PreviewView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import vn.com.vti.smartta.R
import vn.com.vti.smartta.ui.photocapture.helper.GraphicOverlay
import vn.com.vti.smartta.ui.photocapture.helper.ICaptureHelper

@BindingAdapter(value = ["imageCaptureHelper"], requireAll = true)
fun PreviewView.setUpWithCaptureHelper(imageICaptureHelper: ICaptureHelper) {
    imageICaptureHelper.initSurfaceProvider(this.surfaceProvider)
}

@BindingAdapter(value = ["imageCaptureHelper"], requireAll = true)
fun GraphicOverlay.setUpWithCaptureHelper(imageICaptureHelper: ICaptureHelper){
    imageICaptureHelper.initAnalyzer(this)
}

@BindingAdapter(value = ["filePathSrc"], requireAll = true)
fun ImageView.loadFromBitmap(file: String?){
    Glide.with(this.context)
        .load(file).error(R.drawable.sample_avt)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .into(this)
}




