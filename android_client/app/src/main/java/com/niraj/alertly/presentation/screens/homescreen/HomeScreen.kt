package com.niraj.alertly.presentation.screens.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.niraj.alertly.data.GroupData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val homeViewModel: HomeViewModel = viewModel()
    val groupList by homeViewModel.groupList.collectAsState()
    var showAlertDialog by remember {
        mutableStateOf(false)
    }
    var newGroupName by remember {
        mutableStateOf("")
    }
    var newGroupDescription by remember {
        mutableStateOf("")
    }
    fun dismissAlertdialog() {
        showAlertDialog = false
        newGroupName = ""
        newGroupDescription = ""
    }

    var showJoinGroupAlertDialog by remember {
        mutableStateOf(false)
    }
    var accessTokenToJoinGroup by remember {
        mutableStateOf("")
    }


    fun dismissJoinAlertDialog() {
        showJoinGroupAlertDialog = false
        accessTokenToJoinGroup = ""
    }


    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Alertly")
                    },
                    actions = {
                        IconButton(onClick = {
                            showAlertDialog = true
                        }) {
                            Icon(imageVector = Icons.Default.Create, contentDescription = "Create group")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        showJoinGroupAlertDialog = true
                    },
                    shape = FloatingActionButtonDefaults.extendedFabShape
                ) {
                    Row (
                        modifier = Modifier.padding(horizontal = 15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(imageVector = Icons.Default.Create, contentDescription = "Join Group Button")
                        Text("Join Group")
                    }
                }
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item {
                    Text("Groups", style = MaterialTheme.typography.displaySmall, modifier = Modifier.padding(horizontal = 10.dp))
                }
                items(groupList.size) {
                    GroupItem(groupData = groupList[it])
                }
            }
        }
    }

    if(showAlertDialog) {
        AlertDialog(
            onDismissRequest = { dismissAlertdialog() },
            icon = {
                   Icon(imageVector = Icons.Default.Create, contentDescription = "create group")
            },
            title = {
                    Text("Create Group")
            },
            text = {
                   Column (
                       verticalArrangement = Arrangement.spacedBy(15.dp)
                   ){
                       TextField(
                           value = newGroupName,
                           onValueChange = {
                               newGroupName = it
                           },
                           placeholder = {
                               Text("Group Name")
                           }
                       )
                       TextField(
                           value = newGroupDescription,
                           onValueChange = {
                               newGroupDescription = it
                           },
                           placeholder = {
                               Text("Group Description")
                           }
                       )
                   }
            },
            confirmButton = {
                Button(
                    onClick = {
                        homeViewModel.createGroup(newGroupName, newGroupDescription)
                        dismissAlertdialog()
                    }
                ) {
                    Text("create")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    dismissAlertdialog()
                }) {
                    Text(text = "cancel")
                }
            }
        )
    }

    if(showJoinGroupAlertDialog) {
        AlertDialog(
            onDismissRequest = {
                dismissJoinAlertDialog()
            },
            confirmButton = {
                Button(
                    onClick = {
                        homeViewModel.joinGroup(accessTokenToJoinGroup)
                        dismissJoinAlertDialog()
                    }
                ) {
                    Text("Join Group")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dismissJoinAlertDialog()
                    }
                ) {
                    Text("cancel")
                }
            },
            icon = {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Join Group")
            },
            title = {
                Text("Join Group")
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    TextField(
                        value = accessTokenToJoinGroup,
                        onValueChange = {
                            if(it.isDigitsOnly() && it.length < 7) {
                                accessTokenToJoinGroup = it
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        placeholder = {
                            Text("Access Code")
                        }
                    )
                }
            }
        )
    }
}

@Composable
fun GroupItem(
    groupData: GroupData
) {
    Card (
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(CornerSize(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row {
            AsyncImage(
                model = "https://source.unsplash.com/random?${(0..100).random()}",
                contentDescription = null,
                modifier = Modifier
                    .padding(10.dp)
                    .clip(
                        RoundedCornerShape(CornerSize(9.dp))
                    )
                    .size(86.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(6.dp))
            Column(
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, end = 10.dp)
            ) {
                Text(
                    text = "${groupData.group_name}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = groupData.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
        }
    }
}