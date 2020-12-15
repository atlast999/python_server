package vn.com.vti.smartta.ui.photocapture.helper

import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import timber.log.Timber

/** Face Detector Demo.  */
class FaceDetectorProcessor(
    detectorOptions: FaceDetectorOptions,
    private val faceDetectedListener: (Boolean) -> Unit
) :
    VisionProcessorBase<List<Face>>() {

    private val detector: FaceDetector = FaceDetection.getClient(detectorOptions)

    override fun stop() {
        super.stop()
        detector.close()
    }

    override fun detectInImage(image: InputImage): Task<List<Face>> {
        return detector.process(image)
    }

    override fun onSuccess(results: List<Face>, graphicOverlay: GraphicOverlay) {
        for (face in results) {
            graphicOverlay.add(FaceGraphic(graphicOverlay, face) {
                if (GraphicsFocusZoneView.sInnerRectangle.contains(it)) {
                    Timber.d(it.toShortString())
                    faceDetectedListener(true)
                } else {
                    Timber.d(it.toShortString())
                    faceDetectedListener(false)
                }
            })
        }
        if (results.size == 0) {
            faceDetectedListener.invoke(false)
        }
    }
        override fun onFailure(e: Exception) {
            Timber.d(e)
        }

    }