package vn.com.vti.smartta

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import vn.com.vti.common.BaseApplication
import vn.com.vti.common.util.logger.CrashlyticTree
import vn.com.vti.common.util.logger.logDisplayDimensions
import vn.com.vti.smartta.injection.AppInjector

import vn.com.vti.smartta.injection.DaggerAppInjector

class App : BaseApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appInjector: AppInjector = DaggerAppInjector.builder()
            .application(this)
            .build()
        appInjector.inject(this)
        return appInjector
    }

    override fun onCreate() {
        super.onCreate()
        if (isDebugMode()) {
            logDisplayDimensions()
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashlyticTree())
        }
    }

}