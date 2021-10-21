package c.bmartinez.yelpclone.data.remote.dto.business_details

import com.google.gson.annotations.SerializedName

data class DetailsLocationDto(
    val address1: String,
    val address2: String,
    val address3: String,
    val city: String,
    val country: String,
    @SerializedName("cross_streets") val crossStreets: String,
    @SerializedName("display_address") val displayAddress: List<String>,
    val state: String,
    @SerializedName("zip_code") val zipCode: String
)