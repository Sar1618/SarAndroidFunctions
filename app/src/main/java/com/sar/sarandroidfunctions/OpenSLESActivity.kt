package com.sar.sarandroidfunctions

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sar.sarandroidfunctions.databinding.ActivityOpenSlEsBinding

class OpenSLESActivity : AppCompatActivity() {

    lateinit var dataBinding: ActivityOpenSlEsBinding

    var requestPermissions =
        mutableListOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val requestPermissionsCode = 100

    var isGrantAllNeedPermissions = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_open_sl_es)
        dataBinding.tvAudioRecord.setOnClickListener {
            if (!isGrantAllNeedPermissions) {
                return@setOnClickListener
            }
        }
        dataBinding.tvAudioPlay.setOnClickListener {
            if (!isGrantAllNeedPermissions) {
                return@setOnClickListener
            }
        }
        requestPermissions = filterPermissionsForRequest(this, requestPermissions)
        if (requestPermissions.size > 0) {
            requestPermissions(requestPermissions.toTypedArray(), requestPermissionsCode)
        } else {
            isGrantAllNeedPermissions = true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != requestPermissionsCode) {
            return
        }
        isGrantAllNeedPermissions = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
    }
}