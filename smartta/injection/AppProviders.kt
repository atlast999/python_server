package vn.com.vti.smartta.injection

import dagger.Module
import vn.com.vti.smartta.injection.provider.IActivityProvider
import vn.com.vti.smartta.injection.provider.IFragmentProvider
import vn.com.vti.smartta.injection.provider.IViewModelProvider

@Module(includes = [IActivityProvider::class, IFragmentProvider::class, IViewModelProvider::class])
interface AppProviders