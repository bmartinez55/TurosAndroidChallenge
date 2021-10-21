package c.bmartinez.yelpclone.data.remote.dto.business_search

import com.google.gson.annotations.SerializedName

data class SearchLocationDto(
    val address1: String,
    val address2: String,
    val address3: String,
    val city: String,
    val state: String,
    @SerializedName("zip_code") val zipCode: String
)
