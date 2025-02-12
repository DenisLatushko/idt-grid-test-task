package net.idt.testtask.grid.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import net.idt.testtask.grid.feature.grid.GridScreenEntryPoint
import net.idt.testtask.grid.feature.gridbuilder.GridBuilderEntryPoint
import net.idt.testtask.grid.feature.gridbuilder.GridBuilderSideEffect.NavigationSideEffect.GridReady

@Composable
internal fun AppNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = GridBuilderRoute
    ) {
        composable<GridBuilderRoute>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            GridBuilderEntryPoint { navEffect ->
                when(navEffect) {
                    is GridReady -> navHostController.navigate(
                        route = GridRoute(
                            colNumber = navEffect.colNumber,
                            rowNumber = navEffect.rowNumber
                        )
                    )
                }
            }
        }

        composable<GridRoute>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) { backStackEntry ->
            val route = backStackEntry.toRoute<GridRoute>()

            GridScreenEntryPoint(
                colNumber = route.colNumber,
                rowNumber = route.rowNumber
            )
        }
    }
}