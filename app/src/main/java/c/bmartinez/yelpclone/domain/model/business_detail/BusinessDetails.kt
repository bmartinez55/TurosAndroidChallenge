package c.bmartinez.yelpclone.domain.model.business_detail

import c.bmartinez.yelpclone.data.remote.dto.business_details.*
import c.bmartinez.yelpclone.data.remote.dto.common_dtos.CategoriesDto
import com.google.gson.annotations.SerializedName

data class BusinessDetails(
    val categories: List<CategoriesDto>,
    val displayPhone: String,
    val hours: List<HourDto>,
    val id: String,
    @SerializedName("image_url")val imageUrl: String,
    @SerializedName("is_closed") val isClosed: Boolean,
    val name: String,
    val price: String,
    val rating: Double,
    @SerializedName("review_count") val reviewCount: Int,
    val transactions: List<String>
)
