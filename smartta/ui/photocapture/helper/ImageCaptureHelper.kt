package vn.com.vti.smartta.ui.photocapture.helper

import android.content.Context
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.common.MlKitException
import com.google.mlkit.vision.face.FaceDetectorOptions
import timber.log.Timber
import java.io.File
import java.util.concurrent.ExecutionException


class ImageCaptureHelper(val context: Context?, private val faceDetectedListener: (Boolean) -> Unit) : ICaptureHelper {

    private var lensFacing = CameraSelector.LENS_FACING_FRONT
    private var cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    private var cameraProvider: ProcessCameraProvider? = null

    private var previewUseCase: Preview? = null
    private lateinit var surfaceProvider: Preview.SurfaceProvider
    private var imageCaptureUseCase: ImageCapture? = null
    private var analysisUseCase: ImageAnalysis? = null
    private lateinit var analyzer: ImageAnalysis.Analyzer

    private var imageProcessor: VisionImageProcessor? = null
    private var needUpdateGraphicOverlayImageSourceInfo = false

    init {
        val faceDetectorOptions = FaceDetectorOptions.Builder()
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_NONE)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .setMinFaceSize(0.1F)
            .build()
        imageProcessor = FaceDetectorProcessor(faceDetectorOptions) {
            faceDetectedListener.invoke(it)
        }

    }

    private var cameraProviderLiveData: MutableLiveData<ProcessCameraProvider>? = null

    private fun getProcessCameraProvider(): LiveData<ProcessCameraProvider?>? {
        if (cameraProviderLiveData == null) {
            cameraProviderLiveData = MutableLiveData()
            val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
                ProcessCameraProvider.getInstance(context!!)
            cameraProviderFuture.addListener(
                {
                    try {
                        cameraProviderLiveData!!.setValue(cameraProviderFuture.get())
                    } catch (e: ExecutionException) {
                        // Handle any errors (including cancellation) here.
                        Timber.d(e)
                    } catch (e: InterruptedException) {
                        Timber.d(e)
                    }
                },
                ContextCompat.getMainExecutor(context)
            )
        }
        return cameraProviderLiveData
    }

    private fun bindPreviewUseCase(lifecycle: LifecycleOwner) {
        val builder = Preview.Builder()
        previewUseCase = builder.build()
        previewUseCase!!.setSurfaceProvider(surfaceProvider)
        cameraProvider!!.bindToLifecycle(lifecycle, cameraSelector, previewUseCase)
    }

    private fun bindAnalysisUseCase(lifecycle: LifecycleOwner) {
        if (imageProcessor != null) {
            imageProcessor!!.stop()
        }

        val builder = ImageAnalysis.Builder()
        analysisUseCase = builder.build()

        needUpdateGraphicOverlayImageSourceInfo = true

        // imageProcessor.processImageProxy will use another thread to run the detection underneath,
        // thus we can just runs the analyzer itself on main thread.
        analysisUseCase?.setAnalyzer(ContextCompat.getMainExecutor(context), analyzer)
        cameraProvider!!.bindToLifecycle(lifecycle, cameraSelector, analysisUseCase)
    }

    private fun bindImageCaptureUseCase(lifecycle: LifecycleOwner){
        val builder = ImageCapture.Builder()
        imageCaptureUseCase = builder.build()
        cameraProvider!!.bindToLifecycle(lifecycle, cameraSelector, imageCaptureUseCase)
    }

    private fun bindAllCameraUseCases(lifecycle: LifecycleOwner) {
        if (cameraProvider != null) {
            // As required by CameraX API, unbinds all use cases before trying to re-bind any of them.
            cameraProvider!!.unbindAll()
            bindPreviewUseCase(lifecycle)
            bindImageCaptureUseCase(lifecycle)
            bindAnalysisUseCase(lifecycle)
        }
    }

    fun startObservingCamera(lifecycle: LifecycleOwner) {
        getProcessCameraProvider()!!
            .observe(lifecycle, { provider: ProcessCameraProvider? ->
                Timber.d("observed")
                cameraProvider = provider
                bindAllCameraUseCases(lifecycle)
            })
    }

    fun takePicture(position: Int, callback: (String) -> Unit) {
        val imageDir = File(context?.filesDir, "myImages")
        if(!imageDir.exists()) imageDir.mkdir()
        val newImage = File(imageDir, "$position.jpeg")
        val outputFileOption = ImageCapture.OutputFileOptions.Builder(newImage).build()
        imageCaptureUseCase!!.takePicture(outputFileOption, ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    callback.invoke(newImage.absolutePath)
                }

                override fun onError(exception: ImageCaptureException) {
                    Timber.d("save image error")
                    callback.invoke("error")
                }

            })
    }

    fun deleteFile(){
        val imageDir = File(context?.filesDir, "myImages")
        if(imageDir.exists()) imageDir.delete()
    }

    override fun initAnalyzer(graphicOverlay: GraphicOverlay){
        Timber.d("init analyzer %s",  graphicOverlay.toString())
        analyzer = ImageAnalysis.Analyzer { imageProxy: ImageProxy ->
            Timber.d("analysis image: %s", imageProxy.toString())
            if (needUpdateGraphicOverlayImageSourceInfo) {
                val isImageFlipped =
                    lensFacing == CameraSelector.LENS_FACING_FRONT
                val rotationDegrees =
                    imageProxy.imageInfo.rotationDegrees
                if (rotationDegrees == 0 || rotationDegrees == 180) {
                    graphicOverlay.setImageSourceInfo(
                        imageProxy.width, imageProxy.height, isImageFlipped
                    )
                } else {
                    graphicOverlay.setImageSourceInfo(
                        imageProxy.height, imageProxy.width, isImageFlipped
                    )
                }
                needUpdateGraphicOverlayImageSourceInfo = false
            }
            try {
                imageProcessor!!.processImageProxy(imageProxy, graphicOverlay)
                Timber.d("process it")
            } catch (e: MlKitException) {
                Timber.d("Failed to process image. Error: %s", e.localizedMessage)
            }
        }
    }

    override fun initSurfaceProvider(surfaceProvider: Preview.SurfaceProvider) {
        Timber.d("init surface %s", surfaceProvider.toString())
        this.surfaceProvider = surfaceProvider
    }


}