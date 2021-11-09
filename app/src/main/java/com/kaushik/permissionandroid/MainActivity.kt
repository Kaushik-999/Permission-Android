package com.kaushik.permissionandroid

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.kaushik.permissionandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRequestPermission.setOnClickListener{
            requestPermissions()
        }
    }

    /*
        Functions to check if the permission is granted or not
     */
    private fun hasWriteExternalStoragePermission() =
        ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun hasLocationForegroundPermission() =
        ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    // for a real app check if the user uses Android Q
    // else it will crash
//    private fun hasLocationBackgroundPermission() =
//        ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED


    /*
        functions to request permissions
     */
    private fun requestPermissions() {
        val permissionsToRequest = mutableListOf<String>()
        if(!hasWriteExternalStoragePermission()) {
            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if(!hasLocationForegroundPermission()) {
            permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        // you cant request foreground and background location at the same time
        // else it will through an exception
//        if(hasLocationForegroundPermission() && !hasLocationBackgroundPermission()) {
//            permissionsToRequest.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
//        }

        // checking if all permissions are already granted
        // ie if the list is not null then all permissions are already granted
        // IMPORTANT: if there are multiple request then it differentiated by the request code, here it is 0 the next maybe 1
        Log.d("PermissionsRequested",permissionsToRequest.toString())
        if(permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(),0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 0 && grantResults.isNotEmpty()) {
            for(i in grantResults.indices) {
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("PermissionRequest", "${permissions[i]} is granted")
                }
            }
        }


    }


}