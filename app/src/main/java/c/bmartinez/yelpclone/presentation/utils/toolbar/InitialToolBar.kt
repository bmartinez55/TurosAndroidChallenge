package c.bmartinez.yelpclone.presentation.utils.toolbar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import c.bmartinez.yelpclone.presentation.Screen
import c.bmartinez.yelpclone.presentation.main_screen.view_model.MainScreenViewModel

@ExperimentalComposeUiApi
@Composable
fun InitialToolBar(
    viewModel: MainScreenViewModel = hiltViewModel(),
    focusManager: FocusManager,
    navController: NavController,
    isLoading: Boolean
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
        ,
        color = Color.White,
        elevation = 8.dp,
    ){
        Column{
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
                        Icon(Icons.Filled.Search, contentDescription = null)
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
                            navController.navigate(
                                Screen.SearchListScreen.route + "/${query.value}"
                            )
                        }
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        cursorColor = Color.Black,

                        ),
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface)
                )
            }
            if(isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}