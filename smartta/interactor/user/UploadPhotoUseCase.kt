package vn.com.vti.smartta.interactor.user

import io.reactivex.rxjava3.core.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import vn.com.vti.common.domain.usecase.rx.SingleUseCase
import java.io.File
import javax.inject.Inject

class UploadPhotoUseCase @Inject constructor(): SingleUseCase<String, List<String>>() {


    override fun create(params: List<String>?): Single<out String> {
        val files = mutableListOf<MultipartBody.Part>()
        params?.forEach {
            val file = File(it)
            if (file.exists()){
                val part = MultipartBody.Part.createFormData("files", file.name, RequestBody.create(
                    "multipart/form-data".toMediaTypeOrNull(), file))
                files.add(part)
            }
        }
        return Single.just(files.size.toString())
    }
}