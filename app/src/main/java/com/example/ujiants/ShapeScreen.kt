package com.example.ujiants

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ujiants.geometry.SolidShape
import com.example.ujiants.navigation.LocalNavController
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.hypot
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun FormGenerator(
    listField: Map<String, MutableState<String>>,
    matchPattern: Regex
) {
    listField.forEach {(fieldName, fieldState) ->
        OutlinedTextField(
            value = fieldState.value,
            onValueChange = {
                if (it.isEmpty() || it.matches(matchPattern)) {
                    fieldState.value = it
                }
            },
            label = { Text(fieldName) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
fun ShowResult(
    areaValue: Double,
    volumeValue: Double
) {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Luas Permukaan :",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth(0.75f),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = df.format(areaValue) + " cm2",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.fillMaxWidth(0.75f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Volume :",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth(0.75f),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = df.format(volumeValue) + " cm3",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.fillMaxWidth(0.75f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FormCalc(
    shape: SolidShape,
    calcNow: MutableState<Boolean>
) {
    val pattern = remember { Regex("^\\d+\$") }
    var areaCalc: Double by remember { mutableDoubleStateOf(shape.area()) }
    var volumeCalc: Double by remember { mutableDoubleStateOf(shape.volume()) }
    when(shape) {
        is SolidShape.Cone -> {
            val arcString: MutableState<String> = remember { mutableStateOf("") }
            val heightString: MutableState<String> = remember { mutableStateOf("") }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FormGenerator(
                    listField = mapOf("jari-jari" to arcString, "tinggi" to heightString),
                    matchPattern = pattern
                )
                Spacer(modifier = Modifier.height(2.dp))
                FilledTonalButton(onClick = { calcNow.value = true }) {
                    Text(text = "Hitung")
                }
                Spacer(modifier = Modifier.height(12.dp))
                if(calcNow.value) {
                    if(arcString.value.isNotEmpty() &&
                        heightString.value.isNotEmpty()
                        ) {
                        shape.arc = arcString.value.toDouble()
                        shape.height = heightString.value.toDouble()
                        shape.hypot = hypot(shape.arc, shape.height)
                        areaCalc = shape.area()
                        volumeCalc = shape.volume()
                    }
                    ShowResult(areaValue = areaCalc, volumeValue = volumeCalc)
                }
            }
        }
        is SolidShape.Cube -> {
            val rusukString: MutableState<String> = remember { mutableStateOf("") }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FormGenerator(listField = mapOf("rusuk" to rusukString), matchPattern = pattern)
                Spacer(modifier = Modifier.height(2.dp))
                FilledTonalButton(onClick = { calcNow.value = true }) {
                    Text(text = "Hitung")
                }
                Spacer(modifier = Modifier.height(12.dp))
                if(calcNow.value) {
                    if(rusukString.value.isNotEmpty()) {
                        shape.rusuk = rusukString.value.toDouble()
                        areaCalc = shape.area()
                        volumeCalc = shape.volume()
                    }
                    ShowResult(areaValue = areaCalc, volumeValue = volumeCalc)
                }
            }
        }
        is SolidShape.Cubeoid -> {
            val widthString: MutableState<String> = remember { mutableStateOf("") }
            val heightString: MutableState<String> = remember { mutableStateOf("") }
            val lengthString: MutableState<String> = remember { mutableStateOf("") }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FormGenerator(
                    listField = mapOf("panjang" to widthString, "tinggi" to heightString, "lebar" to lengthString),
                    matchPattern = pattern
                )
                Spacer(modifier = Modifier.height(2.dp))
                FilledTonalButton(onClick = { calcNow.value = true }) {
                    Text(text = "Hitung")
                }
                Spacer(modifier = Modifier.height(12.dp))
                if(calcNow.value) {
                    if(widthString.value.isNotEmpty() &&
                        heightString.value.isNotEmpty() &&
                        lengthString.value.isNotEmpty()
                        ) {
                        shape.width = widthString.value.toDouble()
                        shape.height = heightString.value.toDouble()
                        shape.length = lengthString.value.toDouble()
                        areaCalc = shape.area()
                        volumeCalc = shape.volume()
                    }
                    ShowResult(areaValue = areaCalc, volumeValue = volumeCalc)
                }
            }
        }
        is SolidShape.Cylinder -> {
            val archString: MutableState<String> = remember { mutableStateOf("") }
            val heightString: MutableState<String> = remember { mutableStateOf("") }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FormGenerator(listField = mapOf("jari-jari" to archString,"tinggi" to heightString), matchPattern = pattern)
                Spacer(modifier = Modifier.height(2.dp))
                FilledTonalButton(onClick = { calcNow.value = true }) {
                    Text(text = "Hitung")
                }
                Spacer(modifier = Modifier.height(12.dp))
                if(calcNow.value) {
                    if(archString.value.isNotEmpty() && heightString.value.isNotEmpty()) {
                        shape.arc = archString.value.toDouble()
                        shape.height = heightString.value.toDouble()
                        areaCalc = shape.area()
                        volumeCalc = shape.volume()
                    }
                    ShowResult(areaValue = areaCalc, volumeValue = volumeCalc)
                }
            }
        }
        is SolidShape.OctaPrism -> {
            val baseEdgeString: MutableState<String> = remember { mutableStateOf("") }
            val heightString: MutableState<String> = remember { mutableStateOf("") }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FormGenerator(listField = mapOf("panjang sisi" to baseEdgeString, "tinggi" to heightString), matchPattern = pattern)
                Spacer(modifier = Modifier.height(2.dp))
                FilledTonalButton(onClick = { calcNow.value = true }) {
                    Text(text = "Hitung")
                }
                Spacer(modifier = Modifier.height(12.dp))
                if(calcNow.value) {
                    if(baseEdgeString.value.isNotEmpty() && heightString.value.isNotEmpty()) {
                        shape.baseEdge = baseEdgeString.value.toDouble()
                        shape.height = heightString.value.toDouble()
                        shape.lsa = 8 * (shape.baseEdge * shape.height)
                        shape.mult = ((1 + sqrt(2.0)) * shape.baseEdge.pow(2))
                        areaCalc = shape.area()
                        volumeCalc = shape.volume()
                    }
                    ShowResult(areaValue = areaCalc, volumeValue = volumeCalc)
                }
            }
        }
        is SolidShape.Pyramid -> {
            val baseWidthString: MutableState<String> = remember { mutableStateOf("") }
            val heightString: MutableState<String> = remember { mutableStateOf("") }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FormGenerator(listField = mapOf("sisi alas" to baseWidthString, "tinggi" to heightString), matchPattern = pattern)
                Spacer(modifier = Modifier.height(2.dp))
                FilledTonalButton(onClick = { calcNow.value = true }) {
                    Text(text = "Hitung")
                }
                Spacer(modifier = Modifier.height(12.dp))
                if(calcNow.value) {
                    if(baseWidthString.value.isNotEmpty() && heightString.value.isNotEmpty()) {
                        shape.baseWidth = baseWidthString.value.toDouble()
                        shape.height = heightString.value.toDouble()
                        shape.aob = shape.baseWidth.pow(2)
                        shape.hypot = hypot((shape.baseWidth/2), shape.height)
                        areaCalc = shape.area()
                        volumeCalc = shape.volume()
                    }
                    ShowResult(areaValue = areaCalc, volumeValue = volumeCalc)
                }
            }
        }
        is SolidShape.Sphere -> {
            val arcString: MutableState<String> = remember { mutableStateOf("") }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FormGenerator(listField = mapOf("jari-jari" to arcString), matchPattern = pattern)
                Spacer(modifier = Modifier.height(2.dp))
                FilledTonalButton(onClick = { calcNow.value = true }) {
                    Text(text = "Hitung")
                }
                Spacer(modifier = Modifier.height(12.dp))
                if(calcNow.value) {
                    if(arcString.value.isNotEmpty()) {
                        shape.arc = arcString.value.toDouble()
                        areaCalc = shape.area()
                        volumeCalc = shape.volume()
                    }
                    ShowResult(areaValue = areaCalc, volumeValue = volumeCalc)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShapeScreenLayout(
    shape: SolidShape
) {
    val calculateBtnClicked = remember {  mutableStateOf(false)  }
    val navController = LocalNavController.current
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { /*TODO*/ },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 22.dp)
                .padding(it)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = shape.name, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(12.dp))
            Image(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .aspectRatio(1f / 1f)
                    .clip(MaterialTheme.shapes.small),
                painter = painterResource(id = shape.imageId),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = shape.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(0.75f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            FormCalc(shape = shape, calcNow = calculateBtnClicked)
        }
    }
}

@Preview
@Composable
fun ShapeScreenPreview() {
    ShapeScreenLayout(
        shape = SolidShape.Cone(description = stringResource(id = R.string.shape_cone_desc), imageId = R.drawable.cone)
    )
}