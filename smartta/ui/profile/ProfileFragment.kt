package vn.com.vti.smartta.ui.profile

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import vn.com.vti.common.extension.inflateBinding
import vn.com.vti.common.viewmodel.ActionDirection
import vn.com.vti.common.viewmodel.Direction
import vn.com.vti.smartta.R
import vn.com.vti.smartta.base.scene.SmartTaFragment
import vn.com.vti.smartta.ui.authentication.AuthenticationActivity
import vn.com.vti.smartta.ui.photocapture.PhotoCaptureActivity
import vn.com.vti.smartta.ui.profile.avatar.UpdateAvatarDialogFragment
import vn.com.vti.smartta.ui.profile.contract.ProfileContract
import vn.com.vti.smartta.ui.profile.contract.ProfileViewModel

class ProfileFragment : SmartTaFragment<ProfileContract.ViewModel>() {

    val requiredPermissions: ArrayList<String?> = ArrayList()

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding = inflater.inflateBinding(R.layout.fragment_profile, container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requiredPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        requiredPermissions.add(Manifest.permission.CAMERA)
    }


    override fun getViewModelClass(): Class<out ViewModel> = ProfileViewModel::class.java

    override fun onDispatchDirectionEvent(direction: Direction): Boolean {
        (direction as? ActionDirection)?.let {
            when (it.actionId) {
                ProfileContract.Action.ACTION_CHANGE_AVT -> {
                    UpdateAvatarDialogFragment().show(childFragmentManager, "TAG")
                }
                ProfileContract.Action.ACTION_UPLOAD_PHOTO -> {
                    if (!allPermissionsGranted()) {
                        runtimePermissions
                    } else {
                        val intent = Intent(activity, PhotoCaptureActivity::class.java)
                        startActivity(intent)
                    }

                }
                ProfileContract.Action.ACTION_LOGOUT -> {
                    val intent = Intent(activity, AuthenticationActivity::class.java)
                    startActivity(intent)
                }

            }
        }
        return super.onDispatchDirectionEvent(direction)
    }


    private val runtimePermissions: Unit
        get() {
            if (requiredPermissions.isNotEmpty() && requiredPermissions.size == 2) {
                requestPermissions(
                    requiredPermissions.toTypedArray(),
                    100
                )
            }
        }

    private fun allPermissionsGranted(): Boolean {

        for (permission in requiredPermissions) {
            if (!isPermissionGranted(activity?.applicationContext!!, permission)) {
                return false
            }
        }
        return true
    }

    private fun isPermissionGranted(
        context: Context,
        permission: String?
    ): Boolean {
        if (ContextCompat.checkSelfPermission(context, permission!!)
            == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }

        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == 100) {
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                val intent = Intent(activity, PhotoCaptureActivity::class.java)
                startActivity(intent)
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_confirm, menu)
        menu.findItem(R.id.action_done).title = resources.getString(R.string.common_edit)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.action_done) {
            viewModel.onClickEdit()
            if (viewModel.editProfile().value!!) {
                item.title = resources.getString(R.string.common_done)
            } else {
                item.title = resources.getString(R.string.common_edit)
            }
        } else if (id == R.id.action_upload_photo){
            viewModel.onClickUploadPhoto()
        } else if (id == R.id.action_logout){
            viewModel.onClickLogout()
        }
        return super.onOptionsItemSelected(item)
    }

}