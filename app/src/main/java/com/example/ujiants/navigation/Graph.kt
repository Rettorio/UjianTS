package com.example.ujiants.navigation

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.example.ujiants.geometry.SolidShape
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Serializable
object Home

@Serializable
object Profile

@Serializable
object Tutor

@Serializable
object GeometryList

@Serializable
data class GeometricShape(val shape: SolidShape)

val ShapeParameterType = object : NavType<SolidShape>(
    isNullableAllowed = false
) {
    override fun get(bundle: Bundle, key: String): SolidShape? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, SolidShape::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): SolidShape {
        return Json.decodeFromString<SolidShape>(value)
    }

    override fun serializeAsValue(value: SolidShape): String {
        return Json.encodeToString(value)
    }

    override fun put(bundle: Bundle, key: String, value: SolidShape) {
        bundle.putParcelable(key, value)
    }

}