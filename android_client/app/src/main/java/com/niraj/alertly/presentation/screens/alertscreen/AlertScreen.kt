package com.niraj.alertly.presentation.screens.alertscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.niraj.alertly.R
import com.niraj.alertly.data.groupalerts.Alert
import com.niraj.alertly.ui.theme.rubikFontFamily
import com.niraj.alertly.utils.containerColorMap
import com.niraj.alertly.utils.contentColorMap
import java.util.NavigableMap

@Preview
@Composable
fun AlertScreen (
    alert: String = "Test",
    description: String = "This is a severe warning, please stay inside your house",
    severity: String = "Danger",
    navigateToHome: () -> Unit = {}
){
    Surface (
        modifier = Modifier.fillMaxSize(),
        color = containerColorMap[severity]!!
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(30.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.circle_exclamation_solid),
                contentDescription = null,
                modifier = Modifier.size(120.dp, 120.dp)
            )
            Text(
                text = alert,
                style = MaterialTheme.typography.displayLarge,
                fontFamily = rubikFontFamily,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = severity,
                style = MaterialTheme.typography.displayMedium,
                fontFamily = rubikFontFamily,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
                style = MaterialTheme.typography.headlineMedium,
                fontFamily = rubikFontFamily,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(10.dp))
            Button(
                onClick = {
                    navigateToHome()
                },
                modifier = Modifier
                    .height(60.dp)
                    .width(200.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(
                        alpha = 0.6f
                    ),
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "STOP",
                    style = MaterialTheme.typography.headlineSmall,
                    fontFamily = rubikFontFamily,
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}