package com.flywith24.request

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.flywith24.activityresult.ActivityResultLauncher
import com.flywith24.activityresult.launcher.*
import com.flywith24.activityresult.permission.MultiPermissionLauncher


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val pictureLauncher by lazy { TakePictureLauncher() }
    private val previewLauncher by lazy { TakePicturePreviewLauncher() }
    private val videoLauncher by lazy { TakeVideoLauncher() }
    private val contactLauncher by lazy { PickContactLauncher() }
    private val launcher by lazy { ActivityResultLauncher() }
    private val multiPermissionLauncher by lazy { MultiPermissionLauncher() }
    private val callPhoneLauncher by lazy { CallPhoneLauncher() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(pictureLauncher)
        lifecycle.addObserver(previewLauncher)
        lifecycle.addObserver(videoLauncher)
        lifecycle.addObserver(contactLauncher)
        lifecycle.addObserver(launcher)
        lifecycle.addObserver(multiPermissionLauncher)
        lifecycle.addObserver(callPhoneLauncher)
    }

    /**
     * 拍照，返回图片路径
     */
    fun takePictureClick(view: View) {
        pictureLauncher.lunch() { path ->
            Log.i(TAG, "take picture success path = $path")
        }
    }

    /**
     * 拍照，返回图片 bitmap
     */
    fun takePicturePreviewClick(view: View) {
        previewLauncher.lunch { bitmap ->
            findViewById<ImageView>(R.id.image).setImageBitmap(bitmap)
        }
    }

    /**
     * 拍视频，返回视频 path
     */
    fun takeVideoClick(view: View) {
        videoLauncher.lunch { path ->
            Log.i(TAG, "take video success path = $path")
        }
    }

    /**
     * 选择联系人，返回 uri
     */
    fun pickContactClick(view: View) {
        contactLauncher.lunch { uri ->
            Log.i(TAG, "pick contact success uri = $uri")
        }
    }

    /**
     * startActivityForResult
     */
    fun startActivityForResultClick(view: View) {
/*        // 使用默认 intent
        launcher.lunch<SecondActivity> {
            val location = it?.getStringExtra("Configs.LOCATION_RESULT")
            Log.i(TAG, "startActivityForResultClick: $location")
        }*/
        launcher.lunch<SecondActivity>(
            setIntent = {
                //配置请求 intent
                it.putExtra("Configs.LOCATION_RESULT", "value form Main")
            },
            onSuccess = {
                val location = it?.getStringExtra("Configs.LOCATION_RESULT")
                Log.i(TAG, "startActivityForResultClick: $location")
            }
        )
    }

    fun requestMultiPermissions(view: View) {
        multiPermissionLauncher.lunch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_CONTACTS
            )
        ) {
            Log.i(TAG, "requestMultiPermissions: 全部通过")
        }
    }

    /**
     * Call Phone
     */
    fun callPhone(view: View) {
        callPhoneLauncher.lunch(phoneNumber = "09222222222")
    }


    /**
     * 拍照，返回图片 Call Phone
     */
    fun requestReadWrite(view: View) {
        multiPermissionLauncher.lunch(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            Log.i(TAG, "Read Write Permission Granted")
        }
    }


    /**
     * 拍照，返回图片 Call Phone
     */
    fun requestLocation(view: View) {
        multiPermissionLauncher.lunch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            Log.i(TAG, "Read Write Permission Granted")
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}