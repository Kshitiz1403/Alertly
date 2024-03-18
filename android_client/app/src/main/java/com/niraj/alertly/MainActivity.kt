package com.niraj.alertly

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.integerArrayResource
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.messaging
import com.niraj.alertly.navigation.Screen
import com.niraj.alertly.navigation.SetupNavGraph
import com.niraj.alertly.ui.theme.AlertlyTheme
import com.niraj.alertly.utils.Constants.ALERT_DESCRIPTION
import com.niraj.alertly.utils.Constants.ALERT_SEVERITY
import com.niraj.alertly.utils.Constants.ALERT_TITLE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("AuthToken")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
    private fun checkActionManageOverlayPermission() {
        if(!Settings.canDrawOverlays(applicationContext)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + BuildConfig.APPLICATION_ID))
            startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        askNotificationPermission()
        checkActionManageOverlayPermission()
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("TOKEN", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.d("TOKEN", "token: " + token)
        })

//        intent.putExtra(ALERT_TITLE, "Test1")
//        intent.putExtra(ALERT_DESCRIPTION, "This is a severe warning, please stay inside your house")
//        intent.putExtra(ALERT_SEVERITY, "Normal")

        var startDestination = ""
        if(intent.hasExtra(ALERT_TITLE) && intent.hasExtra(ALERT_DESCRIPTION) && intent.hasExtra(
                ALERT_SEVERITY) && getStartDestination(applicationContext) == Screen.Home.route
        ) {
            startDestination = Screen.Alert.route
        }
        setContent {
            AlertlyTheme {
                val ctx = LocalContext.current
                val navController = rememberNavController()
                subscribeToTopic("test", ctx)
//                deleteToken(ctx)
                if(startDestination.isEmpty()) {
                    startDestination = getStartDestination(ctx)
                }

                SetupNavGraph(startDestination = startDestination, navController = navController, intent)
//                GroupScreen()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }
}

private fun getStartDestination(ctx: Context) : String {
    return runBlocking {
        val token = loadToken(ctx)
        if(token == "-1") {
           Screen.Login.route
        } else {
            Screen.Home.route
        }
    }
}

suspend fun loadToken(ctx: Context): String {
    val prefKey = stringPreferencesKey("token")
    val preference = ctx.dataStore.data.first()
    return preference[prefKey] ?: "-1"
}

suspend fun saveToken(token: String, ctx: Context){
    val prefKey = stringPreferencesKey("token")
    ctx.dataStore.edit {
        it[prefKey] = token
    }
}

fun deleteToken(ctx: Context) {
    runBlocking {
        val prefKey = stringPreferencesKey("token")
        ctx.dataStore.edit {
            it[prefKey] = "-1"
        }
    }
}

fun subscribeToTopic(topic: String, ctx: Context) {
    Firebase.messaging.subscribeToTopic(topic).addOnCompleteListener {
        Toast.makeText(ctx, "Subscribe to $topic!", Toast.LENGTH_SHORT).show()
    }
}

