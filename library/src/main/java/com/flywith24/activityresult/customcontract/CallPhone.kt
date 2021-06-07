package com.flywith24.activityresult.customcontract

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

class CallPhone : ActivityResultContract<String?, Intent?>() {

    override fun createIntent(context: Context, input: String?): Intent {
        return Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:$input"))
    }


    override fun parseResult(resultCode: Int, intent: Intent?): Intent? {
        intent?.let {
            return intent
        } ?: kotlin.run {
            return null
        }
    }
}