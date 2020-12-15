package vn.com.vti.smartta.ui.photocapture.helper

import androidx.camera.core.Preview

interface ICaptureHelper {
    fun initSurfaceProvider(surfaceProvider: Preview.SurfaceProvider)
    fun initAnalyzer(graphicOverlay: GraphicOverlay)
}