package com.niraj.alertly.presentation.screens.LoginScreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.niraj.alertly.BuildConfig
import com.niraj.alertly.R
import com.niraj.alertly.data.LoginState
import com.niraj.alertly.ui.theme.rubikFontFamily
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import com.stevdzasan.onetap.rememberOneTapSignInState
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navigateToHome: () -> Unit
) {
    val isSigningInState by remember {
        mutableStateOf(false)
    }
    val oneTapSignInState = rememberOneTapSignInState()
    val loginViewModel: LoginViewModel = viewModel()
    val loginState by loginViewModel.loginState.collectAsState()
    val ctx = LocalContext.current

    LaunchedEffect(key1 = loginState) {
        if(loginState == LoginState.SUCCESS) {
            loginViewModel.saveToken(ctx)
            navigateToHome()
        }
    }

    Surface (
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "ALERTLY",
                fontFamily = rubikFontFamily,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Get notified instantly",
                fontFamily = rubikFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(Modifier.height(30.dp))
            SignInWithGoogleButton(loginState = loginState) {
                oneTapSignInState.open()
            }
        }
    }
    val scope = rememberCoroutineScope()
    OneTapSignInWithGoogle(
        state = oneTapSignInState,
        clientId = BuildConfig.CLIENT_ID,
        onTokenIdReceived = {
            scope.launch {
                loginViewModel.Login(it)
            }
        },
        onDialogDismissed = {
            Log.d("token", "Dialog Dismissed: $it")
        }
    )
}

@Composable
fun SignInWithGoogleButton(
    loginState: LoginState,
    onClick: () -> Unit
) {
    when (loginState) {
        LoginState.PROGRESS -> {
            CircularProgressIndicator(modifier = Modifier.height(50.dp))
        }
        LoginState.FAILED -> {
            Button(
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            )
            {
                Text("Failed", fontFamily = rubikFontFamily)
            }
        }
        LoginState.SUCCESS -> {
            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            )
            {
                Text("Success", fontFamily = rubikFontFamily)
            }
        }
        else -> {
            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            )
            {
                Text("Sign in with Google", fontFamily = rubikFontFamily)
            }
        }
    }
}