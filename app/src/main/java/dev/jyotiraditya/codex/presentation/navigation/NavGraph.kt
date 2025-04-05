package dev.jyotiraditya.codex.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.jyotiraditya.codex.di.AppModule
import dev.jyotiraditya.codex.presentation.detail.CodecDetailScreen
import dev.jyotiraditya.codex.presentation.home.HomeScreen

/**
 * Routes used in the navigation graph
 */
object Routes {
    const val HOME = "home"
    const val CODEC_DETAIL = "codec_detail/{codecIndex}"

    fun codecDetail(codecIndex: Int): String = "codec_detail/$codecIndex"
}

/**
 * Main navigation graph for the application
 */
@Composable
fun NavGraph(
    navController: NavHostController
) {
    // Get all codecs once to pass through the navigation
    val getCodecListUseCase = remember { AppModule.getCodecListUseCase }
    val allCodecs = remember { getCodecListUseCase() }

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(
                onCodecClick = { codecItem, index ->
                    navController.navigate(Routes.codecDetail(index))
                }
            )
        }

        composable(
            route = Routes.CODEC_DETAIL,
            arguments = listOf(
                navArgument("codecIndex") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val codecIndex = backStackEntry.arguments?.getInt("codecIndex") ?: 0
            val codecItem = allCodecs.getOrNull(codecIndex)

            if (codecItem != null) {
                CodecDetailScreen(
                    codecInfo = codecItem,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}