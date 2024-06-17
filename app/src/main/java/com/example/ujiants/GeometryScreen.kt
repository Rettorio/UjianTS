package com.example.ujiants

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material.icons.outlined.DensityLarge
import androidx.compose.material.icons.outlined.Square
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ujiants.geometry.SolidShape
import com.example.ujiants.navigation.LocalNavController
import com.example.ujiants.ui.theme.UjianTSTheme
import java.math.RoundingMode
import java.text.DecimalFormat


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
    val areaCalc = remember { mutableDoubleStateOf(shape.area()) }
    val volumeCalc = remember { mutableDoubleStateOf(shape.volume()) }
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
        Column(
            modifier = Modifier.width(300.dp)
        ) {
            ShapeBadge(shape = shape, expanded = calculateBtnClicked, area = areaCalc.doubleValue, volume = volumeCalc.doubleValue)
            Spacer(Modifier.height(8.dp))
            FormCalc(shape = shape, calcNow = calculateBtnClicked, areaCalc, volumeCalc)
        }
    }
}

@Composable
fun ShapeBadge(
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .width(300.dp)
            .height(500.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedCard(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            shape = RoundedCornerShape(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 16.dp)
                    .animateContentSize()
                    .height(if (expanded) 100.dp else 45.dp), //40.dp
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    Column {
                        Text("sisi :", style = MaterialTheme.typography.labelMedium)
                        Spacer(Modifier.height(4.dp))
                        BadgedBox(badge = {
                            Badge{
                                Text(text = "6")
                            }
                        }) {
                            Icon(imageVector = Icons.Outlined.Square, contentDescription = null)
                        }
                    }
                    Column {
                        Text("rusuk :", style = MaterialTheme.typography.labelMedium)
                        Spacer(Modifier.height(4.dp))
                        BadgedBox(badge = {
                            Badge{
                                Text(text = "12")
                            }
                        }) {
                            Icon(imageVector = Icons.Outlined.DensityLarge, contentDescription = null)
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Luas permukaan :", style = MaterialTheme.typography.labelLarge)
                    Text("864 cm2", style = MaterialTheme.typography.labelMedium)
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Volume :", style = MaterialTheme.typography.labelLarge)
                    Text("1728 cm3", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}

@Composable
fun ShapeBadge(
    modifier: Modifier = Modifier,
    shape: SolidShape,
    expanded: MutableState<Boolean>,
    area: Double,
    volume: Double
) {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    Column(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .width(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 16.dp)
                    .animateContentSize()
                    .height(if (expanded.value) 100.dp else 45.dp), //40.dp
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    Column {
                        Text("sisi :", style = MaterialTheme.typography.labelMedium)
                        Spacer(Modifier.height(4.dp))
                        BadgedBox(badge = {
                            Badge{
                                Text(text = shape.sisi.toString())
                            }
                        }) {
                            Icon(imageVector = Icons.Outlined.Square, contentDescription = null)
                        }
                    }
                    Column {
                        Text("rusuk :", style = MaterialTheme.typography.labelMedium)
                        Spacer(Modifier.height(4.dp))
                        BadgedBox(badge = {
                            Badge{
                                Text(text = shape.rusuk.toString())
                            }
                        }) {
                            Icon(imageVector = Icons.Outlined.DensityLarge, contentDescription = null)
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Luas permukaan :", style = MaterialTheme.typography.labelLarge)
                    Text(text = df.format(area) + " cm2", style = MaterialTheme.typography.labelMedium)
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Volume :", style = MaterialTheme.typography.labelLarge)
                    Text(text = df.format(volume) + " cm3", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BadgePreview() {
    UjianTSTheme {
        ShapeBadge()
    }
}