package c.bmartinez.yelpclone.presentation.main_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import c.bmartinez.yelpclone.presentation.main_screen.components.ParentMainScreenList
import c.bmartinez.yelpclone.presentation.main_screen.view_model.MainScreenViewModel
import c.bmartinez.yelpclone.presentation.utils.DisplayError
import c.bmartinez.yelpclone.presentation.utils.toolbar.InitialToolBar

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun MainScreen(
    navController: NavController,
    focusManager: FocusManager,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopStart)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        viewModel.onQueryTermClear()
                        focusManager.clearFocus(true)
                    })
                }
        ) {
            InitialToolBar(viewModel, focusManager, navController, state.isLoading)
            ParentMainScreenList(
                popularBusinesses = state.businesses,
                navigationController = navController
            )
        }
        DisplayError(state.error)
    }
}