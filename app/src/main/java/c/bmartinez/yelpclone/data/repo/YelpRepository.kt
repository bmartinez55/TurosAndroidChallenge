package c.bmartinez.yelpclone.data.repo

import c.bmartinez.yelpclone.data.services.RetrofitService
import c.bmartinez.yelpclone.utils.YelpConstants

/*
    This class holds the endpoint calls with query search data
 */
class YelpRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getSearchResults(searchTerm: String) = retrofitService.getSearchResults(
        searchTerm,
        YelpConstants().officeAddress,
        YelpConstants().searchRadius,
        "distance",
        YelpConstants().maxResults
    )

    suspend fun getPopularLocations() = retrofitService.getPopularLocations(
        YelpConstants().officeAddress,
        YelpConstants().searchRadius,
        "rating",
        YelpConstants().maxResults
    )

//    suspend fun getAllBeerLocations() = retrofitService.getAllBeerLocations(
//        "beer",
//        YelpConstants().officeAddress,
//        YelpConstants().searchRadius
//    )
}