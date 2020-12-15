package vn.com.vti.smartta.ui.notification.contract

import androidx.recyclerview.widget.ItemTouchHelper
import vn.com.vti.common.ui.list.IListController
import vn.com.vti.common.viewmodel.AbsViewModel

interface NotificationContract {

    interface ViewModel : AbsViewModel {

        fun getNotificationController(): IListController

        fun getItemTouchHelper(): ItemTouchHelper
    }

}