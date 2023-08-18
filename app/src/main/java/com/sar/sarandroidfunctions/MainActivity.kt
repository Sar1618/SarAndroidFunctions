package com.sar.sarandroidfunctions

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sar.sarandroidfunctions.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBinding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        dataBinding.tvLocalPictureSelector.setOnClickListener {
            startActivity(Intent(this, LocalPictureSelectorActivity::class.java))
        }
        dataBinding.tvOnlineMp4Playback.setOnClickListener {
            startActivity(Intent(this, OnlineMp4PlaybackActivity::class.java))
        }
        dataBinding.tvAudioRecordAndAudioTrack.setOnClickListener {
            startActivity(Intent(this, AudioRecordAndAudioTrackActivity::class.java))
        }
        dataBinding.tvOpenSLES.setOnClickListener {
            startActivity(Intent(this, OpenSLESActivity::class.java))
        }
    }
}