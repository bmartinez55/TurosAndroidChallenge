package c.bmartinez.yelpclone.data.repository

import c.bmartinez.yelpclone.data.remote.RetrofitApi
import c.bmartinez.yelpclone.data.remote.dto.business_search.BusinessSearchDto
import c.bmartinez.yelpclone.domain.repository.BusinessRepository
import c.bmartinez.yelpclone.utils.YelpConstants
import javax.inject.Inject

class BusinessRepositoryImpl @Inject constructor(
    private val retrofitApi: RetrofitApi
): BusinessRepository{

    override suspend fun getPopularLocations(
        latitude: Double,
        longitude: Double
    ) = retrofitApi.getPopularLocations(
            latitude,
            longitude,
            YelpConstants.SEARCH_POPULAR_RADIUS,
            "restaurants",
            "rating",
            YelpConstants.MAX_POPULAR_RESULT_SIZE
        )

    override suspend fun getBusinessDetails(businessId: String) = retrofitApi
        .getBusinessDetails(businessId)

    override suspend fun getSearchBusinesses(
        searchTerm: String,
        latitude: Double,
        longitude: Double
    ) = retrofitApi.getSearchResults(
        searchTerm,
        latitude,
        longitude,
        YelpConstants.SEARCH_NEW_RADIUS,
        "rating",
        YelpConstants.MAX_SEARCH_LIST_SIZE
    )
}