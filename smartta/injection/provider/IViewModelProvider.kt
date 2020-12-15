package vn.com.vti.smartta.injection.provider

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import vn.com.vti.smartta.ui.authentication.login.contract.LoginViewModel
import vn.com.vti.smartta.ui.authentication.password.forgot.contract.ForgotPasswordViewModel
import vn.com.vti.smartta.ui.dialog.detail_timesheet.contract.DetailTimeSheetViewModel
import vn.com.vti.smartta.ui.dialog.selection.contract.SingleChoiceRadioViewModel
import vn.com.vti.smartta.ui.main.contract.MainViewModel
import vn.com.vti.smartta.ui.main.navslider.contract.MainNavSliderViewModel
import vn.com.vti.smartta.ui.notification.contract.NotificationViewModel
import vn.com.vti.smartta.ui.photocapture.contract.PhotoCaptureViewModel
import vn.com.vti.smartta.ui.profile.avatar.contract.AvatarViewModel
import vn.com.vti.smartta.ui.profile.contract.ProfileViewModel
import vn.com.vti.smartta.ui.schedule.contract.ScheduleViewModel
import vn.com.vti.smartta.ui.setting.contract.SettingViewModel
import vn.com.vti.smartta.ui.splash.contract.SplashViewModel
import vn.com.vti.smartta.ui.timesheet.contract.DepartmentTimesheetViewModel
import vn.com.vti.smartta.ui.timesheet.contract.UserTimesheetViewModel
import vn.com.vti.smartta.ui.zone.contract.AccessibleZoneViewModel
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class ViewModelFactory @Inject constructor(private val providerMap: MutableMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return providerMap[modelClass]?.get() as T?
            ?: throw RuntimeException(modelClass.simpleName + " is not added to binds-map, check ViewModelModule")
    }

}

@Singleton
class InjectedAndroidViewModelFactory
@Inject constructor(
    application: Application,
    private val providerMap: MutableMap<Class<out AndroidViewModel>, @JvmSuppressWildcards Provider<AndroidViewModel>>
) : ViewModelProvider.AndroidViewModelFactory(application) {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (AndroidViewModel::class.java.isAssignableFrom(modelClass))
            providerMap[modelClass as Class<out AndroidViewModel>]?.get() as? T
                ?: throw RuntimeException(modelClass.simpleName + " is not added to binds-map, check ViewModelModule")
        else
            super.create(modelClass)
    }
}

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class AndroidViewModelKey(val value: KClass<out AndroidViewModel>)

@Module
abstract class IViewModelProvider {

    @AndroidViewModelKey(SplashViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideSplashViewModel(vm: SplashViewModel): AndroidViewModel

    @AndroidViewModelKey(MainNavSliderViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideMainNavSliderViewModel(vm: MainNavSliderViewModel): AndroidViewModel

    @AndroidViewModelKey(ProfileViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideProfileViewModel(vm: ProfileViewModel): AndroidViewModel

    @AndroidViewModelKey(AvatarViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideAvatarViewModel(vm: AvatarViewModel): AndroidViewModel

    @AndroidViewModelKey(ScheduleViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideScheduleViewModel(vm: ScheduleViewModel): AndroidViewModel

    @AndroidViewModelKey(MainViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideMainViewModel(vm: MainViewModel): AndroidViewModel

    @Binds
    abstract fun bindViewModelProviderFactory(vmf: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    abstract fun bindAndroidViewModelProviderFactory(vmf: InjectedAndroidViewModelFactory): ViewModelProvider.AndroidViewModelFactory

    @AndroidViewModelKey(NotificationViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideNotificationViewModel(vm: NotificationViewModel): AndroidViewModel

    @AndroidViewModelKey(LoginViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideLoginViewModel(vm: LoginViewModel): AndroidViewModel

    @AndroidViewModelKey(SettingViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideSettingViewModel(vm: SettingViewModel): AndroidViewModel

    @AndroidViewModelKey(SingleChoiceRadioViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideSingleChoiceRadioViewModel(vm: SingleChoiceRadioViewModel): AndroidViewModel

    @AndroidViewModelKey(AccessibleZoneViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideAccessibleZoneViewModel(vm: AccessibleZoneViewModel): AndroidViewModel

    @AndroidViewModelKey(PhotoCaptureViewModel::class)
    @Binds
    @IntoMap
    abstract fun providePhotoCaptureViewModel(vm: PhotoCaptureViewModel): AndroidViewModel

    @AndroidViewModelKey(DetailTimeSheetViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideDetailTimeSheetViewModel(vm: DetailTimeSheetViewModel): AndroidViewModel

    @AndroidViewModelKey(ForgotPasswordViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideForgotPasswordViewModel(vm: ForgotPasswordViewModel): AndroidViewModel

    @AndroidViewModelKey(UserTimesheetViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideUserTimesheetViewModel(vm: UserTimesheetViewModel): AndroidViewModel

    @AndroidViewModelKey(DepartmentTimesheetViewModel::class)
    @Binds
    @IntoMap
    abstract fun provideDepartmentTimesheetViewModel(vm: DepartmentTimesheetViewModel): AndroidViewModel
}