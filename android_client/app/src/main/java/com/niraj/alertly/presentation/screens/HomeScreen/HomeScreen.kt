package com.niraj.alertly.presentation.screens.HomeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.niraj.alertly.data.GroupData
import com.niraj.alertly.presentation.screens.LoginScreen.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {


    val homeViewModel: HomeViewModel = viewModel()
    val groupList by homeViewModel.groupList.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text("Alertly")
                })
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
//            val imageLoader = ImageLoader.Builder(LocalContext.current).diskCache {
//
//            }
//            AsyncImage(model = , contentDescription = )
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