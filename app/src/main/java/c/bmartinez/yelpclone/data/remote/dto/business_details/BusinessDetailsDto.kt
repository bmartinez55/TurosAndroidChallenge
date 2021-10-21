package c.bmartinez.yelpclone.data.remote.dto.business_details

import c.bmartinez.yelpclone.data.remote.dto.common_dtos.CategoriesDto
import c.bmartinez.yelpclone.domain.model.business_detail.BusinessDetails
import com.google.gson.annotations.SerializedName

data class BusinessDetailsDto(
    val alias: String,
    val categories: List<CategoriesDto>,
    val coordinates: CoordinatesDto,
    @SerializedName("display_phone") val displayPhone: String,
    val hours: List<HourDto>,
    val id: String,
    @SerializedName("image_url")val imageUrl: String,
    @SerializedName("is_claimed") val isClaimed: Boolean,
    @SerializedName("is_closed") val isClosed: Boolean,
    val detailsLocation: DetailsLocationDto,
    val name: String,
    val phone: String,
    val photos: List<String>,
    val price: String,
    val rating: Double,
    @SerializedName("review_count") val reviewCount: Int,
    @SerializedName("special_hours") val specialHour: List<SpecialHourDto>,
    val transactions: List<String>,
    val url: String
)

fun BusinessDetailsDto.toBusinessDetails(): BusinessDetails {
    return BusinessDetails(
        categories = categories,
        displayPhone = displayPhone,
        hours = hours,
        id = id,
        imageUrl = imageUrl,
        isClosed = isClosed,
        name = name,
        price = price,
        rating = rating,
        reviewCount = reviewCount,
        transactions = transactions
    )
}