package c.bmartinez.yelpclone.presentation.search_list_screen

import android.content.Context
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import c.bmartinez.yelpclone.presentation.search_list_screen.components.SearchRecyclerView
import c.bmartinez.yelpclone.presentation.search_list_screen.view_model.SearchListViewModel
import c.bmartinez.yelpclone.presentation.utils.toolbar.SearchScreenToolBar
import dagger.hilt.android.qualifiers.ApplicationContext

@ExperimentalComposeUiApi
@Composable
fun SearchListScreen(
    navController: NavController,
    focusManager: FocusManager,
    @ApplicationContext context: Context,
    viewModel: SearchListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus(true)
                    })
                }
        ) {
            SearchScreenToolBar(viewModel, focusManager, context, navController)

            SearchRecyclerView(state.businesses, navController)
        }
    }
}