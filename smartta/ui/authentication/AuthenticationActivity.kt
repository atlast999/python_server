package vn.com.vti.smartta.ui.authentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import vn.com.vti.smartta.R
import vn.com.vti.smartta.databinding.ActivityAuthenticationBinding

class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityAuthenticationBinding>(this, R.layout.activity_authentication)

    }
}