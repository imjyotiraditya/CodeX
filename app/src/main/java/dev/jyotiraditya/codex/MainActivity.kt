// file: MainActivity.kt
package dev.jyotiraditya.codex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import dev.jyotiraditya.codex.presentation.navigation.NavGraph
import dev.jyotiraditya.codex.presentation.theme.CodexTheme

/**
 * Main activity for the Codex application
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodexApp()
        }
    }
}

/**
 * Root composable for the application
 */
@Composable
fun CodexApp() {
    CodexTheme {
        val navController = rememberNavController()
        NavGraph(navController = navController)
    }
}