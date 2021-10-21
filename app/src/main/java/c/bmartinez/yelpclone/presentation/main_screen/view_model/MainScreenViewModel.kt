package c.bmartinez.yelpclone.presentation.main_screen.view_model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import c.bmartinez.yelpclone.domain.use_case.get_popular_locations.GetPopularLocationsUseCase
import c.bmartinez.yelpclone.utils.Resource
import c.bmartinez.yelpclone.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getPopularLocationsUseCase: GetPopularLocationsUseCase,
    @ApplicationContext context: Context,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    val queryTerm = mutableStateOf("")

    init {
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
                getPopularLocations(resultLatitude, resultLongitude)
            }
        }
    }

    private fun getPopularLocations(latitude: Double, longitude: Double) {
        getPopularLocationsUseCase(latitude = latitude, longitude = longitude).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = result.data?.let { MainScreenState(businesses = it.businesses) }!!
                }
                is Resource.Error -> {
                    _state.value = MainScreenState(error = result.message ?: "An unexpected error occurred.")
                }
                is Resource.Loading -> {
                    _state.value = MainScreenState(isLoading = true)
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