package com.sar.sarandroidfunctions

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sar.sarandroidfunctions.databinding.ActivityOpenSlEsBinding

class OpenSLESActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOpenSlEsBinding

    private var isRecording = false
    private var isPlaying = false

    var requestPermissions =
        mutableListOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val requestPermissionsCode = 100

    var isGrantAllNeedPermissions = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_open_sl_es)
        setContentView(binding.root)

        binding.tvAudioRecord.setOnClickListener {
            if (isRecording) {
                stopRecord()
            } else {
                startRecord()
            }
            isRecording = !isRecording
            binding.tvAudioRecord.text = if (isRecording) "停止录制" else "开始录制"
        }

        binding.tvAudioPlay.setOnClickListener {
            if (isPlaying) {
                stopPlay()
            } else {
                startPlay()
            }
            isPlaying = !isPlaying
            binding.tvAudioPlay.text = if (isPlaying) "停止播放" else "开始播放"
        }

        requestPermissions = filterPermissionsForRequest(this, requestPermissions)
        if (requestPermissions.size > 0) {
            requestPermissions(requestPermissions.toTypedArray(), requestPermissionsCode)
        } else {
            isGrantAllNeedPermissions = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
                startActivity(Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION))
            }
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

        if (isGrantAllNeedPermissions) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
                startActivity(Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        release()
    }

    external fun startPlay()
    external fun stopPlay()
    external fun startRecord()
    external fun stopRecord()
    external fun release()

    companion object {
        // Used to load the 'cppdemo' library on application startup.
        init {
            System.loadLibrary("cppdemo")
        }
    }
}