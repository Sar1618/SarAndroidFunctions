package com.sar.sarandroidfunctions

import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sar.sarandroidfunctions.databinding.ActivityOnlineMp4PlaybackBinding


class OnlineMp4PlaybackActivity : AppCompatActivity() {
    lateinit var dataBinding: ActivityOnlineMp4PlaybackBinding
    var isPrepared = false
    var currentPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_online_mp4_playback)
        playMp4()
    }

    private fun playMp4() {
        val mp4Url = "http://10.20.22.12:9000/video/F21030314883F2023-06-25F21030314883-20230625103000ft20230625235959.mp4"
        dataBinding.vv.setVideoPath(mp4Url)
        dataBinding.vv.setOnErrorListener { _, what, extra ->
            Toast.makeText(
                this@OnlineMp4PlaybackActivity,
                "视频播放失败 what = $what extra = $extra",
                Toast.LENGTH_LONG
            ).show()
            isPrepared = false
            finish()
            return@setOnErrorListener true
        }
        val mediaController = MediaController(this)
        dataBinding.vv.setMediaController(mediaController)
        dataBinding.vv.setOnPreparedListener { mp ->
            isPrepared = true
            mp.isLooping = true
            mp.start()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!dataBinding.vv.isPlaying && isPrepared) {
            dataBinding.vv.seekTo(currentPosition)
            dataBinding.vv.start()
        }
    }

    override fun onPause() {
        super.onPause()
        if (dataBinding.vv.isPlaying) {
            dataBinding.vv.pause()
            currentPosition = dataBinding.vv.currentPosition
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dataBinding.vv.stopPlayback()
    }
}