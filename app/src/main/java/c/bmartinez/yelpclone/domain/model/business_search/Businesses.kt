package c.bmartinez.yelpclone.domain.model.business_search

import c.bmartinez.yelpclone.data.remote.dto.business_search.SearchLocationDto
import c.bmartinez.yelpclone.data.remote.dto.common_dtos.CategoriesDto

data class Businesses(
    val rating: Double,
    val price: String?,
    val id: String,
    val categories: List<CategoriesDto>,
    val reviewCount: Int,
    val name: String,
    val imageUrl: String,
    val location: SearchLocationDto,
    val distance: Double,
    val transactions: List<String>
)
