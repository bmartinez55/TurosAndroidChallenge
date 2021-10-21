package c.bmartinez.yelpclone.presentation.search_list_screen.view_model

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import c.bmartinez.yelpclone.domain.use_case.get_search_businesses.GetSearchBusinessesUseCase
import c.bmartinez.yelpclone.utils.Resource
import c.bmartinez.yelpclone.utils.SharedPreferencesUtils
import c.bmartinez.yelpclone.utils.YelpConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchListViewModel @Inject constructor(
    private val getSearchBusinessesUseCase: GetSearchBusinessesUseCase,
    @ApplicationContext context: Context,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _state = mutableStateOf(SearchListState())
    val state: State<SearchListState> = _state

    val queryTerm = mutableStateOf("")

    init {
        queryTerm.value = savedStateHandle.get<String>(YelpConstants.PARAM_KEY_SEARCH_TERM) ?: ""
        val latitude = SharedPreferencesUtils.getFloatPref(
            context,
            SharedPreferencesUtils.COORDINATES_SPF,
            SharedPreferencesUtils.COORDINATES_LAT,
            0.0f
        )?.toDouble()

        val longitude = SharedPreferencesUtils.getFloatPref(
            context,
            SharedPreferencesUtils.COORDINATES_SPF,
            SharedPreferencesUtils.COORDINATES_LONG,
            0.0f
        )?.toDouble()

        latitude?.let { resultLatitude ->
            longitude?.let { resultLongitude ->
                getSearchBusinesses(
                    queryTerm.value,
                    resultLatitude,
                    resultLongitude
                )
            }
        }
    }

    fun getSearchBusinesses(
        searchTerm: String,
        latitude: Double,
        longitude: Double
    ) {
        getSearchBusinessesUseCase(
            searchTerm,
            latitude,
            longitude
        ).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = result.data?.let { SearchListState(businesses = it.businesses) }!!
                }
                is Resource.Error -> {
                    _state.value = SearchListState(error = result.message ?: "An unexpected error occurred.")
                }
                is Resource.Loading -> {
                    _state.value = SearchListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onQueryTermChanged(term: String) {
        this.queryTerm.value = term
    }

    fun onQueryTermClear() {
        this.queryTerm.value = ""
    }
}