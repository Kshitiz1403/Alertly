package com.niraj.alertly.presentation.screens.groupscreen

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ChipColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.niraj.alertly.R
import com.niraj.alertly.data.GroupData
import com.niraj.alertly.data.groupalerts.Alert
import com.niraj.alertly.data.groupalerts.AlertLoadingState
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupScreen(
    groupId: Int = 1,
    navigateToHome: () -> Unit
) {

    val groupScreenViewModel: GroupScreenViewModel = viewModel()
    groupScreenViewModel.setGroupId(groupId)

    val groupList by groupScreenViewModel.groupList.collectAsState()

    val group by remember {
        derivedStateOf {
            if(groupList.isEmpty()) GroupData()
            else groupList[groupId-1]
        }
    }
    
    var showCreateAlertDialog by remember {
        mutableStateOf(false)
    }
    var alertTitle by remember {
        mutableStateOf("")
    }
    var alertDescription by remember {
        mutableStateOf("")
    }
    var alertSeverity by remember {
        mutableStateOf("Normal")
    }

    val showAlertButton by remember {
        derivedStateOf {
            if(alertTitle.isNotEmpty() && alertDescription.isNotEmpty() && alertSeverity.isNotEmpty()) {
                true
            } else {
                false
            }
        }
    }
    
    fun dismissCreateAlertDialog() {
        showCreateAlertDialog = false
        alertTitle = ""
        alertDescription = ""
        alertSeverity = "Normal"
    }

    var showAccessCodeDialog by remember {
        mutableStateOf(false)
    }
    Surface (
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold (
            topBar = {
                TopAppBar (
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navigateToHome()
                            }
                        ) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back")
                        }
                    },
                    title = {
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ){
                           AsyncImage(
                               model = "https://source.unsplash.com/random?${(0..100).random()}",
                               contentDescription = "group image",
                               modifier = Modifier
                                   .size(40.dp)
                                   .clip(CircleShape),
                               contentScale = ContentScale.Crop
                           )
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(
                                text = group.group_name,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    },
                    actions = {
                        // Access Code Button
                        IconButton(
                            onClick = {
                                showAccessCodeDialog = true
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = "Access code button")
                        }
                        IconButton(
                            onClick = {
                            /*TODO*/
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Leave Group",
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = {
                        showCreateAlertDialog = true
                    },
                    contentColor = MaterialTheme.colorScheme.secondaryContainer,
                    containerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                ) {
                    Icon(painterResource(R.drawable.circle_exclamation_solid), contentDescription = "Create Alert Icon")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Alert")
                }
            }
        ){ paddingValues ->
            GroupScreenContent(paddingValues, groupScreenViewModel, groupId)
        }
    }

    val chipSelectedColor = AssistChipDefaults.assistChipColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        labelColor = MaterialTheme.colorScheme.onPrimaryContainer
    )
    val chipNotSelectedColor = AssistChipDefaults.assistChipColors(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        labelColor = MaterialTheme.colorScheme.onSecondaryContainer
    )
    val severities: List<String> = listOf("Normal", "Elevated", "Danger")
    val containerColorMap: Map<String, Color> = mapOf(
        Pair("Normal", Color(0xFFCBFFA9)),
        Pair("Elevated", Color(0xFFFFFEC4)),
        Pair("Danger", Color(0xFFFF9B9B)),
    )
    val contentColorMap: Map<String, Color> = mapOf(
        Pair("Normal", Color.Black),
        Pair("Elevated", Color.Black),
        Pair("Danger", Color.White),
    )

    if(showCreateAlertDialog) {
        AlertDialog(
            onDismissRequest = { 
                dismissCreateAlertDialog()                               
            }, 
            confirmButton = {
                Button(
                    onClick = {
                        groupScreenViewModel.createAlert(alertTitle, alertDescription, alertSeverity)
                        dismissCreateAlertDialog()
                    },
                    enabled = showAlertButton,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = containerColorMap[alertSeverity]!!,
                        contentColor = contentColorMap[alertSeverity]!!
                    )
                ) {
                    Icon(painter = painterResource(id = R.drawable.circle_exclamation_solid), contentDescription = "Alert")
                    Spacer(Modifier.width(10.dp))
                    Text("Alert")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dismissCreateAlertDialog()
                    }
                ) {
                    Text("cancel")
                }
            },
            icon = {
                Icon(painter = painterResource(id = R.drawable.circle_exclamation_solid), contentDescription = "Alert Icon")
            },
            title = {
                Text("Alert")
            },
            text = {
                Column (
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = alertTitle, 
                        onValueChange = {
                            alertTitle = it
                        },
                        placeholder = {
                            Text(text = "Alert title")
                        }
                    )
                    TextField(
                        value = alertDescription,
                        onValueChange = {
                            alertDescription = it
                        },
                        placeholder = {
                            Text(text = "Alert description")
                        }
                    )
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        severities.forEach { level ->
                            ElevatedAssistChip(
                                onClick = {
                                    alertSeverity = level
                                },
                                label = {
                                        Text(text = level)
                                },
                                modifier = Modifier.wrapContentSize(),
                                colors = if (alertSeverity == level) chipSelectedColor else chipNotSelectedColor
                            )
                        }
                    }
                }
            }
        )
    }

    val accessToken by groupScreenViewModel.accessToken.collectAsState()
    val ctx = LocalContext.current

    fun dismissAccessCodeDialog() {
        showAccessCodeDialog = false
    }
    if(showAccessCodeDialog) {
        AlertDialog(
            onDismissRequest = { dismissAccessCodeDialog() },
            confirmButton = {
                Button(onClick = { dismissAccessCodeDialog() }) {
                    Text("Exit")
                }
            },
            title = {Text(text = "Access Token")},
            text = {
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if(accessToken.isEmpty()) {
                        CircularProgressIndicator()
                    } else {
                        Text(accessToken, style = MaterialTheme.typography.displayLarge)
                        Spacer(Modifier.height(20.dp))
                        Row (
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ){
                            TextButton(onClick = {
                                groupScreenViewModel.getAccessToken()
                            }) {
                                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                                Spacer(modifier = Modifier.width(5.dp))
                                Text("Refresh")
                            }
                            TextButton(onClick = {
                                val sendIntent: Intent = Intent().apply{
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, "Join the alert group: ${group.group_name} using the access code: \n$accessToken")
                                    type = "text/plain"
                                }
                                val shareIntent = Intent.createChooser(sendIntent, null)
                                ctx.startActivity(shareIntent)
                            }) {
                                Icon(imageVector = Icons.Default.Share, contentDescription = null)
                                Spacer(modifier = Modifier.width(5.dp))
                                Text("Share")
                            }
                        }
                    }
                }
            }
        )
    }

}

