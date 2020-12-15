package vn.com.vti.smartta.ui.photocapture

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import vn.com.vti.smartta.BR
import vn.com.vti.smartta.R
import vn.com.vti.smartta.base.scene.SmartTaActivity
import vn.com.vti.smartta.databinding.ActivityPhotoCaptureBinding
import vn.com.vti.smartta.ui.photocapture.contract.PhotoCaptureContract
import vn.com.vti.smartta.ui.photocapture.contract.PhotoCaptureViewModel

class PhotoCaptureActivity : SmartTaActivity<PhotoCaptureContract.ViewModel>() {

    private lateinit var binding: ActivityPhotoCaptureBinding

    override fun onCreateViewDataBinding(): ViewDataBinding {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_capture)
        binding.navToolbar.toolbar.apply {
            setSupportActionBar(this)
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    onBackPressed()
                }

            })

        }
        return binding
    }

    override fun getViewModelClass(): Class<out ViewModel> {
        return PhotoCaptureViewModel::class.java
    }

    override fun getViewModelVariableId(): Int {
        return BR.vm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.observeCamera(this)
    }



    override fun onResume() {
        super.onResume()
        viewModel.observeCamera(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_confirm, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.action_done) {
            if(viewModel.getButtonDoneVisibility()){
                onBackPressed()
            }

        }
        return super.onOptionsItemSelected(item)
    }


}