package com.example.ujiants

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.ujiants.geometry.SolidShape
import com.example.ujiants.navigation.LocalNavController


@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun GeometryScreen(
) {
    val navController = LocalNavController.current
    val shapeList: List<SolidShape> = listOf(
        SolidShape.Cube(description = stringResource(R.string.shape_cube_desc), imageId = R.drawable.cube),
        SolidShape.Cubeoid(description = stringResource(R.string.shape_cubeoid_desc), imageId = R.drawable.cubeoid),
        SolidShape.Sphere(description = stringResource(R.string.shape_sphere_desc), imageId = R.drawable.sphere),
        SolidShape.Cone(description = stringResource(R.string.shape_cone_desc), imageId = R.drawable.cone),
        SolidShape.Cylinder(description = stringResource(R.string.shape_cylinder_desc), imageId = R.drawable.cylinder),
        SolidShape.Pyramid(description = stringResource(R.string.shape_limas_desc), imageId = R.drawable.pyramid),
        SolidShape.OctaPrism(description = stringResource(R.string.shape_octPrism_desc), imageId = R.drawable.octagonal_prism)
    )
    var openShape by remember {
        mutableStateOf(false)
    }
    var selectedShape by remember {
        mutableIntStateOf(-1)
    }
    val backToList = {
        openShape = false
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bangun Ruang") },
                navigationIcon = {
                    IconButton(onClick = { if(openShape) backToList() else navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Navigation icon")
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) {innerPadd ->
        SharedTransitionLayout {
            AnimatedContent(targetState = openShape, label = "basic_transition") {targetState ->
                if(!targetState) {
                    GeometricContent(
                        modifier = Modifier.padding(innerPadd),
                        shapeList = shapeList,
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        onShowDetails = {shapeIndex: Int -> openShape = true; selectedShape = shapeIndex }
                    )
                } else if(selectedShape >= 0) {
                    GeometricDetail(
                        modifier = Modifier.padding(innerPadd),
                        shape = shapeList[selectedShape],
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GeometricContent(
    modifier: Modifier = Modifier,
    shapeList: List<SolidShape>,
    onShowDetails: (Int) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
) {
    LazyColumn(modifier) {
        with(sharedTransitionScope) {
            itemsIndexed(shapeList) { index,shape ->
                ListItem(
                    modifier = Modifier.fillMaxWidth(),
                    headlineContent = {
                        Text(
                            text = shape.name,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.sharedElement(
                                rememberSharedContentState(key = "shape/${shape.name}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                        )
                    },
                    supportingContent = {
                        Text(
                            text = shape.description,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.sharedElement(
                                rememberSharedContentState(key = "desc/shape/${shape.name}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                        )
                    },
                    leadingContent = {
                        Image(
                            painter = painterResource(shape.imageId),
                            contentDescription = null,
                            modifier = Modifier
                                .height(48.dp)
                                .width(48.dp)
                                .clip(MaterialTheme.shapes.small)
                                .aspectRatio(1f / 1f)
                                .sharedElement(
                                    rememberSharedContentState(key = "image/shape/${shape.name}"),
                                    animatedVisibilityScope = animatedVisibilityScope
                                ),
                            contentScale = ContentScale.Crop
                        )
                    },
                    trailingContent = {
                        IconButton(onClick = { onShowDetails(index) }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.OpenInNew,
                                contentDescription = null
                            )
                        }
                    }
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GeometricDetail(
    modifier: Modifier = Modifier,
    shape: SolidShape,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
) {
    val calculateBtnClicked = remember {  mutableStateOf(false)  }
    Column(
        modifier = modifier
            .padding(horizontal = 12.dp, vertical = 22.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        with(sharedTransitionScope) {
            Text(
                text = shape.name,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.sharedElement(
                    rememberSharedContentState(key = "shape/${shape.name}"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Image(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .aspectRatio(1f / 1f)
                    .clip(MaterialTheme.shapes.small)
                    .sharedElement(
                        rememberSharedContentState(key = "image/shape/${shape.name}"),
                        animatedVisibilityScope = animatedVisibilityScope
                    ),
                painter = painterResource(id = shape.imageId),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = shape.description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .sharedElement(
                        rememberSharedContentState(key = "desc/shape/${shape.name}"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        FormCalc(shape = shape, calcNow = calculateBtnClicked)
    }
}