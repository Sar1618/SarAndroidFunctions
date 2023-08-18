package com.sar.sarandroidfunctions

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sar.sarandroidfunctions.databinding.ActivityAudioRecordAndAudioTrackBinding

class AudioRecordAndAudioTrackActivity : AppCompatActivity() {

    lateinit var dataBinding: ActivityAudioRecordAndAudioTrackBinding

    var requestPermissions =
        mutableListOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val requestPermissionsCode = 100

    var isGrantAllNeedPermissions = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_audio_record_and_audio_track)
        dataBinding.tvAudioRecord.setOnClickListener {
            if (!isGrantAllNeedPermissions) {
                return@setOnClickListener
            }
            startActivity(Intent(this, AudioRecordActivity::class.java))
        }
        dataBinding.tvAudioPlay.setOnClickListener {
            if (!isGrantAllNeedPermissions) {
                return@setOnClickListener
            }
            startActivity(Intent(this, AudioPlayActivity::class.java))
        }
        dataBinding.tvAudioRecordAndPlay.setOnClickListener {
            if (!isGrantAllNeedPermissions) {
                return@setOnClickListener
            }
            startActivity(Intent(this, AudioRecordAndPlayAtSameTimeActivity::class.java))
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