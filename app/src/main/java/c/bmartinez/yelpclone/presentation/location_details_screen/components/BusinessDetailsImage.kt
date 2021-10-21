package c.bmartinez.yelpclone.presentation.location_details_screen.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import c.bmartinez.yelpclone.domain.model.business_detail.BusinessDetails
import c.bmartinez.yelpclone.presentation.utils.DisplayStarRating
import c.bmartinez.yelpclone.utils.DEFAULT_BUSINESS_IMAGE
import c.bmartinez.yelpclone.utils.LOCATION_DETAILS_IMAGE_HEIGHT
import c.bmartinez.yelpclone.utils.LoadPicture

@Composable
fun BusinessDetailsImage(location: BusinessDetails?, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(LOCATION_DETAILS_IMAGE_HEIGHT.dp)
    ) {
        location?.imageUrl.let{ url ->
            val image = LoadPicture(url = url, defaultImage = DEFAULT_BUSINESS_IMAGE).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(LOCATION_DETAILS_IMAGE_HEIGHT.dp)
                )
            }
        }
//        Image(
//            painter = painterResource(id = DEFAULT_BUSINESS_IMAGE),
//            contentDescription = null,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(LOCATION_DETAILS_IMAGE_HEIGHT.dp)
//            ,
//            contentScale = ContentScale.Crop
//        )

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
            location?.let {
                Text(
                    text = it.name,
                    modifier = Modifier
                        .padding(start = 20.dp, end = 0.dp, top = 0.dp, bottom = 0.dp),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp,
                    color = Color.Red
                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .height(40.dp)
                    .padding(start = 20.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                ,
                verticalAlignment = Alignment.CenterVertically
            ){
                location?.let { DisplayStarRating(rating = it.rating, width = 100, height = 90) }
                Text(
                    text = "${location?.reviewCount}",
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