@Composable
fun GroupScreenContent(
    paddingValues: PaddingValues,
    groupScreenViewModel: GroupScreenViewModel,
    groupId: Int
) {
    val alertList by groupScreenViewModel.alertList.collectAsState()
    val loadingState by groupScreenViewModel.alertLoadingState.collectAsState()

    Surface(
        Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        when (loadingState) {
            AlertLoadingState.LOADING -> {
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(50.dp, 50.dp)
                    )
                    Spacer(Modifier.height(20.dp))
                    Text("Loading Alerts...")
                }
            }
            AlertLoadingState.FAILED -> {
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("😔", style = MaterialTheme.typography.displayLarge)
                    Spacer(Modifier.height(20.dp))
                    Text("Failed to load Alerts")
                    Spacer(Modifier.height(20.dp))
                    Button(
                        onClick = {
                            groupScreenViewModel.getGroupAlerts()
                        },
                        shape = RoundedCornerShape(CornerSize(40.dp)),
                        modifier = Modifier
                            .height(60.dp)
                            .fillMaxWidth(0.7f)
                    ) {
                        Text("Re-Load Alerts")
                    }
                }
            }
            else -> {
                LazyColumn (
                    verticalArrangement = Arrangement.Bottom
                ) {
                    items(alertList.size) {
                        AlertComponent(alert = alertList[it])
                        Spacer(Modifier.height(20.dp))
                    }
                    item {
                        Spacer(Modifier.height(65.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun AlertComponent(
    alert: Alert
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 20.dp)
    ){
        Card (
            Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(CornerSize(16.dp)),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            elevation = CardDefaults.cardElevation()
        ) {
            Row (

            ) {
                AsyncImage(
                    model = "https://source.unsplash.com/random?${(0..100).random()}",
                    contentDescription = null,
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(
                            RoundedCornerShape(CornerSize(14.dp))
                        )
                        .size(80.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, end = 10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = alert.title.replaceFirstChar {
                                 it.titlecase(
                                    Locale.ROOT
                                 )
                            },
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = (alert.sent_at.substring(11..18)).replaceFirstChar {
                                it.titlecase(
                                    Locale.ROOT
                                )
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = alert.severity,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = "~ ${alert.sender_name}",
                            style = MaterialTheme.typography.bodySmall.copy (
                                textAlign = TextAlign.End
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = alert.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                }
            }
        }
    }
}
