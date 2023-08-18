package com.sar.sarandroidfunctions.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sar.sarandroidfunctions.AudioPlayer
import com.sar.sarandroidfunctions.AudioRecorder

class AudioRecordAndPlayAtSameTimeViewModel : ViewModel() {
    private val _uIState = mutableStateOf(AudioRecordAndPlayAtSameTimeUIState(false))
    fun getUIState(): State<AudioRecordAndPlayAtSameTimeUIState> {
        return _uIState
    }

    fun start() {
        _uIState.value =
            _uIState.value.copy(isRunning = true)
        AudioRecorder.startRecord {
            AudioPlayer.onDataReceiver(it)
        }
        AudioPlayer.startPlay()
    }

    fun stop() {
        _uIState.value =
            _uIState.value.copy(isRunning = false)
        AudioRecorder.stopRecord()
        AudioPlayer.stopPlay()
    }
}