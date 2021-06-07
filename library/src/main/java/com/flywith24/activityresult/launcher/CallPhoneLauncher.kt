package com.flywith24.activityresult

import android.Manifest
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.LifecycleOwner
import com.flywith24.activityresult.customcontract.CallPhone
import com.flywith24.activityresult.permission.PermissionLauncher

/**
 * @author yyz (杨云召)
 * @date   2020/11/27
 * time   16:25
 * description
 */
class CallPhoneLauncher :
    BasePictureLauncher<String?, Intent?>(CallPhone()) {
    var onError: (resultCode: String) -> Unit = {}
    var onSuccess: (uri: Intent?) -> Unit = {}
    val permission by lazy { PermissionLauncher() }

    override fun onCreate(owner: LifecycleOwner) {
        if (owner is ComponentActivity) {
            owner.lifecycle.addObserver(permission)
        }
        super.onCreate(owner)
    }

    fun lunch(
        phoneNumber: String,
        onError: (message: String) -> Unit = {},
        onSuccess: (uri: Intent?) -> Unit = {}
    ) {
        this.onError = onError
        this.onSuccess = onSuccess

        camera.lunch(Manifest.permission.CALL_PHONE) {
            granted = {
                launcher.launch(phoneNumber)
            }
            denied = {
                onError.invoke("Call Phone permission denied")
            }
            explained = {
                onError.invoke("Call Phone permission denied")
            }
        }
    }

    override fun onActivityResult(result: Intent?) {
        Log.e("FUCKING SHIT ", "GGWP $result")
        if (result != null) {
            onSuccess.invoke(result)
        } else {
            onError.invoke("result is null")
        }
    }
}