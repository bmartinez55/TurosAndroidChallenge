package c.bmartinez.yelpclone.data.remote.dto.business_details

import com.google.gson.annotations.SerializedName

data class HourDto(
    @SerializedName("hours_type") val hoursType: String,
    @SerializedName("is_open_now")val isOpenNow: Boolean,
    val openDto: List<OpenDto>
)