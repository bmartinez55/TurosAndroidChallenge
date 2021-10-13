package c.bmartinez.yelpclone.data.model

import com.google.gson.annotations.SerializedName

data class YelpSearchResults(
    @SerializedName("total") val total: Int,
    @SerializedName("businesses") val restaurants: List<Results>
)

data class Results(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("display_phone") val phone: String,
    @SerializedName("image_url") val image_url: String,
    @SerializedName("distance") val distance: Double,
    @SerializedName("location") val location: ResultsLocation,
    @SerializedName("review_count") val review_count: Int,
    @SerializedName("rating") val rating: Double,
    @SerializedName("price") val price: String,
    @SerializedName("categories") val categories: List<ResultsCategories>
)

data class ResultsLocation(
    @SerializedName("address1") val address: String,
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String,
    @SerializedName("zip_code") val zipCode: String
)

data class ResultsCategories(
    @SerializedName("title") val title: String
)
