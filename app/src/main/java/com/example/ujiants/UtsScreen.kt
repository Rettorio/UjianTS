package com.example.ujiants

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ujiants.navigation.GeometryList
import com.example.ujiants.navigation.LocalNavController
import com.example.ujiants.navigation.Profile
import com.example.ujiants.navigation.Tutor
import com.example.ujiants.ui.theme.UjianTSTheme
import kotlinx.coroutines.launch

data class CardContent(
    var imageId: Int,
    val onClick: () -> Unit = {},
    val titleId: Int,
    val descId: Int
)

@Composable
fun HomeScreen() {
    Surface{
            HomeScreenContent(modifier = Modifier
                .fillMaxHeight())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    val geometCard = CardContent(
        titleId = R.string.geometry_title,
        descId = R.string.geometry_desc,
        imageId = R.drawable.geometry_bg,
        onClick = { navController.navigate(GeometryList) }
    )
    val tutorCard = CardContent(
            titleId = R.string.tutorTitle,
            descId = R.string.tutor_desc,
            imageId = R.drawable.tutor_image,
            onClick = { navController.navigate(Tutor) }
        )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 22.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TooltipBox(
                positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                tooltip = {
                          PlainTooltip {
                              Text("Profile")
                          }
                },
                state = rememberTooltipState()
            ) {
                FilledTonalIconButton(onClick = { navController.navigate(Profile) }) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = stringResource(R.string.appTitle), style = MaterialTheme.typography.displayMedium)
        Spacer(modifier = Modifier.height(14.dp))
        AppBadge()
        Spacer(modifier = Modifier.height(28.dp))
        ImageCard(
            title = stringResource(geometCard.titleId),
            description = stringResource(geometCard.descId),
            imageResource = geometCard.imageId,
            onClick = geometCard.onClick
        )
        Spacer(modifier = Modifier.height(24.dp))
        ImageCard(
            title = stringResource(tutorCard.titleId),
            description = stringResource(tutorCard.descId),
            imageResource = tutorCard.imageId,
            onClick = tutorCard.onClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UtsScreenPreview() {
    UjianTSTheme {
        HomeScreen()
    }
}

@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    imageResource: Int,
    title: String,
    description: String
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = MaterialTheme.shapes.large,
        onClick = { onClick() }
    ) {
        Image(painter = painterResource(imageResource), contentDescription = null, modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .fillMaxWidth()
            .heightIn(max = 180.dp)
            .aspectRatio(3f / 1.5f),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.padding(12.dp)
            ) {
            Text(text = title,style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = description,style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageCardPreview() {
    ImageCard(
        title = stringResource(R.string.geometry_title),
        description = stringResource(R.string.geometry_desc),
        imageResource = R.drawable.geometry_bg,
        onClick = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBadge() {
    val tooltipState = rememberTooltipState()
    val scope = rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TooltipBox(
            positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
            tooltip = {
                      PlainTooltip {
                          Text(text = "App made with 💖 by myself :)")
                      }
            }, state = tooltipState) {}
        FilledTonalButton(
            onClick = { scope.launch { tooltipState.show() } }
        ) {
            Icon(imageVector = Icons.Outlined.Android, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(R.string.appSubject), style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Preview(showBackground = true, heightDp = 300, widthDp = 300)
@Composable
fun TooltipPreview() {
    UjianTSTheme {
        Surface {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                AppBadge()
            }
        }
    }
}