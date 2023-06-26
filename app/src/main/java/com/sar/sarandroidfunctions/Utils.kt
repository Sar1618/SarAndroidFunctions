package com.sar.sarandroidfunctions

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import java.io.FileNotFoundException

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
 * 按目标宽高缩小Uri图片后返回Bitmap
 */
fun minifyBitmapFromURI(context: Context, contentUri: Uri, dstWidth: Int, dstHeight: Int): Bitmap? {
    var bitmap: Bitmap? = null
    try {
        context.contentResolver.openInputStream(contentUri)
    } catch (ignore: FileNotFoundException) {
        null
    }?.use {
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeStream(it, null, bmOptions)
        bmOptions.inSampleSize = calculateInSampleSize(bmOptions, dstWidth, dstHeight)
        bmOptions.inJustDecodeBounds = false
        try {
            context.contentResolver.openInputStream(contentUri)
        } catch (ignore: FileNotFoundException) {
            null
        }?.use { inputStream ->
            bitmap = BitmapFactory.decodeStream(inputStream, null, bmOptions)
        }
    }
    return bitmap
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val (height: Int, width: Int) = options.run { outHeight to outWidth }
    var inSampleSize = 1
    if (height > reqHeight || width > reqWidth) {
        val halfHeight: Int = height / 2
        val halfWidth: Int = width / 2
        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}

/**
 * 从图库选择图片的Intent
 */
val selectPictureIntent = Intent(
    Intent.ACTION_PICK,
    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
)