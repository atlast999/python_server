package vn.com.vti.smartta.model.data.menu

import androidx.annotation.DrawableRes
import vn.com.vti.smartta.R

data class MainMenuItem(
    val id: Int = 0,
    @DrawableRes val icon: Int? = R.drawable.ic_navigate_next,
    val title: String?
)

object MainMenuAction {
    const val TO_PROFILE = 1
    const val TO_TIMESHEET_PERSONAL = 2
    const val TO_TIMESHEET_DEPARTMENT = 3
    const val TO_SCHEDULE_PERSONAL = 4
    const val TO_NOTIFICATION = 5
    const val TO_SETTINGS = 6
    const val TO_ACCESSIBLE_ZONE = 7
}