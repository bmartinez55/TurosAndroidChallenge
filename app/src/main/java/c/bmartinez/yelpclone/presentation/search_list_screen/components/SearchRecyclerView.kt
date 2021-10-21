package c.bmartinez.yelpclone.presentation.search_list_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import c.bmartinez.yelpclone.domain.model.business_search.Businesses
import c.bmartinez.yelpclone.presentation.Screen

@Composable
fun SearchRecyclerView(searchedLocations: List<Businesses>, navController: NavController) {
    Column {
        Text(
            text = "All Results",
            modifier = Modifier
                .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .fillMaxWidth()
            ,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold
        )
        LazyColumn{
            itemsIndexed(items = searchedLocations) { index, searchedLocation ->
                SearchListItem(searchItem = searchedLocation,
                    onClick = {
                        navController.navigate(
                            Screen.BusinessDetailsScreen.route + "/${searchedLocation.id}"
                        )
                    }
                )
            }
        }
    }
}