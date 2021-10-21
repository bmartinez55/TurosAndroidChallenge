package c.bmartinez.yelpclone.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import c.bmartinez.yelpclone.presentation.location_details_screen.BusinessDetailsScreen
import c.bmartinez.yelpclone.presentation.main_screen.MainScreen
import c.bmartinez.yelpclone.presentation.search_list_screen.SearchListScreen
import c.bmartinez.yelpclone.utils.services.DeviceLocationService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val TAG = MainActivity::class.java.name

    @ExperimentalComposeUiApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this.application

        setContent{
            Surface(color = MaterialTheme.colors.background) {
                val navController = rememberNavController()
                val focusManager = LocalFocusManager.current
                NavHost(
                    navController = navController,
                    startDestination = Screen.MainScreen.route
                ) {
                    composable(
                        route = Screen.MainScreen.route
                    ) {
                        MainScreen(navController, focusManager)
                    }
                    composable(
                        route = Screen.BusinessDetailsScreen.route + "/{businessId}"
                    ) {
                        BusinessDetailsScreen(navController)
                    }
                    composable(
                        route = Screen.SearchListScreen.route + "/{searchTerm}"
                    ) {
                        SearchListScreen(navController, focusManager, context)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, DeviceLocationService::class.java))
    }
}