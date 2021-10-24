package c.bmartinez.yelpclone.presentation.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import c.bmartinez.yelpclone.utils.YelpConstants

@Composable
fun TestImage(imageSize: Int){
    Image(
        painter = painterResource(id = YelpConstants.DEFAULT_BUSINESS_IMAGE),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(imageSize.dp),
        contentScale = ContentScale.Crop
    )
}