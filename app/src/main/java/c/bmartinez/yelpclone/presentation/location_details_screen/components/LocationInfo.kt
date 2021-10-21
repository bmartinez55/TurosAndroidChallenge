package c.bmartinez.yelpclone.presentation.location_details_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import c.bmartinez.yelpclone.domain.model.business_detail.BusinessDetails
import c.bmartinez.yelpclone.utils.*

@Composable
fun LocationAdditionalInfo(location: BusinessDetails?){
    Row(
        modifier = Modifier
            .padding(start = 20.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
            .fillMaxWidth()
    ) {
        val locCategories: String? = location?.let { concatBusinessListOfStrings(it.categories) }

        if(location?.price.isNullOrBlank()){
            locCategories?.let {
                Text(
                    modifier = Modifier
                        .padding(start = 2.dp)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    text = it
                )
            }

        } else {
            Text(
                modifier = Modifier
                    .padding(start = 2.dp)
                    .align(Alignment.CenterVertically)
                ,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                text = "${location?.price} \u2022 $locCategories"
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
    ) {
        IsLocationOpen(isOpenNow = location?.hours?.get(0)?.isOpenNow)
    }
}

@Composable
fun IsLocationOpen(isOpenNow: Boolean?){
    if(isOpenNow == true){
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
