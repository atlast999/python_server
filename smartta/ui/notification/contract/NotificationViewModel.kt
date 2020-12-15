package vn.com.vti.smartta.ui.notification.contract

import android.app.Application
import androidx.recyclerview.widget.RecyclerView
import vn.com.vti.common.annotation.LoadingType
import vn.com.vti.common.ui.list.impl.ListController
import vn.com.vti.common.ui.list.impl.ListController.Listener
import vn.com.vti.smartta.base.viewmodel.SmartTaViewModel
import vn.com.vti.smartta.interactor.notification.GetNotificationUseCase
import vn.com.vti.smartta.model.pojo.notification.Notification
import vn.com.vti.smartta.ui.notification.adapter.NotificationAdapter
import javax.inject.Inject

class NotificationViewModel @Inject constructor(application: Application) :
    SmartTaViewModel(application), NotificationContract.ViewModel {

    @Inject
    lateinit var getNotificationUseCase: GetNotificationUseCase

    private val adapter = NotificationAdapter()

    private val controller = ListController(adapter, false, object : Listener {

        override fun onContentLoadmore(totalItemsCount: Int, view: RecyclerView) {
            loadNotification()
        }

        override fun onContentRefresh() {
            adapter.clear()
            loadNotification()
        }

        override fun onRequestTryAgain() {

        }

    })

    override fun getNotificationController() = controller

    override fun getItemTouchHelper() = adapter.getItemTouchHelper()

    private fun loadNotification() {
        fetch(getNotificationUseCase, object : BaseCallback<List<Notification>>() {

            override fun onStart() {
                super.onStart()
                controller.notifyLoaderStarted(LoadingType.FREE)
            }

            override fun onNext(result: List<Notification>) {
                adapter.addData(result)
            }

            override fun onComplete() {
                super.onComplete()
                controller.notifyLoaderFinished(LoadingType.FREE)
            }

        })
    }

    override fun onReady() {
        super.onReady()
        loadNotification()
        controller.setRefreshEnable(true)
    }

}