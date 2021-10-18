package c.bmartinez.yelpclone.data.repository

import c.bmartinez.yelpclone.data.remote.RetrofitApi
import c.bmartinez.yelpclone.data.remote.dto.business_details.BusinessDetailsDto
import c.bmartinez.yelpclone.data.remote.dto.business_search.BusinessSearchDto
import c.bmartinez.yelpclone.domain.repository.BusinessRepository
import c.bmartinez.yelpclone.utils.maxPopularResults
import c.bmartinez.yelpclone.utils.searchPopularRadius
import javax.inject.Inject

class BusinessRepositoryImpl @Inject constructor(
    private val retrofitApi: RetrofitApi
): BusinessRepository{

    override suspend fun getPopularLocations(
        latitude: Double,
        longitude: Double
    ): BusinessSearchDto {
        return retrofitApi.getPopularLocations(
            latitude,
            longitude,
            searchPopularRadius,
            "restaurants",
            "rating",
            maxPopularResults
        )
    }

    override suspend fun getBusinessDetails(businessId: String): BusinessDetailsDto {
        TODO("Not yet implemented")
    }

}