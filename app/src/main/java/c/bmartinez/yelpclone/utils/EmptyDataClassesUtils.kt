package c.bmartinez.yelpclone.utils

import c.bmartinez.yelpclone.data.remote.dto.business_search.SearchLocationDto
import c.bmartinez.yelpclone.data.remote.dto.common_dtos.CategoriesDto
import c.bmartinez.yelpclone.domain.model.business_search.Businesses

object EmptyDataClassesUtils {
    val TEST_BUSINESS_SEARCH_OBJECT = Businesses(
        4.5,
        "$$$",
        "businessID",
        listOf(
            CategoriesDto(
                "test-1",
                "Test 1"
            ),
            CategoriesDto(
                "test-2",
                "Test 2"
            )
        ),
        1452,
        "Burger King",
        "a;sdlkj;alskdfjg.jpg",
        SearchLocationDto(
            "123 address1 St",
            "123 address2 Dr",
            "123 address3 Blvd",
            "Salinas",
            "CA",
            "93907"
        ),
        12343.23,
        listOf("pick-up","delivery","take-out")
    )
}

