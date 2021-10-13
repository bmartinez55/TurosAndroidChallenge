package c.bmartinez.yelpclone.data.model

import com.google.gson.annotations.SerializedName

data class YelpBusinessDetails(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("image_url") val image_url: String,
    @SerializedName("is_closed") val is_closed: Boolean,
    @SerializedName("display_phone") val display_phone: String,
    @SerializedName("review_count") val review_count: Int,
    @SerializedName("categories") val categories: List<BusinessCategories>,
    @SerializedName("rating") val rating: Double,
    @SerializedName("location") val location: BusinessLocation,
    @SerializedName("coordinates") val coordinates: BusinessCoordinates,
    @SerializedName("photos") val photos: List<String>,
    @SerializedName("price") val price: String,
    @SerializedName("hours") val hours: List<BusinessTimeSlots>
)

data class BusinessTimeSlots(
    @SerializedName("open") val open: List<BusinessHours>,
    @SerializedName("hours_type") val hours_type: String,
    @SerializedName("is_open_now") val is_open_now: Boolean
)

data class BusinessHours(
    @SerializedName("is_overnight") val is_overnight: Boolean,
    @SerializedName("start") val start: String,
    @SerializedName("end") val end: String,
    @SerializedName("day") val day: Int
)

data class BusinessLocation(
    @SerializedName("address1") val address: String,
    @SerializedName("city") val city: String,
    @SerializedName("zip_code") val zip_code: String,
    @SerializedName("country") val country: String,
    @SerializedName("state") val state: String,
)

data class BusinessCoordinates(
    @SerializedName("latitude") val latitude: Float,
    @SerializedName("longitude") val longitude: Float
)

data class BusinessCategories(
    @SerializedName("title") val title: String
)
