package c.bmartinez.yelpclone.presentation.location_details_screen.view_model

import c.bmartinez.yelpclone.domain.model.business_detail.BusinessDetails

data class DetailsScreenState(
    val isLoading: Boolean = false,
    val business: BusinessDetails? = null,
    val error: String = ""
)
