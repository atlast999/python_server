package vn.com.vti.smartta.base.viewmodel

import android.app.Application
import io.reactivex.rxjava3.disposables.Disposable
import vn.com.vti.common.annotation.LoadingType
import vn.com.vti.common.domain.usecase.UseCase
import vn.com.vti.common.ui.list.IListController
import vn.com.vti.common.viewmodel.impl.BaseInteractorViewModel
import vn.com.vti.smartta.R
import vn.com.vti.smartta.model.pojo.FailureResponseExecption

open class SmartTaViewModel(application: Application) : BaseInteractorViewModel(application) {

    protected abstract inner class ApiCallback<RESULT>(blocking: Boolean = false) :
        NetworkingCallback<RESULT>(blocking) {

        override fun onError(error: Throwable) {
            if (error is FailureResponseExecption) {
                error.failureResponse?.message?.let {
                    toast(it)
                }
            } else {
                super.onError(error)
            }
        }
    }

    override fun onNetworkConnectFailed() {
        super.onNetworkConnectFailed()
        toast(R.string.msg_no_netword_connections)
    }

    protected inline fun <R, P> fetch(
        useCase: UseCase<Disposable, R, P>,
        params: P? = null,
        blocking: Boolean = false,
        crossinline foldSuccess: (R) -> Unit,
        crossinline foldError: (Throwable) -> Boolean = { false },
    ) {
        fetch(useCase, object : ApiCallback<R>(blocking) {
            override fun onNext(result: R) {
                foldSuccess(result)
            }

            override fun onError(error: Throwable) {
                if (foldError(error)) {
                    return
                }
                super.onError(error)
            }

        }, params)
    }

    protected inline fun <R, P> fetch(
        useCaseWithController: Pair<UseCase<Disposable, R, P>, IListController>,
        params: P? = null,
        crossinline foldSuccess: (R) -> Unit,
        crossinline foldError: (Throwable) -> Boolean = { false },
    ) {
        val (useCase, controller) = useCaseWithController
        fetch(useCase, object : ApiCallback<R>(false) {

            override fun onStart() {
                super.onStart()
                controller.notifyLoaderStarted(LoadingType.FREE)
            }

            override fun onNext(result: R) {
                foldSuccess(result)
            }

            override fun onError(error: Throwable) {
                if (foldError(error)) {
                    return
                }
                super.onError(error)
            }

            override fun onComplete() {
                super.onComplete()
                controller.notifyLoaderFinished(LoadingType.FREE)
            }

        }, params)
    }

    protected inline fun <R, P> fetchBaseCallback(
        useCase: UseCase<Disposable, R, P>,
        params: P? = null,
        blocking: Boolean = false,
        crossinline foldSuccess: (R) -> Unit,
        crossinline foldError: (Throwable) -> Boolean = { false },
    ) {
        fetch(useCase, object : BaseCallback<R>(blocking) {
            override fun onNext(result: R) {
                foldSuccess(result)
            }

            override fun onError(error: Throwable) {
                if (foldError(error)) {
                    return
                }
                super.onError(error)
            }

        }, params)
    }
}