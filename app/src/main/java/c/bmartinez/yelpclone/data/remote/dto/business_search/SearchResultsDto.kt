package c.bmartinez.yelpclone.data.remote.dto.business_search

import c.bmartinez.yelpclone.domain.model.business_search.Businesses
import com.google.gson.annotations.SerializedName

data class SearchResults(
    val rating: Double,
    val price: String,
    val phone: String,
    val id: String,
    val alias: String,
    @SerializedName("is_closed") val isClosed: Boolean,
    val categories: List<CategoriesDto>,
    @SerializedName("review_count") val reviewCount: Int,
    val name: String,
    val url: String,
    val coordinatesDto: CoordinatesDto,
    @SerializedName("image_url") val imageUrl: String,
    val locationDto: LocationDto,
    val distance: Double,
    val transactions: List<String>
)

fun SearchResults.toBusinesses(): Businesses {
    return Businesses(
        rating = rating,
        price = price,
        id = id,
        categories = categories,
        reviewCount = reviewCount,
        name = name,
        imageUrl = imageUrl,
        distance = distance
    )
}
