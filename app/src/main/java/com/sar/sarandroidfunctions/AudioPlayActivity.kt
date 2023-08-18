package com.sar.sarandroidfunctions

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sar.sarandroidfunctions.ui.AudioPlayUIState
import com.sar.sarandroidfunctions.ui.AudioPlayViewModel
import com.sar.sarandroidfunctions.ui.theme.SarAndroidFunctionsTheme


class AudioPlayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SarAndroidFunctionsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: AudioPlayViewModel by viewModels()
                    val uiState = viewModel.getUIStateFlow().value
                    AudioPlayPage(uiState, {
                        if (uiState.isPlaying) {
                            viewModel.playStop()
                        } else {
                            viewModel.playStart()
                        }
                    }, {
                        viewModel.setFileName(it)
                    })
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            startActivity(Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioPlayPage(
    uiState: AudioPlayUIState,
    onButtonClick: () -> Unit,
    onFileNameChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column {
            TextField(
                singleLine = true,
                label = { Text("Enter File Name") },
                value = uiState.fileName,
                onValueChange = onFileNameChange
            )

            Button(onClick = onButtonClick, enabled = uiState.fileName.isNotEmpty()) {
                Text(
                    text = if (uiState.isPlaying) "停止播放" else "开始播放",
                    modifier = modifier
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AudioPlayPagePreview() {
    SarAndroidFunctionsTheme {
        AudioPlayPage(AudioPlayUIState(false), {}, {})
    }
}