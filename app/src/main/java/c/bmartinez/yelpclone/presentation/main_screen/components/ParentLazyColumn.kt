package c.bmartinez.yelpclone.presentation.main_screen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import c.bmartinez.yelpclone.domain.model.business_search.Businesses

//Parent Recycler View that holds nested Lazy Rows or Columns
@ExperimentalFoundationApi
@Composable
fun ParentMainScreenList(
    popularBusinesses: List<Businesses>,
    navigationController: NavController
) {
    LazyColumn(
        contentPadding = PaddingValues(start = 10.dp)
    ){
        item {
            Text(
                text = "Nearby and open now",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 0.dp, end = 0.dp, top = 10.dp, bottom = 15.dp)
                ,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp
            )
        }

        item {
            PopularBusinessesLazyRow(popularBusinesses = popularBusinesses, navigationController = navigationController)
        }
    }
}