package com.example.ujiants

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ujiants.geometry.SolidShape
import kotlin.math.hypot
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun FormGenerator(
    listField: Map<String, MutableState<String>>,
    matchPattern: Regex,
    toChange: MutableState<Boolean>
) {
    listField.forEach {(fieldName, fieldState) ->
        OutlinedTextField(
            value = fieldState.value,
            onValueChange = {
                if (it.isEmpty() || it.matches(matchPattern)) {
                    toChange.value = false
                    fieldState.value = it
                }
            },
            label = { Text(fieldName) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
fun FormCalc(
    shape: SolidShape,
    calcNow: MutableState<Boolean>,
    areaCalc: MutableDoubleState,
    volumeCalc: MutableDoubleState
) {
    val pattern = remember { Regex("^\\d+\$") }
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
                    listField = mapOf("panjang jari-jari" to arcString, "panjang tinggi" to heightString),
                    matchPattern = pattern,
                    toChange = calcNow
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
                        areaCalc.doubleValue = shape.area()
                        volumeCalc.doubleValue = shape.volume()
                    }
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
                FormGenerator(listField = mapOf("panjang rusuk" to rusukString), matchPattern = pattern, toChange = calcNow)
                Spacer(modifier = Modifier.height(2.dp))
                FilledTonalButton(onClick = { calcNow.value = true }) {
                    Text(text = "Hitung")
                }
                Spacer(modifier = Modifier.height(12.dp))
                if(calcNow.value) {
                    if(rusukString.value.isNotEmpty()) {
                        shape.rusukLength = rusukString.value.toDouble()
                        areaCalc.doubleValue = shape.area()
                        volumeCalc.doubleValue = shape.volume()
                    }
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
                    matchPattern = pattern,
                    toChange = calcNow
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
                        areaCalc.doubleValue = shape.area()
                        volumeCalc.doubleValue = shape.volume()
                    }
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
                FormGenerator(listField = mapOf("panjang jari-jari" to archString,"panjang tinggi" to heightString), matchPattern = pattern, toChange = calcNow)
                Spacer(modifier = Modifier.height(2.dp))
                FilledTonalButton(onClick = { calcNow.value = true }) {
                    Text(text = "Hitung")
                }
                Spacer(modifier = Modifier.height(12.dp))
                if(calcNow.value) {
                    if(archString.value.isNotEmpty() && heightString.value.isNotEmpty()) {
                        shape.arc = archString.value.toDouble()
                        shape.height = heightString.value.toDouble()
                        areaCalc.doubleValue = shape.area()
                        volumeCalc.doubleValue = shape.volume()
                    }
//                    ShowResult(areaValue = areaCalc, volumeValue = volumeCalc)
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
                FormGenerator(listField = mapOf("panjang sisi" to baseEdgeString, "panjang tinggi" to heightString), matchPattern = pattern, toChange = calcNow)
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
                        areaCalc.doubleValue = shape.area()
                        volumeCalc.doubleValue = shape.volume()
                    }
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
                FormGenerator(listField = mapOf("panjang sisi alas" to baseWidthString, "panjang tinggi" to heightString), matchPattern = pattern, toChange = calcNow)
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
                        areaCalc.doubleValue = shape.area()
                        volumeCalc.doubleValue = shape.volume()
                    }
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
                FormGenerator(listField = mapOf("panjang jari-jari" to arcString), matchPattern = pattern, toChange = calcNow)
                Spacer(modifier = Modifier.height(2.dp))
                FilledTonalButton(onClick = { calcNow.value = true }) {
                    Text(text = "Hitung")
                }
                Spacer(modifier = Modifier.height(12.dp))
                if(calcNow.value) {
                    if(arcString.value.isNotEmpty()) {
                        shape.arc = arcString.value.toDouble()
                        areaCalc.doubleValue = shape.area()
                        volumeCalc.doubleValue = shape.volume()
                    }
                }
            }
        }
    }
}
