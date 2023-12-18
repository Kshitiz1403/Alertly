package com.niraj.alertly.presentation.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niraj.alertly.BuildConfig
import com.niraj.alertly.R
import com.niraj.alertly.ui.theme.rubikFontFamily
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import com.stevdzasan.onetap.rememberOneTapSignInState

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoginScreen() {
    val isSigningInState by remember {
        mutableStateOf(false)
    }
    val oneTapSignInState = rememberOneTapSignInState()

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
            SignInWithGoogleButton(isLoading = isSigningInState) {
                oneTapSignInState.open()
            }
        }
    }

    OneTapSignInWithGoogle(
        state = oneTapSignInState,
        clientId = BuildConfig.CLIENT_ID,
        onTokenIdReceived = {
            Log.d("token", it)
        },
        onDialogDismissed = {
            Log.d("token", "Dialog Dismissed: $it")
        }
    )
}

@Composable
fun SignInWithGoogleButton(
    isLoading: Boolean,
    onClick: () -> Unit
) {
    if(!isLoading) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp)
        )
        {
            Text("Sign in with Google", fontFamily = rubikFontFamily)
        }
    } else {
        CircularProgressIndicator(modifier = Modifier.height(50.dp))
    }

}