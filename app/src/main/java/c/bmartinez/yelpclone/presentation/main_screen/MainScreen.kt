package c.bmartinez.yelpclone.presentation.main_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import c.bmartinez.yelpclone.presentation.main_screen.components.ParentFragRecyclerView
import c.bmartinez.yelpclone.presentation.main_screen.view_model.MainScreenViewModel
import c.bmartinez.yelpclone.presentation.utils.ProgressDialog
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
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        viewModel.onQueryTermClear()
                        focusManager.clearFocus(true)
                    })
                }
        ) {
            InitialToolBar(viewModel, focusManager, navController)

            ParentFragRecyclerView(
                popularBusinesses = state.businesses,
                navigationController = navController
            )

            if(state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        //.align(Alignment.Center)
                )
            }

            if(state.isLoading) {
                ProgressDialog()
            }
        }

    }
}