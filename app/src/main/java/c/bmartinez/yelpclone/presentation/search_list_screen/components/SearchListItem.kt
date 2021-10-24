package c.bmartinez.yelpclone.presentation.search_list_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Room
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import c.bmartinez.yelpclone.R
import c.bmartinez.yelpclone.domain.model.business_search.Businesses
import c.bmartinez.yelpclone.presentation.search_list_screen.components.category_tags.CategoryTagRow
import c.bmartinez.yelpclone.presentation.utils.DisplayStarRating
import c.bmartinez.yelpclone.utils.*

@Composable
fun SearchListItem(searchItem: Businesses, onClick: () -> Unit) {//
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .border(
                width = 0.dp,
                color = Color.Transparent,
                shape = RoundedCornerShape(40.dp)
            )
            .clickable(onClick = onClick)
        ,
        elevation = 10.dp
    ) {
        Column (
            modifier = Modifier
                .padding(10.dp)
        ) {
            SearchListImage(searchItem.imageUrl)

            TitleDistanceRow(searchItem.name, searchItem.distance)

            RatingReviewsRow(searchItem.rating, searchItem.reviewCount)

            val listOfCategories = listOfTitles(searchItem.categories)
            CategoryTagRow(listOfCategories)

            CityPriceRow(searchItem.location.city, searchItem.price)

            if(searchItem.transactions.isNotEmpty()){
                Divider(color = Color.LightGray, thickness = 1.dp)
                TransactionsRow(searchItem.transactions)
            }
        }
    }
}

@Composable
private fun SearchListImage(imageUrl: String){
    imageUrl.let { url ->
        val image = loadPicture(url = url, defaultImage = YelpConstants.DEFAULT_BUSINESS_IMAGE).value
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

    //TestImage(YelpConstants.IMAGE_SIZE_ITEM)
}

@Composable
private fun TitleDistanceRow(title: String, distance: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth(.7f)
                .wrapContentWidth(Alignment.Start),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${
                String.format(
                    "%.2f",
                    (distance / YelpConstants.METERS_IN_MILE)
                )
            } mi",
            modifier = Modifier
                .padding(start = 40.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                .wrapContentWidth(align = Alignment.End)
        )
    }
}

@Composable
private fun RatingReviewsRow(rating: Double, reviews: Int){
    rating.let { rate ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DisplayStarRating(rating = rate, height = 40, width = 80)
            Text(
                modifier = Modifier
                    .padding(start = 2.dp)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                text = "$reviews Reviews",
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun CityPriceRow(city: String, price: String?){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.Room, contentDescription = null)
        Text(
            text = city,
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )

        price?.let { price ->
            if(price != "") {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    text = " • $price",
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
private fun TransactionsRow(transactions: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_check_24),
            contentDescription = null
        )
        Text(
            text = transactions.joinToString(", ")
        )
    }
}
