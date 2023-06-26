package com.sar.sarandroidfunctions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore

/**
 * 从URI获取FilePath
 */
fun getFilePathFromURI(context: Context, contentUri: Uri): String {
    var filePath = ""
    context.contentResolver.query(
        contentUri,
        arrayOf(MediaStore.Images.Media.DATA),
        null,
        null,
        null
    )?.use {
        val columnIndex = it.getColumnIndex(MediaStore.Images.Media.DATA)
        it.moveToFirst()
        if (columnIndex != -1) {
            filePath = it.getString(columnIndex)
        }
    }
    return filePath
}

/**
 * 从图库选择图片的Intent
 */
val selectPictureIntent = Intent(
    Intent.ACTION_PICK,
    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
)