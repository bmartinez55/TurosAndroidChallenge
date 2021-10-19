package c.bmartinez.yelpclone.utils

import c.bmartinez.yelpclone.data.model.*
import c.bmartinez.yelpclone.data.remote.dto.business_search.CategoriesDto
import c.bmartinez.yelpclone.domain.model.business_search.BusinessSearch
import c.bmartinez.yelpclone.domain.model.business_search.Businesses
import com.google.gson.annotations.SerializedName

val TEST_BUSINESS_SEARCH_OBJECT = Businesses(
    4.5,
    "$$$$",
    "businessID",
    listOf(
        CategoriesDto(
            "test-1",
            "Test 1'"
        ),
        CategoriesDto(
            "test-2",
            "Test 2"
        )
    ),
    1452,
    "Burger King",
    "a;sdlkj;alskdfjg.jpg",
    45131.55,
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