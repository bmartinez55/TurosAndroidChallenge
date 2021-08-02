package c.bmartinez.turosandroidchallenge.data.repo

import c.bmartinez.turosandroidchallenge.data.services.RetrofitService
import c.bmartinez.turosandroidchallenge.utils.YelpConstants

/*
    This class holds the endpoint calls with query search data
 */
class YelpRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllPizzaLocations() = retrofitService.getAllPizzaLocations(
        "Bearer ${YelpConstants().api_key}",
        "pizza",
        YelpConstants().officeAddress,
        YelpConstants().searchRadius
    )

    suspend fun getAllBeerLocations() = retrofitService.getAllBeerLocations(
        "Bearer ${YelpConstants().api_key}",
        "beer",
        YelpConstants().officeAddress,
        YelpConstants().searchRadius
    )
}