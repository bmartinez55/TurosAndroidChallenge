package c.bmartinez.yelpclone.data.repo

import c.bmartinez.yelpclone.data.services.RetrofitService
import c.bmartinez.yelpclone.utils.maxListResults
import c.bmartinez.yelpclone.utils.maxPopularResults
import c.bmartinez.yelpclone.utils.searchNewRadius
import c.bmartinez.yelpclone.utils.searchPopularRadius

/*
    This class holds the endpoint calls with query search data
 */
class YelpRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getSearchResults(searchTerm: String, latitude: Double, longitude: Double) = retrofitService.getSearchResults(
        searchTerm,
        latitude,
        longitude,
        searchNewRadius,
        "distance",
        maxListResults
    )

    suspend fun getPopularLocations(latitude: Double, longitude: Double) = retrofitService.getPopularLocations(
        latitude,
        longitude,
        searchPopularRadius,
        "restaurants",
        "rating",
        maxPopularResults
    )
}