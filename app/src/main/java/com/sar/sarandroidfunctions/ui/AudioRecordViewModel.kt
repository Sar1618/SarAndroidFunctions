package com.sar.sarandroidfunctions.ui

import android.os.Environment
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sar.sarandroidfunctions.AudioRecorder
import java.io.File
import java.io.FileOutputStream

class AudioRecordViewModel : ViewModel() {
    private val _audioRecordUIStateFlow = mutableStateOf(AudioRecordUIState(false))
    fun getAudioRecordUIStateFlow(): State<AudioRecordUIState> {
        return _audioRecordUIStateFlow
    }

    var fileOutputStream: FileOutputStream? = null
    fun recordStart() {
        val file =
            File(Environment.getExternalStorageDirectory(), "${System.currentTimeMillis()}.pcm")
        if (!file.exists()) {
            file.createNewFile()
        }
        fileOutputStream = FileOutputStream(file)
        _audioRecordUIStateFlow.value =
            _audioRecordUIStateFlow.value.copy(isRecording = true, filePath = file.absolutePath)
        AudioRecorder.startRecord {
            fileOutputStream?.write(it)
        }
    }

    fun recordStop() {
        _audioRecordUIStateFlow.value =
            _audioRecordUIStateFlow.value.copy(isRecording = false, filePath = null)
        AudioRecorder.stopRecord()
        fileOutputStream?.close()
        fileOutputStream = null
    }
}