package com.example.ujiants.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.ujiants.GeometryScreen
import com.example.ujiants.HomeScreen
import com.example.ujiants.ProfileScreen
import com.example.ujiants.ShapeScreenLayout
import com.example.ujiants.TutorScreen
import com.example.ujiants.geometry.SolidShape
import kotlin.reflect.typeOf

@Composable
fun HomeNavigationGraph() {
    val navController = LocalNavController.current

    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> { HomeScreen() }
        composable<Profile> { ProfileScreen() }
        composable<Tutor> { TutorScreen() }
        composable<GeometryList> { GeometryScreen() }
        composable<GeometricShape>(
            typeMap = mapOf(typeOf<SolidShape>() to ShapeParameterType)
        ) { backStackEntry ->
            val (shape) = backStackEntry.toRoute<GeometricShape>()
            ShapeScreenLayout(shape = shape)
        }
    }
}