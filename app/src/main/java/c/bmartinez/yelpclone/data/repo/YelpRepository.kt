package c.bmartinez.yelpclone.data.repo

import c.bmartinez.yelpclone.data.services.RetrofitService
import c.bmartinez.yelpclone.utils.YelpConstants

/*
    This class holds the endpoint calls with query search data
 */
class YelpRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllPizzaLocations() = retrofitService.getAllPizzaLocations(
        "pizza",
        YelpConstants().officeAddress,
        YelpConstants().searchRadius,
        "distance"
    )

    suspend fun getAllBeerLocations() = retrofitService.getAllBeerLocations(
        "beer",
        YelpConstants().officeAddress,
        YelpConstants().searchRadius
    )
}