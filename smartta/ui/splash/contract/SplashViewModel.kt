package vn.com.vti.smartta.ui.splash.contract

import android.app.Application
import androidx.lifecycle.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import vn.com.vti.common.viewmodel.toIntentDirection
import vn.com.vti.smartta.BuildConfig
import vn.com.vti.smartta.R
import vn.com.vti.smartta.base.viewmodel.SmartTaViewModel
import vn.com.vti.smartta.interactor.authentication.FetchAuthenticationStateUseCase
import vn.com.vti.smartta.model.const.AuthState
import vn.com.vti.smartta.ui.authentication.AuthenticationActivity
import vn.com.vti.smartta.ui.main.MainActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashViewModel @Inject constructor(application: Application) :
    SmartTaViewModel(application), SplashContract.ViewModel, LifecycleObserver {

    @Inject
    lateinit var fetchAuthenticationStateUseCase: FetchAuthenticationStateUseCase

    private val liveGreetings = MutableLiveData<String>()

    private val liveVerion = MutableLiveData<String>()

    override fun getGreetings(): LiveData<String> = liveGreetings

    override fun getVersion(): LiveData<String> = liveVerion

    override fun onReady() {
        super.onReady()
        liveGreetings.value = resource.getString(R.string.app_name)
        liveVerion.value = BuildConfig.VERSION_NAME
    }

    override fun onAttachLifecycle(owner: LifecycleOwner) {
        super.onAttachLifecycle(owner)
        owner.lifecycle.addObserver(this)
    }

    override fun onDetachLifecycle(owner: LifecycleOwner) {
        super.onDetachLifecycle(owner)
        owner.lifecycle.removeObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onSceneResume() {
        cancelAll()
        Completable.complete().delay(2, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                fetchAuthState()
            }.also {
                addCancelable(it)
            }
    }

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onScenePause() {
        cancelAll()
    }

    private fun fetchAuthState() {
        fetchAuthenticationStateUseCase.cancelIfRunning()
        fetchBaseCallback(fetchAuthenticationStateUseCase, foldSuccess = {
            when (it) {
                AuthState.AUTHENTICATED -> redirect(
                    MainActivity::class.java.toIntentDirection(
                        finish = true
                    )
                )
                AuthState.TOKEN_EXPIRED -> {
                    //TODO perform refreshing-token
                    toast(R.string.msg_login_session_expired)
                    redirect(AuthenticationActivity::class.java.toIntentDirection(finish = true))
                }
                AuthState.GUEST -> redirect(
                    AuthenticationActivity::class.java.toIntentDirection(
                        finish = true
                    )
                )
            }
        }, blocking = true)
    }
}