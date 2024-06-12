package com.example.ujiants

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.ujiants.navigation.LocalNavController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

data class TutorVideo(val id: String, val title: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorScreen(
) {
    val navController = LocalNavController.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Video") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Navigation icon")
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        TutorScreenContent(modifier = Modifier.padding(it))
    }
}

@Composable
fun TutorScreenContent(modifier: Modifier = Modifier) {
    val ytList: List<TutorVideo> = listOf(
        TutorVideo(title = stringResource(R.string.videoTitle1), id = "q50HvRP1n4E"),
        TutorVideo(title = stringResource(R.string.videoTitle2), id = "NOsM5iEseMs"),
        TutorVideo(title = stringResource(R.string.videoTitle3), id = "X4iZvxQHoCs"),
        TutorVideo(title = stringResource(R.string.videoTitle4), id = "dLGIjlvbzdU"),
        TutorVideo(title = stringResource(R.string.videoTitle5), id = "ZF9BEPhAAUg"),
        TutorVideo(title = stringResource(R.string.videoTitle6), id = "PljECBkHQpg"),
        TutorVideo(title = stringResource(R.string.videoTitle7), id = "Wmwe1fmR1SM")
    )
    Column(
        modifier = modifier
            .padding(horizontal = 12.dp, vertical = 20.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        YoutubePlayerList(youtubeCards = ytList, lifecycleOwner = LocalLifecycleOwner.current)
    }
}

@Composable
fun YoutubePlayerList(
    youtubeCards: List<TutorVideo>,
    lifecycleOwner: LifecycleOwner
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        items(youtubeCards) {tutorVid ->
            Text(text = tutorVid.title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(start = 8.dp,end = 8.dp, bottom = 4.dp))
            Spacer(modifier = Modifier.height(5.dp))
            AndroidView(factory = {context ->
                YouTubePlayerView(context = context).apply {
                    lifecycleOwner.lifecycle.addObserver(this)

                    addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.cueVideo(videoId = tutorVid.id, 0f)
                        }
                    })
                }
            },
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clip(MaterialTheme.shapes.small)
                    .fillMaxWidth()
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TutorDefaultPreview() {
    TutorScreen()
}