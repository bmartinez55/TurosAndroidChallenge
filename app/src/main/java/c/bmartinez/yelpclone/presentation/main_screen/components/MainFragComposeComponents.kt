package c.bmartinez.yelpclone.presentation.mainscreen.components.mainfrag

import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import c.bmartinez.yelpclone.R
import c.bmartinez.yelpclone.data.model.Results
import c.bmartinez.yelpclone.presentation.ui.components.utils.DisplayStarRating
import c.bmartinez.yelpclone.utils.DEFAULT_BUSINESS_IMAGE
import c.bmartinez.yelpclone.utils.LoadPicture

@Composable
fun MainFragProgressDialog(isDisplayed: Boolean) {
    if(isDisplayed){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

//Parent Recycler View that holds nested Recycler Views
@ExperimentalFoundationApi
@Composable
fun ParentFragRecyclerView(popularLocations: List<Results>, navigationController: NavController) {
    LazyColumn(
        contentPadding = PaddingValues(start = 10.dp)
    ){
        stickyHeader{
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
            PopularLocationsRecyclerView(popularLocations, navigationController = navigationController)
        }
    }
}

@Composable
fun PopularLocationsRecyclerView(popularLocations: List<Results>, navigationController: NavController) {
    if(popularLocations == null){

    } else {
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
                itemsIndexed(items = popularLocations) { index, popLocation ->
                    PopularListItem(popularLocation = popLocation,
                        onClick = {
                                  if(!popLocation.id.isNullOrEmpty()){
                                      val bundle = Bundle()
                                      bundle.putString("locationID", popLocation.id)
                                      navigationController.navigate(R.id.viewLocationDetails, bundle)
                                  }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PopularListItem(popularLocation: Results, onClick: () -> Unit){
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .width(270.dp)
            .padding(start = 0.dp, end = 8.dp, top = 0.dp, bottom = 0.dp)
            .border(width = 0.dp, color = Color.Transparent, shape = RectangleShape)
            .clickable(onClick = onClick)
        ,
        elevation = 0.dp
    ) {
        Column {
            popularLocation.image_url.let { url ->
                val image = LoadPicture(url = url, defaultImage = DEFAULT_BUSINESS_IMAGE).value
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )
                }
            }
            Text(
                text = popularLocation.name,
                modifier = Modifier
                    .padding(start = 1.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                    .fillMaxWidth(.5f)
                    .wrapContentWidth(Alignment.Start),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
            popularLocation.rating.let { rate ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DisplayStarRating(rating = rate, height = 40, width = 80)
                    Text(
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .align(Alignment.CenterVertically)
                        ,
                        textAlign = TextAlign.Center,
                        text = "${popularLocation.review_count} Reviews",
                        color = Color.Gray
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(popularLocation.price.isNullOrBlank()){
                    Text(
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .align(Alignment.CenterVertically)
                        ,
                        textAlign = TextAlign.Center,
                        text = popularLocation.categories[0].title,
                        color = Color.Gray
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .align(Alignment.CenterVertically)
                        ,
                        textAlign = TextAlign.Center,
                        text = "${popularLocation.price} \u2022 ${popularLocation.categories[0].title}",
                        color = Color.Gray
                    )
                }
            }
        }
    }
}