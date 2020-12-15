package vn.com.vti.smartta.interactor.main

import io.reactivex.rxjava3.core.Single
import vn.com.vti.common.adapter.tree.Node
import vn.com.vti.common.domain.usecase.rx.SingleUseCase
import vn.com.vti.common.util.AppResources
import vn.com.vti.smartta.R
import vn.com.vti.smartta.model.data.menu.MainMenuAction
import vn.com.vti.smartta.model.data.menu.MainMenuItem
import javax.inject.Inject

class FetchNavSlideMenuItemUseCase @Inject constructor() :
    SingleUseCase<List<Node<MainMenuItem>>, Unit>() {

    override fun create(params: Unit?): Single<out List<Node<MainMenuItem>>> = Single.defer {
        listOf(
            /*Node(
                type = Node.ITEM,
                data = MainMenuItem(
                    id = MainMenuAction.TO_PROFILE,
                    icon = R.drawable.ic_account_circle,
                    title = AppResources.getString(R.string.menu_profile)
                )
            ),*/
            Node(
                data = MainMenuItem(title = AppResources.getString(R.string.menu_timesheet)),
                childNode = listOf(
                    Node(
                        level = 1,
                        type = Node.ITEM,
                        data = MainMenuItem(
                            id = MainMenuAction.TO_TIMESHEET_PERSONAL,
                            icon = R.drawable.ic_access_time,
                            title = AppResources.getString(R.string.menu_timesheet_personal)
                        )
                    ),
                    Node(
                        level = 1,
                        type = Node.ITEM,
                        data = MainMenuItem(
                            id = MainMenuAction.TO_TIMESHEET_DEPARTMENT,
                            icon = R.drawable.ic_access_time,
                            title = AppResources.getString(R.string.menu_timesheet_department)
                        )
                    )
                )
            ),
            Node(
                type = Node.ITEM,
                data = MainMenuItem(
                    id = MainMenuAction.TO_SCHEDULE_PERSONAL,
                    icon = R.drawable.ic_event,
                    title = AppResources.getString(R.string.menu_schedule_personal)
                )
            ),
            Node(
                type = Node.ITEM,
                data = MainMenuItem(
                    id = MainMenuAction.TO_ACCESSIBLE_ZONE,
                    icon = R.drawable.ic_zone,
                    title = AppResources.getString(R.string.menu_accessible_zone)
                )
            ),
            Node(
                type = Node.ITEM,
                data = MainMenuItem(
                    id = MainMenuAction.TO_SETTINGS,
                    icon = R.drawable.ic_settings,
                    title = AppResources.getString(R.string.menu_settings)
                )
            ),
            Node(
                type = Node.ITEM,
                data = MainMenuItem(
                    id = MainMenuAction.TO_NOTIFICATION,
                    icon = R.drawable.ic_notifications_active,
                    title = AppResources.getString(R.string.menu_notification)
                )
            ),
        ).let {
            Single.just(it)
        }
    }

}