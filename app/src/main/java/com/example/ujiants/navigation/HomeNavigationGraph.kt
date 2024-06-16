package com.example.ujiants.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
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
            enterTransition = {
                return@composable fadeIn(tween(1000))
            },
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(400)
                )
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(350)
                )
            }
        ) { HomeScreen() }

        composable<Profile>(
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(400)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(350)
                )
            }
        ) { ProfileScreen() }

        composable<Tutor>(
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(400)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(350)
                )
            }
        ) { TutorScreen() }


        composable<GeometryList>(
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(400)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(350)
                )
            }
        ) { GeometryScreen() }

    }
}