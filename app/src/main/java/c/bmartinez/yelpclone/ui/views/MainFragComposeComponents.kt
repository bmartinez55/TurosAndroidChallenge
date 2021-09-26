package c.bmartinez.yelpclone.ui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import c.bmartinez.yelpclone.R
import c.bmartinez.yelpclone.data.model.Results

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
fun ParentFragRecyclerView(popularLocations: MutableList<Results>?) {
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
            PopularLocationsRecyclerView(popularLocations)
        }
    }
}

@Composable
fun PopularLocationsRecyclerView(popularLocations: MutableList<Results>?) {
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
                items(popularLocations) { locations ->
                    PopularListItem(locations)
                }
            }
        }
    }
}

@Composable
fun PopularListItem(popularLocation: Results){
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .width(300.dp)
            .padding(start = 0.dp, end = 8.dp, top = 0.dp, bottom = 0.dp)
            .border(width = 0.dp, color = Color.Transparent, shape = RectangleShape)
        ,
        elevation = 0.dp
    ) {
        Column {
            popularLocation.image_url?.let {
                Image(
                    bitmap = ImageBitmap
                        .imageResource(R.drawable.temp_business_icon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DisplayStarRating(rating = popularLocation.rating)
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if(popularLocation.price == ""){
                        Text(
                            modifier = Modifier
                                .padding(start = 2.dp)
                                .align(Alignment.CenterVertically)
                            ,
                            textAlign = TextAlign.Center,
                            text = "${popularLocation.categories}",
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
//            GlideImage(
//                imageModel = popularLocation.image_url,
//                contentScale = ContentScale.Inside,
//                circularReveal = CircularReveal(duration = 250),
//                placeHolder = ImageBitmap.imageResource(R.drawable.temp_business_icon),
//                error = ImageBitmap.imageResource(R.drawable.temp_business_icon)
//            )
    }
}

@Composable
fun DisplayStarRating(rating: Double){
    when(rating){
        0.0 -> Image(
            modifier = Modifier
                .padding(start = 2.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .height(40.dp)
                .width(80.dp)
            ,
            bitmap = ImageBitmap.imageResource(id = R.drawable.stars_regular_0),
            contentDescription = null,
        )
        1.0 -> Image(
            modifier = Modifier
                .padding(start = 2.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .height(40.dp)
                .width(80.dp)
            ,
            bitmap = ImageBitmap.imageResource(id = R.drawable.stars_regular_1),
            contentDescription = null
        )
        1.5 -> Image(
            modifier = Modifier
                .padding(start = 2.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .height(40.dp)
                .width(80.dp)
            ,
            bitmap = ImageBitmap.imageResource(id = R.drawable.stars_regular_1_half),
            contentDescription = null
        )
        2.0 -> Image(
            modifier = Modifier
                .padding(start = 2.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .height(40.dp)
                .width(80.dp)
            ,
            bitmap = ImageBitmap.imageResource(id = R.drawable.stars_regular_2),
            contentDescription = null
        )
        2.5 -> Image(
            modifier = Modifier
                .padding(start = 2.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .height(40.dp)
                .width(80.dp)
            ,
            bitmap = ImageBitmap.imageResource(id = R.drawable.stars_regular_2_half),
            contentDescription = null
        )
        3.0 -> Image(
            modifier = Modifier
                .padding(start = 2.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .height(40.dp)
                .width(80.dp)
            ,
            bitmap = ImageBitmap.imageResource(id = R.drawable.stars_regular_3),
            contentDescription = null
        )
        3.5 -> Image(
            modifier = Modifier
                .padding(start = 2.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .height(40.dp)
                .width(80.dp)
            ,
            bitmap = ImageBitmap.imageResource(id = R.drawable.stars_regular_3_half),
            contentDescription = null
        )
        4.0 -> Image(
            modifier = Modifier
                .padding(start = 2.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .height(40.dp)
                .width(80.dp)
            ,
            bitmap = ImageBitmap.imageResource(id = R.drawable.stars_regular_4),
            contentDescription = null
        )
        4.5 -> Image(
            modifier = Modifier
                .padding(start = 2.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .height(40.dp)
                .width(80.dp)
            ,
            bitmap = ImageBitmap.imageResource(id = R.drawable.stars_regular_4_half),
            contentDescription = null
        )
        5.0 -> Image(
            modifier = Modifier
                .padding(start = 2.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .height(40.dp)
                .width(80.dp)
            ,
            bitmap = ImageBitmap.imageResource(id = R.drawable.stars_regular_5),
            contentDescription = null
        )
    }
}