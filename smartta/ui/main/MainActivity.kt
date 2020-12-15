package vn.com.vti.smartta.ui.main

import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import vn.com.vti.common.viewmodel.Direction
import vn.com.vti.smartta.R
import vn.com.vti.smartta.base.scene.SmartTaActivity
import vn.com.vti.smartta.databinding.ActivityMainBinding
import vn.com.vti.smartta.ui.main.contract.MainContract
import vn.com.vti.smartta.ui.main.contract.MainViewModel

class MainActivity : SmartTaActivity<MainContract.ViewModel>() {

    private var binding: ActivityMainBinding? = null

    override fun onCreateViewDataBinding(): ViewDataBinding =
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .also {
                binding = it
                it.navToolbar.toolbar.apply {
                    setSupportActionBar(this)
                    setNavigationOnClickListener {
                        binding?.drawer?.openDrawer(GravityCompat.START)
                    }
                    val navController = findNavController(provideNavHostId())
                    navController.addOnDestinationChangedListener { _, destination, _ ->
                        title = destination.label
                    }
//                    val appBarConfiguration = AppBarConfiguration(navController.graph, it.drawer)
//                    setupWithNavController(navController, appBarConfiguration)
                }
            }

    override fun getViewModelClass(): Class<out ViewModel> = MainViewModel::class.java

    override fun provideNavHostId() = R.id.nav_host_fragment

    override fun dispatchDelegationDirectionEvent(direction: Direction) {
        binding?.run {
            drawer.closeDrawer(GravityCompat.START)
            root.postDelayed({
                super.dispatchDelegationDirectionEvent(direction)
            }, 256)
        }
    }


}