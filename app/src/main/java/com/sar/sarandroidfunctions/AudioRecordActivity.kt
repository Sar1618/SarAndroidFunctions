package com.sar.sarandroidfunctions

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sar.sarandroidfunctions.ui.AudioRecordUIState
import com.sar.sarandroidfunctions.ui.AudioRecordViewModel
import com.sar.sarandroidfunctions.ui.theme.SarAndroidFunctionsTheme


class AudioRecordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SarAndroidFunctionsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: AudioRecordViewModel by viewModels()
                    val uiState = viewModel.getAudioRecordUIStateFlow().value
                    AudioRecordPage(uiState, {
                        if (uiState.isRecording) {
                            viewModel.recordStop()
                        } else {
                            viewModel.recordStart()
                        }
                    })
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            startActivity(Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION))
        }
    }
}

@Composable
fun AudioRecordPage(
    uiState: AudioRecordUIState,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column {
            Button(onClick = onButtonClick) {
                Text(
                    text = if (uiState.isRecording) "停止录制" else "开始录制",
                    modifier = modifier
                )
            }
            if (!uiState.filePath.isNullOrEmpty()) {
                Text(text = uiState.filePath, modifier = modifier)
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun GreetingPreview() {
    SarAndroidFunctionsTheme {
        AudioRecordPage(AudioRecordUIState(false), {})
    }
}