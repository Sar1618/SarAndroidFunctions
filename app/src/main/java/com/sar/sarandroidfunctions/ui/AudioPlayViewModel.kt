package com.sar.sarandroidfunctions.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sar.sarandroidfunctions.AudioPlayer
import java.io.File
import java.io.FileInputStream

class AudioPlayViewModel : ViewModel() {
    private val _uIState = mutableStateOf(AudioPlayUIState(false))
    fun getUIStateFlow(): State<AudioPlayUIState> {
        return _uIState
    }

    var fileInputStream: FileInputStream? = null
    var thread: Thread? = null
    fun playStart() {
        _uIState.value =
            _uIState.value.copy(true)
        AudioPlayer.startPlay()
        val file = File("/storage/emulated/0/${_uIState.value.fileName}.pcm")
        fileInputStream = FileInputStream(file)
        thread = Thread(runnable)
        thread?.start()
    }

    val byteArray = ByteArray(AudioPlayer.bufferSize)

    val runnable = Runnable {
        while (_uIState.value.isPlaying) {
            val read = fileInputStream?.read(byteArray)
            if (read != -1) {
                AudioPlayer.onDataReceiver(byteArray)
            } else {
                _uIState.value =
                    _uIState.value.copy(false)
            }
        }
    }

    fun playStop() {
        _uIState.value =
            _uIState.value.copy(false)
        AudioPlayer.stopPlay()
        fileInputStream?.close()
        thread?.join()
        thread?.interrupt()
    }

    fun setFileName(fileName: String) {
        _uIState.value =
            _uIState.value.copy(fileName = fileName)
    }
}