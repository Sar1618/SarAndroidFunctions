package com.sar.sarandroidfunctions

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sar.sarandroidfunctions.databinding.ActivityLocalPictureSelectorBinding

class LocalPictureSelectorActivity : AppCompatActivity() {
    // 用于替代startActivityForResult的语法糖api，需要在生命周期方法执行前初始化
    private val selectImageForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.apply {
                    minifyBitmapFromURI(
                        this@LocalPictureSelectorActivity,
                        this,
                        dataBinding.ivLocalPictureSelector.width,
                        dataBinding.ivLocalPictureSelector.height
                    )?.apply {
                        dataBinding.ivLocalPictureSelector.setImageBitmap(this)
                    }
//                    dataBinding.ivLocalPictureSelector.setImageURI(this)
                    Toast.makeText(
                        this@LocalPictureSelectorActivity,
                        getFilePathFromURI(this@LocalPictureSelectorActivity, this),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    lateinit var dataBinding: ActivityLocalPictureSelectorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_local_picture_selector)
        dataBinding.ivLocalPictureSelector.setOnClickListener {
            selectImageForResult.launch(selectPictureIntent)
        }
    }
}