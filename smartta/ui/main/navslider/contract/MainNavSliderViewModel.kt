package vn.com.vti.smartta.ui.main.navslider.contract

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import vn.com.vti.common.adapter.tree.Node
import vn.com.vti.common.annotation.LoadingType
import vn.com.vti.common.ui.list.IListController
import vn.com.vti.common.ui.list.impl.ListController
import vn.com.vti.common.util.notNullOrEmptyLet
import vn.com.vti.common.viewmodel.NavGraphDirection
import vn.com.vti.smartta.R
import vn.com.vti.smartta.base.viewmodel.SmartTaViewModel
import vn.com.vti.smartta.interactor.main.FetchNavSlideMenuItemUseCase
import vn.com.vti.smartta.interactor.user.GetUserInfoUseCase
import vn.com.vti.smartta.model.data.menu.MainMenuAction
import vn.com.vti.smartta.model.data.menu.MainMenuItem
import vn.com.vti.smartta.model.pojo.user.User
import vn.com.vti.smartta.model.pojo.user.UserInfoRequest
import vn.com.vti.smartta.ui.main.navslider.adapter.MainNavSliderMenuAdapter
import javax.inject.Inject

class MainNavSliderViewModel @Inject constructor(application: Application) :
    SmartTaViewModel(application), MainNavSliderContract.ViewModel {

    @Inject
    lateinit var fetchNavSlideMenuItemUseCase: FetchNavSlideMenuItemUseCase

    @Inject
    lateinit var getUserInfoUseCase: GetUserInfoUseCase

    private val liveDataUser = MutableLiveData<User>()

    private val adapter: MainNavSliderMenuAdapter = MainNavSliderMenuAdapter().also {
        it.setItemClickListener { i, _, node ->
            node?.let {
                if (it.getType() == Node.GROUP) return@setItemClickListener
                when (it.getData().id) {
                    MainMenuAction.TO_NOTIFICATION -> R.id.toNoficationFragment
                    MainMenuAction.TO_PROFILE -> R.id.toProfileFragment
                    MainMenuAction.TO_TIMESHEET_DEPARTMENT -> R.id.toDepartmentTimesheetFragment
                    MainMenuAction.TO_TIMESHEET_PERSONAL -> R.id.toUserTimesheetFragment
                    MainMenuAction.TO_SCHEDULE_PERSONAL -> R.id.toScheduleFragment
                    MainMenuAction.TO_SETTINGS -> R.id.toSettingsFragment
                    MainMenuAction.TO_ACCESSIBLE_ZONE -> R.id.toAccessibleZoneFragment
                    else -> null
                }?.let {
                    val args = Bundle()
                    args.putInt("typeTimeSheet", it)
                    redirect(NavGraphDirection(it))
                }
            }
        }
    }

    private val controller = ListController(adapter = adapter)

    override fun getListController(): IListController = controller

    override fun getUser() = liveDataUser

    override fun onClickProfile() {
        redirect(NavGraphDirection(R.id.toProfileFragment))
    }

    override fun onReady() {
        super.onReady()
        fetch(fetchNavSlideMenuItemUseCase, object : BaseCallback<List<Node<MainMenuItem>>>() {

            override fun onStart() {
                super.onStart()
                controller.notifyLoaderStarted(LoadingType.FREE)
            }

            override fun onNext(result: List<Node<MainMenuItem>>) {
                result.notNullOrEmptyLet {
                    adapter.setMenu(result)
                }
            }

            override fun onComplete() {
                super.onComplete()
                controller.notifyLoaderFinished(LoadingType.FREE)
            }

        })
        getUserInfo()
    }

    private fun getUserInfo() {
        fetch(getUserInfoUseCase, params = UserInfoRequest(),
            foldSuccess = {
                liveDataUser.value = it.result
            })
    }

}