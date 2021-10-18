package c.bmartinez.yelpclone.domain.model.business_search

import c.bmartinez.yelpclone.data.remote.dto.business_search.CategoriesDto

data class Businesses(
    val rating: Double,
    val price: String,
    val id: String,
    val categories: List<CategoriesDto>,
    val reviewCount: Int,
    val name: String,
    val imageUrl: String,
    val distance: Double,
)
