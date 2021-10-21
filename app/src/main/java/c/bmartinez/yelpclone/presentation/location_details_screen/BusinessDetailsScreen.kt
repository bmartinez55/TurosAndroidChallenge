package c.bmartinez.yelpclone.presentation.location_details_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import c.bmartinez.yelpclone.presentation.location_details_screen.components.BusinessDetailsImage
import c.bmartinez.yelpclone.presentation.location_details_screen.components.LocationAdditionalInfo
import c.bmartinez.yelpclone.presentation.location_details_screen.view_model.BusinessDetailsViewModel

@Composable
fun BusinessDetailsScreen(
    navController: NavController,
    viewModel: BusinessDetailsViewModel = hiltViewModel()
){
    val verticalScrollState = rememberScrollState()
    val state = viewModel.state.value

    //Smooth scroll to specified pixels on first composition
    LaunchedEffect(Unit) { verticalScrollState.animateScrollTo(10000) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(verticalScrollState)
        ) {
            BusinessDetailsImage(state.business, navController)
            LocationAdditionalInfo(state.business)
        }
    }

}