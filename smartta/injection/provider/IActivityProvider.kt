package vn.com.vti.smartta.injection.provider

import dagger.Module
import dagger.android.ContributesAndroidInjector
import vn.com.vti.common.injection.scope.ActivityScope
import vn.com.vti.smartta.ui.main.MainActivity
import vn.com.vti.smartta.ui.photocapture.PhotoCaptureActivity
import vn.com.vti.smartta.ui.splash.SplashActivity

@Module
interface IActivityProvider {

    @ContributesAndroidInjector
    @ActivityScope
    fun provideSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    @ActivityScope
    fun provideMainActivity(): MainActivity

    @ContributesAndroidInjector
    @ActivityScope
    fun providePhotoCaptureActivity(): PhotoCaptureActivity

}