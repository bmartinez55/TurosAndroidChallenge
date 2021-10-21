package c.bmartinez.yelpclone.presentation.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import c.bmartinez.yelpclone.R

@Composable
fun DisplayStarRating(rating: Double, height: Int, width: Int){
    when(rating){
        0.0 -> Image(
            modifier = Modifier
                .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .height(height.dp)
                .width(width.dp)
            ,
            bitmap = ImageBitmap.imageResource(id = R.drawable.stars_regular_0),
            contentDescription = null,
        )
        1.0 -> Image(
            modifier = Modifier
                .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .height(height.dp)
                .width(width.dp)
            ,
            bitmap = ImageBitmap.imageResource(id = R.drawable.stars_regular_1),
            contentDescription = null
        )
        1.5 -> Image(
            modifier = Modifier
                .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .height(height.dp)
                .width(width.dp)
            ,
            bitmap = ImageBitmap.imageResource(id = R.drawable.stars_regular_1_half),
            contentDescription = null
        )
        2.0 -> Image(
            modifier = Modifier
                .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .height(height.dp)
                .width(width.dp)
            ,
            bitmap = ImageBitmap.imageResource(id = R.drawable.stars_regular_2),
            contentDescription = null
        )
        2.5 -> Image(
            modifier = Modifier
                .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .height(height.dp)
                .width(width.dp)
            ,
            bitmap = ImageBitmap.imageResource(id = R.drawable.stars_regular_2_half),
            contentDescription = null
        )
        3.0 -> Image(
            modifier = Modifier
                .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .height(height.dp)
                .width(width.dp)
            ,
            bitmap = ImageBitmap.imageResource(id = R.drawable.stars_regular_3),
            contentDescription = null
        )
        3.5 -> Image(
            modifier = Modifier
                .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .height(height.dp)
                .width(width.dp)
            ,
            bitmap = ImageBitmap.imageResource(id = R.drawable.stars_regular_3_half),
            contentDescription = null
        )
        4.0 -> Image(
            modifier = Modifier
                .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .height(height.dp)
                .width(width.dp)
            ,
            bitmap = ImageBitmap.imageResource(id = R.drawable.stars_regular_4),
            contentDescription = null
        )
        4.5 -> Image(
            modifier = Modifier
                .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .height(height.dp)
                .width(width.dp)
            ,
            bitmap = ImageBitmap.imageResource(id = R.drawable.stars_regular_4_half),
            contentDescription = null
        )
        5.0 -> Image(
            modifier = Modifier
                .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .height(height.dp)
                .width(width.dp)
            ,
            bitmap = ImageBitmap.imageResource(id = R.drawable.stars_regular_5),
            contentDescription = null
        )
    }
}