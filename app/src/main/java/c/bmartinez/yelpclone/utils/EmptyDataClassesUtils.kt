package c.bmartinez.yelpclone.utils

import c.bmartinez.yelpclone.data.model.*
import com.google.gson.annotations.SerializedName

val TEST_RESULTS_OBJECT = Results(
    "",
    "Burger King",
    "(831) 123-4567",
    ";lkasjdf;lkjas.jpg",
    1452.45,
    ResultsLocation(
        "123 I'm Here St",
        "Salinas",
        "CA",
        "93906"
    ),
    1234,
    4.5,
    "$$$",
    listOf(
        ResultsCategories("Smoothie"),
        ResultsCategories("Juice")
    ),
   listOf("Deliver", "Pick-up")
)

val EMPTY_BUSINESS_DETAILS_OBJECT = YelpBusinessDetails(
    "",
    "",
    "",
    false,
    "",
    0,
    listOf(),
    0.0,
    BusinessLocation(
        "",
        "",
        "",
        "",
        ""
    ),
    BusinessCoordinates(0.0f, 0.0f),
    listOf(),
    "",
    listOf()
)