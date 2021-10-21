package c.bmartinez.yelpclone.presentation.search_list_screen.view_model

import c.bmartinez.yelpclone.domain.model.business_search.Businesses

data class SearchListState(
    val isLoading: Boolean = false,
    val businesses: List<Businesses> = emptyList(),
    val error: String = ""
)
