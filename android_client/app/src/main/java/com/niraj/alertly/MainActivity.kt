package com.niraj.alertly

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.rememberNavController
import com.niraj.alertly.navigation.SetupNavGraph
import com.niraj.alertly.presentation.screens.groupscreen.GroupScreen
import com.niraj.alertly.ui.theme.AlertlyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("AuthToken")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlertlyTheme {
                val ctx = LocalContext.current
                val navController = rememberNavController()
//                deleteToken(ctx)
                SetupNavGraph(startDestination = getStartDestination(ctx), navController = navController)
//                GroupScreen()
            }
        }
    }
}

private fun getStartDestination(ctx: Context) : String {
    return runBlocking {
        val token = loadToken(ctx)
        if(token == "-1") {
            "login_screen"
        } else {
            "home_screen"
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

//fun deleteToken(ctx: Context) {
//    runBlocking {
//        val prefKey = stringPreferencesKey("token")
//        ctx.dataStore.edit {
//            it[prefKey] = "-1"
//        }
//    }
//}