package c.bmartinez.yelpclone.data.model

import com.google.gson.annotations.SerializedName

data class YelpSearchPizzaResults(
    @SerializedName("total") val total: Int,
    @SerializedName("businesses") val restaurants: List<Pizza>
)

data class Pizza(
    @SerializedName("name") val name: String,
    @SerializedName("display_phone") val phone: String,
    @SerializedName("image_url") val image_url: String,
    @SerializedName("distance") val distance: Double,
    @SerializedName("location") val location: PizzaLocation
)

data class PizzaLocation (
    @SerializedName("address1") val address: String,
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String,
    @SerializedName("zip_code") val zipCode: String
)
