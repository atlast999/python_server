package vn.com.vti.smartta.interactor.user

import io.reactivex.rxjava3.core.Single
import vn.com.vti.common.domain.usecase.rx.SingleUseCase
import vn.com.vti.smartta.model.pojo.BaseResponse
import vn.com.vti.smartta.model.pojo.user.User
import vn.com.vti.smartta.model.pojo.user.UserInfoRequest
import vn.com.vti.smartta.model.repository.ClientRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor() :
    SingleUseCase<BaseResponse<User>, UserInfoRequest>() {
    @Inject
    lateinit var repository: ClientRepository

    override fun create(params: UserInfoRequest?): Single<out BaseResponse<User>> {
        return params?.let {
            Single.defer {
                repository.fetchMyProfile()
            }
        } ?: errorParamsSingle()
    }


}