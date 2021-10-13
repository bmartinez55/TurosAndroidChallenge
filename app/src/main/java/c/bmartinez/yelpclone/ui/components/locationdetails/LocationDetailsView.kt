package c.bmartinez.yelpclone.ui.components.locationdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import c.bmartinez.yelpclone.data.model.*


@Composable
fun LocationDetailsView(
    location: YelpBusinessDetails,
    navController: NavController
) {
    val scrollState = rememberScrollState()

    //Smooth scroll to specified pixels on first composition
    LaunchedEffect(Unit) { scrollState.animateScrollTo(10000) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        LocationInfo(location = location, navController = navController)
        LocationAdditionalInfo(location = location)
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewLocationDetailsView() {
    LocationDetailsView(location =
        YelpBusinessDetails(
            "",
            "Burger King",
            "",
            false,
            "",
            555,
            listOf(BusinessCategories("Smoothies"), BusinessCategories("Burgers")),
            4.0,
            BusinessLocation(
                "",
                "",
                "",
                "",
                ""
            ),
            BusinessCoordinates(0.0f, 0.0f),
            listOf(),
            "$$",
            listOf(
                BusinessTimeSlots(
                    listOf(
                        BusinessHours(
                            false,
                            "0800",
                            "1600",
                            0
                        )
                    ),
                    "",
                    true
                ),
                BusinessTimeSlots(
                    listOf(
                        BusinessHours(
                            false,
                            "0800",
                            "1600",
                            1
                        )
                    ),
                    "",
                    true
                ),
                BusinessTimeSlots(
                    listOf(
                        BusinessHours(
                            false,
                            "0800",
                            "1600",
                            2
                        )
                    ),
                    "",
                    true
                )
            )
        ),
        NavController(LocalContext.current)
    )
}