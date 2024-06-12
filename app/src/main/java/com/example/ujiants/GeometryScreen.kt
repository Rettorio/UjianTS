package com.example.ujiants

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.ujiants.geometry.SolidShape
import com.example.ujiants.navigation.GeometricShape
import com.example.ujiants.navigation.LocalNavController


@OptIn(ExperimentalMaterial3Api::class)
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bangun Ruang") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Navigation icon")
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            /// :D imma lazy af
            items(shapeList) { shape ->
                GeometricItem(shape = shape)
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}

@Composable
fun GeometricItem(
    modifier: Modifier = Modifier,
    shape: SolidShape
) {
    val navController = LocalNavController.current
    ListItem(
        modifier = modifier.fillMaxWidth(),
        headlineContent = { Text(text = shape.name, style = MaterialTheme.typography.titleMedium) },
        supportingContent = {
            Text(
                text = shape.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            ) },
        leadingContent = {
            Image(
                painter = painterResource(shape.imageId),
                contentDescription = null,
                modifier = Modifier
                    .height(48.dp)
                    .width(48.dp)
                    .clip(MaterialTheme.shapes.small)
                    .aspectRatio(1f / 1f),
                contentScale = ContentScale.Crop
            )
        },
        trailingContent = {
            IconButton(onClick = { navController.navigate(GeometricShape(shape)) }) {
                Icon(imageVector = Icons.AutoMirrored.Outlined.OpenInNew, contentDescription = null)
            }
        }
    )
}