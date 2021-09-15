package c.bmartinez.yelpclone.ui.splash

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import c.bmartinez.yelpclone.R

class SplashComposeComponents {

    @ExperimentalFoundationApi
    @Composable
    fun splashParentLayout() {
        MaterialTheme() {
            Column (
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                Arrangement.Center,
                Alignment.CenterHorizontally
            ) {
                splashImage()
            }
        }
    }

    @Composable
    fun splashImage() {
        Image(
            painter = painterResource(id = R.drawable.yelp_logo),
            contentDescription = "Splash Yelp Logo",
            modifier = Modifier
                //Set image size to W: 350 dp and H: 200 dp
                .size(350.dp, 200.dp)
        )
    }
}