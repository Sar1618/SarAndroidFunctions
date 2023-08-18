package com.sar.sarandroidfunctions

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack

object AudioPlayer {
    val sampleRateInHz = 44100
    val channelConfig = AudioFormat.CHANNEL_OUT_MONO
    val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    val bufferSize = AudioTrack.getMinBufferSize(
        sampleRateInHz, channelConfig, audioFormat
    ) * 2

    var audioTrack: AudioTrack? = null

    @SuppressLint("MissingPermission")
    fun startPlay() {
        audioTrack = AudioTrack(AudioManager.STREAM_MUSIC, sampleRateInHz, channelConfig, audioFormat, bufferSize, AudioTrack.MODE_STREAM)
        audioTrack?.play()
    }

    fun stopPlay() {
        audioTrack?.release()
        audioTrack = null
    }

    fun onDataReceiver(byteArray: ByteArray) {
        audioTrack?.write(byteArray, 0, byteArray.size)
    }
}