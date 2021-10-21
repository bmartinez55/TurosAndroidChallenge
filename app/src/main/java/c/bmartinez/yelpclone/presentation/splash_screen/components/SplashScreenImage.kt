package c.bmartinez.yelpclone.presentation.splash_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import c.bmartinez.yelpclone.R


@Composable
fun SplashImage() {
    Image(
        painter = painterResource(id = R.drawable.yelp_logo),
        contentDescription = "Splash Yelp Logo",
        modifier = Modifier
            //Set image size to W: 350 dp and H: 200 dp
            .size(350.dp, 200.dp)
    )
}