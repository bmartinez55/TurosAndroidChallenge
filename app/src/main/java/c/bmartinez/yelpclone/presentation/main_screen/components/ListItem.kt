package c.bmartinez.yelpclone.presentation.main_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import c.bmartinez.yelpclone.domain.model.business_search.Businesses
import c.bmartinez.yelpclone.presentation.utils.DisplayStarRating
import c.bmartinez.yelpclone.utils.DEFAULT_BUSINESS_IMAGE
import c.bmartinez.yelpclone.utils.LoadPicture

@Composable
fun PopularListItem(
    business: Businesses,
    onClick: () -> Unit
){
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
            business.imageUrl.let { url ->
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
                text = business.name,
                modifier = Modifier
                    .padding(start = 1.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                    .fillMaxWidth(.5f)
                    .wrapContentWidth(Alignment.Start),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
            business.rating.let { rate ->
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
                        text = "${business.reviewCount} Reviews",
                        color = Color.Gray
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(business.price.isNullOrBlank()){
                    Text(
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .align(Alignment.CenterVertically)
                        ,
                        textAlign = TextAlign.Center,
                        text = business.categories[0].title,
                        color = Color.Gray
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .align(Alignment.CenterVertically)
                        ,
                        textAlign = TextAlign.Center,
                        text = "${business.price} \u2022 ${business.categories[0].title}",
                        color = Color.Gray
                    )
                }
            }
        }
    }
}