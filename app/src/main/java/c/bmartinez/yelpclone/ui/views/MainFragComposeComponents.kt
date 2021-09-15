package c.bmartinez.yelpclone.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import c.bmartinez.yelpclone.data.model.Results
import com.skydoves.landscapist.glide.GlideImage

class MainFragComposeComponents {

    @Composable
    fun mainFragProgressDialog(isDisplayed: Boolean) {
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

    @Composable
    fun mainFragPopularList(popularLocations: MutableList<Results>) {
        LazyRow {
            items(popularLocations) { location ->
                mainFragPopularListItem(location)
            }
        }
    }

    @Preview
    @Composable
    fun mainFragPopularListItem(popularLocation: Results){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            elevation = 10.dp
        ) {
            Column(

            ) {
                GlideImage(
                    imageModel = popularLocation.image_url,
                    contentScale = ContentScale.Inside,
                    circularRevealEnabled = true
                )
            }
        }
    }
}