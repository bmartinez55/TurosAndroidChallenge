package c.bmartinez.yelpclone.ui.splash

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import c.bmartinez.yelpclone.R

@Preview(showSystemUi = true)
@ExperimentalFoundationApi
@Composable
fun SplashParentLayout() {
    Column (
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        SplashImage()
    }
}

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