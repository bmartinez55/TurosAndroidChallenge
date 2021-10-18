package c.bmartinez.yelpclone.domain.repository

import c.bmartinez.yelpclone.data.remote.dto.business_details.BusinessDetailsDto
import c.bmartinez.yelpclone.data.remote.dto.business_search.BusinessSearchDto

interface BusinessRepository {

    suspend fun getPopularLocations(
        latitude: Double,
        longitude: Double
    ): BusinessSearchDto

    suspend fun getBusinessDetails(businessId: String): BusinessDetailsDto
}