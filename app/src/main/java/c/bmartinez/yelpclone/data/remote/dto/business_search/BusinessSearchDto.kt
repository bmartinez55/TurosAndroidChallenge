package c.bmartinez.yelpclone.data.remote.dto.business_search

import c.bmartinez.yelpclone.domain.model.business_search.BusinessSearch

data class BusinessSearchDto(
    val businesses: List<SearchResults>,
    val regionDto: RegionDto,
    val total: Int
)

fun BusinessSearchDto.toBusinessSearch(): BusinessSearch {
    return BusinessSearch(
        businesses = businesses.map {
            it.toBusinesses()
        }
    )
}