package c.bmartinez.yelpclone.data.remote.dto.business_details

import com.google.gson.annotations.SerializedName

data class OpenDto(
    val day: Int,
    val end: String,
    @SerializedName("is_overnight") val isOverNight: Boolean,
    val start: String
)