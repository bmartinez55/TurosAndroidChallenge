package c.bmartinez.yelpclone.presentation.main_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import c.bmartinez.yelpclone.domain.model.business_search.Businesses
import c.bmartinez.yelpclone.presentation.Screen

@Composable
fun PopularBusinessesLazyRow(
    popularBusinesses: List<Businesses>,
    navigationController: NavController
) {
    Column {
        Text(
            text = "Popular spots near you",
            modifier = Modifier
                .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .fillMaxWidth()
            ,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "The best in your neighborhood",
            modifier = Modifier
                .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 10.dp)
                .fillMaxWidth()
            ,
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Light
        )
        LazyRow{
            itemsIndexed(items = popularBusinesses) { index, business ->
                PopularListItem(
                    business = business,
                    onClick = {
                        navigationController.navigate(
                            Screen.BusinessDetailsScreen.route + "/${business.id}"
                        )
                    }
                )
            }
        }
    }
}