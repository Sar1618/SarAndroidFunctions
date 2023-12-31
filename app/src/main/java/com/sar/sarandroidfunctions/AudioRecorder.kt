package com.sar.sarandroidfunctions

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder.AudioSource

object AudioRecorder : Runnable {
    val audioSource = AudioSource.VOICE_COMMUNICATION
    val sampleRateInHz = 44100
    val channelConfig = AudioFormat.CHANNEL_IN_MONO
    val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    val bufferSize = AudioRecord.getMinBufferSize(
        sampleRateInHz, channelConfig, audioFormat
    ) * 2

    var audioRecord: AudioRecord? = null
    var isRecording = false
    var byteArray = ByteArray(bufferSize)

    var onDataReceiver: ((ByteArray) -> Unit)? = null

    var thread: Thread? = null

    @SuppressLint("MissingPermission")
    fun startRecord(onDataReceiver: (ByteArray) -> Unit) {
        this.onDataReceiver = onDataReceiver
        audioRecord = AudioRecord(
            audioSource, sampleRateInHz, channelConfig, audioFormat, bufferSize
        )
        audioRecord?.startRecording()
        isRecording = true
        thread = Thread(this)
        thread?.start()
    }

    fun stopRecord() {
        isRecording = false
        thread?.join()
        thread?.interrupt()
        thread = null
        audioRecord?.release()
        audioRecord = null
        onDataReceiver = null
    }

    override fun run() {
        while (isRecording) {
            val read = audioRecord!!.read(byteArray, 0, bufferSize)
            if (read >= 0) {
                onDataReceiver?.invoke(byteArray)
            }
        }
    }
}