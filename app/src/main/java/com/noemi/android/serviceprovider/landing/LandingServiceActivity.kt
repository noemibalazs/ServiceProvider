package com.noemi.android.serviceprovider.landing

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.noemi.android.serviceprovider.R
import com.noemi.android.serviceprovider.databinding.ActivityLandingServiceBinding
import com.noemi.android.serviceprovider.util.PERMISSION_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingServiceBinding
    private val viewModel: LandingViewModel by viewModels()

    private val permissions = mutableListOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private val managerAdapter by lazy {
        ManagerAdapter(managerClickListener)
    }

    private val managerClickListener: ManagerClickListener = { item ->
        viewModel.openActivity(item, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_landing_service)

        if (!permissionsAreGranted()) {
            ActivityCompat.requestPermissions(this, permissions.toTypedArray(), PERMISSION_KEY)
        }

        binding.rvManger.apply {
            adapter = managerAdapter
            managerAdapter.submitList(viewModel.getListOManagers())
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_KEY && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permissions are granted!")
        } else {
            permissionsAreGranted()
        }
    }

    private fun permissionsAreGranted(): Boolean {
        var permission = true
        for (item in permissions) {
            permission = permission && checkIfPermissionAreGranted(item)
        }
        return permission
    }

    private fun checkIfPermissionAreGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }


    companion object {

        private val TAG = LandingServiceActivity::class.java.simpleName
    }
}