package c.bmartinez.yelpclone.presentation.utils.toolbar

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import c.bmartinez.yelpclone.presentation.search_list_screen.view_model.SearchListViewModel
import c.bmartinez.yelpclone.utils.SharedPreferencesUtils

@ExperimentalComposeUiApi
@Composable
fun SearchScreenToolBar(
    viewModel: SearchListViewModel = hiltViewModel(),
    focusManager: FocusManager,
    context: Context,
    navController: NavController
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
        ,
        color = Color.White,
        elevation = 8.dp
    ){
        Row(modifier = Modifier.fillMaxWidth()) {
            val query = viewModel.queryTerm
            TextField(
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .padding(8.dp)
                ,
                value = query.value,
                onValueChange = { newValue ->
                    viewModel.onQueryTermChanged(newValue)
                },
                label = {
                    Text(text = "Search")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                leadingIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                trailingIcon = {
                    if (query.value != TextFieldValue("").text) {
                        IconButton(
                            onClick = {
                                viewModel.onQueryTermClear()
                            }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(15.dp)
                                    .size(24.dp)
                            )
                        }
                    }
                },
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus(true)
                        searchNewBusinesses(viewModel, query.value, context)
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    cursorColor = Color.Black,

                    ),
                textStyle = TextStyle(
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

fun searchNewBusinesses(viewModel: SearchListViewModel, searchTerm: String, context: Context){
    val latitude = SharedPreferencesUtils.getFloatPref(
        context,
        SharedPreferencesUtils.COORDINATES_SPF,
        SharedPreferencesUtils.COORDINATES_LAT,
        0.0f
    )?.toDouble()

    val longitude = SharedPreferencesUtils.getFloatPref(
        context,
        SharedPreferencesUtils.COORDINATES_SPF,
        SharedPreferencesUtils.COORDINATES_LONG,
        0.0f
    )?.toDouble()

    latitude?.let { resultLatitude ->
        longitude?.let { resultLongitude ->
            viewModel.getSearchBusinesses(
                searchTerm,
                resultLatitude,
                resultLongitude
            )
        }
    }
}