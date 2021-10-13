package c.bmartinez.yelpclone.ui.components.locationdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import c.bmartinez.yelpclone.data.model.Results
import c.bmartinez.yelpclone.data.model.ResultsCategories
import c.bmartinez.yelpclone.data.model.ResultsLocation
import c.bmartinez.yelpclone.data.model.YelpBusinessDetails
import c.bmartinez.yelpclone.ui.components.utils.DisplayStarRating
import c.bmartinez.yelpclone.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LocationInfo(location: YelpBusinessDetails, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(LOCATION_DETAILS_IMAGE_HEIGHT.dp)
    ) {
//        location.image_url.let{ url ->
//            val image = LoadPicture(url = url, defaultImage = DEFAULT_BUSINESS_IMAGE).value
//            image?.let { img ->
//                Image(
//                    bitmap = img.asImageBitmap(),
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(LOCATION_DETAILS_IMAGE_HEIGHT.dp)
//                )
//            }
//        }
        Image(
            painter = painterResource(id = DEFAULT_BUSINESS_IMAGE),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(LOCATION_DETAILS_IMAGE_HEIGHT.dp)
            ,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
        ) {
            LocationDetailsBackButton(onBackPress = {
                navController.navigateUp()
            })
        }

        Column(
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Text(
                text = location.name,
                modifier = Modifier
                    .padding(start = 20.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                ,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                color = Color.Red
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .height(40.dp)
                    .padding(start = 20.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                ,
                verticalAlignment = Alignment.CenterVertically
            ){
                DisplayStarRating(rating = location.rating, width = 100, height = 90)
                Text(
                    text = "${location.review_count}",
                    modifier = Modifier
                        .padding(start = 5.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                        .align(Alignment.CenterVertically)
                    ,
                    textAlign = TextAlign.Center,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
            }

        }
    }
}

@Composable
fun LocationDetailsBackButton(onBackPress: () -> Unit){
        IconButton(onClick = onBackPress, modifier = Modifier.width(80.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,

            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    tint = Color.Black,
                    contentDescription = null
                )
                Text(
                    text = "Back",
                    modifier = Modifier
                        .padding(start = 5.dp, end = 0.dp, top = 0.dp, bottom = 0.dp),
                    color = Color.Black
                )
            }
    }


}

@Composable
fun LocationAdditionalInfo(location: YelpBusinessDetails){
    Row(
        modifier = Modifier
            .padding(start = 20.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
            .fillMaxWidth()
    ) {
        val locCategories: String = concatBusinessListOfStrings(location.categories)

        if(location.price.isNullOrBlank()){
            Text(
                modifier = Modifier
                    .padding(start = 2.dp)
                    .align(Alignment.CenterVertically)
                ,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                text = locCategories
            )

        } else {
            Text(
                modifier = Modifier
                    .padding(start = 2.dp)
                    .align(Alignment.CenterVertically)
                ,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                text = "${location.price} \u2022 $locCategories"
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
    ) {
        IsLocationOpen(isOpenNow = location.hours[0].is_open_now)
    }
}

@Composable
fun IsLocationOpen(isOpenNow: Boolean){
    if(isOpenNow){
        Text(
            text = "Open",
            color = Color.Green,
            fontWeight = FontWeight.Bold
        )
    } else {
        Text(
            text = "Closed Now",
            color = Color.Red,
            fontWeight = FontWeight.Bold
        )
    }
}
