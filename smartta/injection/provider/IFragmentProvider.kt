package vn.com.vti.smartta.injection.provider

import dagger.Module
import dagger.android.ContributesAndroidInjector
import vn.com.vti.common.injection.scope.FragmentScope
import vn.com.vti.smartta.ui.authentication.login.LoginFragment
import vn.com.vti.smartta.ui.authentication.password.forgot.ForgotPasswordFragment
import vn.com.vti.smartta.ui.dialog.detail_timesheet.DetailTimesheetDialog
import vn.com.vti.smartta.ui.dialog.selection.SingleChoiceRadioDialogFragment
import vn.com.vti.smartta.ui.main.navslider.MainNavSliderFragment
import vn.com.vti.smartta.ui.notification.NotificationFragment
import vn.com.vti.smartta.ui.profile.ProfileFragment
import vn.com.vti.smartta.ui.profile.avatar.UpdateAvatarDialogFragment
import vn.com.vti.smartta.ui.schedule.ScheduleFragment
import vn.com.vti.smartta.ui.setting.SettingFragment
import vn.com.vti.smartta.ui.timesheet.TimesheetFragment
import vn.com.vti.smartta.ui.zone.AccessibleZoneFragment

@Module
interface IFragmentProvider {

    @ContributesAndroidInjector
    @FragmentScope
    fun provideNotificationFragment(): NotificationFragment

    @ContributesAndroidInjector
    @FragmentScope
    fun provideProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    @FragmentScope
    fun provideMainNavSliderFragment(): MainNavSliderFragment

    @ContributesAndroidInjector
    @FragmentScope
    fun provideAvatarFragment(): UpdateAvatarDialogFragment

    @ContributesAndroidInjector
    @FragmentScope
    fun provideScheduleFragment(): ScheduleFragment

    @ContributesAndroidInjector
    @FragmentScope
    fun provideTimeSheetFragment(): TimesheetFragment

    @ContributesAndroidInjector
    @FragmentScope
    fun provideSettingFragment(): SettingFragment

    @ContributesAndroidInjector
    @FragmentScope
    fun provideLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    @FragmentScope
    fun provideSingleChoiceRadioDialogFragment(): SingleChoiceRadioDialogFragment

    @ContributesAndroidInjector
    @FragmentScope
    fun provideAccessibleZoneFragment(): AccessibleZoneFragment

    @ContributesAndroidInjector
    @FragmentScope
    fun provideDetailTimesheetDialog(): DetailTimesheetDialog

    @ContributesAndroidInjector
    @FragmentScope
    fun provideForgotPasswordFragment(): ForgotPasswordFragment

}