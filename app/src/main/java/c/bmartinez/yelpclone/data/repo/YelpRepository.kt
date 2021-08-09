package c.bmartinez.yelpclone.data.repo

import c.bmartinez.yelpclone.data.services.RetrofitService
import c.bmartinez.yelpclone.utils.YelpConstants

/*
    This class holds the endpoint calls with query search data
 */
class YelpRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getSearchResults(searchTerm: String, latitude: Double, longitude: Double) = retrofitService.getSearchResults(
        searchTerm,
        latitude,
        longitude,
        YelpConstants().searchRadius,
        "distance",
        YelpConstants().maxListResults
    )

    suspend fun getPopularLocations(latitude: Double, longitude: Double) = retrofitService.getPopularLocations(
        latitude,
        longitude,
        YelpConstants().searchRadius,
        "rating",
        YelpConstants().maxPopularResults
    )

//    suspend fun getAllBeerLocations() = retrofitService.getAllBeerLocations(
//        "beer",
//        YelpConstants().officeAddress,
//        YelpConstants().searchRadius
//    )
}