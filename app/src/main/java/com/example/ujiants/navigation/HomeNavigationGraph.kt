package com.example.ujiants.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ujiants.GeometryScreen
import com.example.ujiants.HomeScreen
import com.example.ujiants.ProfileScreen
import com.example.ujiants.TutorScreen

@Composable
fun HomeNavigationGraph() {
    val navController = LocalNavController.current

    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home>(
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, tween(1000)
                )
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, tween(1000)
                )
            }
        ) { HomeScreen() }

        composable<Profile>(
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, tween(1000)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, tween(1000)
                )
            }
        ) { ProfileScreen() }

        composable<Tutor>(
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, tween(1000)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, tween(1000)
                )
            }
        ) { TutorScreen() }


        composable<GeometryList>(
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, tween(1000)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, tween(1000)
                )
            }
        ) { GeometryScreen() }

    }
}