package com.example.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.list.presentation.ChartViewModel
import com.example.list.presentation.DownloadedViewModel
import com.example.list.presentation.model.ListContract
import com.example.list.presentation.ui.MusicList
import com.example.player.presentation.PlayerViewModel
import com.example.player.presentation.ui.PlayerScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavigation(paddingValues: PaddingValues, navController: NavHostController) {
    NavHost(navController = navController, startDestination = Destination.Catalog.route) {
        composable(Destination.Catalog.route) {
            val viewModel: ChartViewModel = koinViewModel()
            MusicList(
                source = "remote",
                paddingValues = paddingValues,
                state = viewModel.viewState.value,
                effectFlow = viewModel.effect,
                onEventSent = { event -> viewModel.setEvent(event) },
                onNavigationRequested = { effect ->
                    when (effect) {
                        is ListContract.Effect.Navigation.ToPlayer -> {
                            navController.navigate(route = "current/${effect.trackId}/${effect.query}/${effect.source}")
                        }
                    }
                }
            )
        }
        composable(Destination.CurrentTrack.route, arguments = listOf(
            navArgument(name = TRACK_ID) { type = NavType.IntType },
            navArgument(name = QUERY) { type = NavType.StringType },
            navArgument(name = SOURCE) { type = NavType.StringType }
        )) { navBackStackEntry ->
            val trackId =
                requireNotNull(navBackStackEntry.arguments?.getInt(TRACK_ID)) { "TRACK_ID is required as an argument" }
            val query =
                requireNotNull(navBackStackEntry.arguments?.getString(QUERY)) { "QUERY is required as an argument" }
            val source =
                requireNotNull(navBackStackEntry.arguments?.getString(SOURCE)) { "SOURCE is required as an argument" }
            val viewModel: PlayerViewModel = koinViewModel { parametersOf(trackId, query, source) }
            PlayerScreen(
                paddingValues = paddingValues,
                state = viewModel.viewState.value,
                effectFlow = viewModel.effect,
                onEventSent = { event -> viewModel.setEvent(event) },
            )
        }
        composable(Destination.Downloaded.route) {
            val viewModel: DownloadedViewModel = koinViewModel()
            MusicList(
                source = "local",
                paddingValues = paddingValues,
                state = viewModel.viewState.value,
                effectFlow = viewModel.effect,
                onEventSent = { event -> viewModel.setEvent(event) },
                onNavigationRequested = { effect ->
                    when (effect) {
                        is ListContract.Effect.Navigation.ToPlayer -> {
                            navController.navigate(route = "current/${effect.trackId}/${effect.query}/local")
                        }
                    }
                }
            )
        }
    }
}

const val TRACK_ID = "TRACK_ID"
const val QUERY = "QUERY"
const val SOURCE = "SOURCE"