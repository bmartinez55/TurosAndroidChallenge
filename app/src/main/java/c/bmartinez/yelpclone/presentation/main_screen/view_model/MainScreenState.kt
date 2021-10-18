package c.bmartinez.yelpclone.presentation.main_screen.view_model

import c.bmartinez.yelpclone.domain.model.business_search.BusinessSearch
import c.bmartinez.yelpclone.domain.model.business_search.Businesses
import kotlinx.coroutines.flow.emptyFlow

data class MainScreenState(
    val isLoading: Boolean = false,
    val businesses: List<Businesses> = emptyList(),
    val error: String = ""
)
