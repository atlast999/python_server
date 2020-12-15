package vn.com.vti.smartta.ui.splash

import vn.com.vti.smartta.R
import vn.com.vti.smartta.base.scene.SmartTaActivity
import vn.com.vti.smartta.databinding.ActivitySplashBinding
import vn.com.vti.smartta.ui.splash.contract.SplashContract
import vn.com.vti.smartta.ui.splash.contract.SplashViewModel

class SplashActivity : SmartTaActivity<SplashContract.ViewModel>() {

    override fun onCreateViewDataBinding() =
        setContentViewBinding<ActivitySplashBinding>(R.layout.activity_splash)

    override fun getViewModelClass() = SplashViewModel::class.java


}