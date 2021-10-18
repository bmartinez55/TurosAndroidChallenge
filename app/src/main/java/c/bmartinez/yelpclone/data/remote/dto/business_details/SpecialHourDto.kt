package c.bmartinez.yelpclone.data.remote.dto.business_details

import com.google.gson.annotations.SerializedName

data class SpecialHourDto(
    val date: String,
    val end: String,
    @SerializedName("is_closed") val isClosed: Any,
    @SerializedName("is_overnight") val isOverNight: Boolean,
    val start: String
)