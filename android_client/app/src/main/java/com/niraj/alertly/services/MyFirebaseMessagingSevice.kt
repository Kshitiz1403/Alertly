package com.niraj.alertly.services

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.compose.ui.res.integerArrayResource
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.niraj.alertly.MainActivity
import com.niraj.alertly.utils.Constants.ALERT_TITLE
import com.niraj.alertly.utils.Constants.ALERT_DESCRIPTION
import com.niraj.alertly.utils.Constants.ALERT_SEVERITY


/*
{
    "title":"alert 1",
    "description":"some desc",
    "severity":"danger"
}
 */
class MyFirebaseMessagingSevice: FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("FIRE", "HERE")
        Log.d("FIRE", message.data.toString())
        Log.d("FIRE", message.toString())



        if(!(message.data.containsKey(ALERT_TITLE)
                    && message.data.containsKey(ALERT_DESCRIPTION)
                    && message.data.containsKey(ALERT_SEVERITY)
             )
        ) {
            return
        }
        Log.d("FIRE", "HERE")
        Log.d("FIRE", "title: ${message.data[ALERT_TITLE]} \n description: ${message.data[ALERT_DESCRIPTION]} \n severity: ${message.data[ALERT_SEVERITY]}")
        Log.d("FIRE", message.toString())

        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.putExtra(ALERT_TITLE, message.data[ALERT_TITLE])
        intent.putExtra(ALERT_DESCRIPTION, message.data[ALERT_DESCRIPTION])
        intent.putExtra(ALERT_SEVERITY, message.data[ALERT_SEVERITY])

        startActivity(intent)

    }
}