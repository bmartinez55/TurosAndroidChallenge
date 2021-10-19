package c.bmartinez.yelpclone.presentation.main_screen.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import c.bmartinez.yelpclone.domain.use_case.get_popular_locations.GetPopularLocationsUseCase
import c.bmartinez.yelpclone.utils.PARAM_KEY_DEVICE_LATITUDE
import c.bmartinez.yelpclone.utils.PARAM_KEY_DEVICE_LONGITUDE
import c.bmartinez.yelpclone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getPopularLocationsUseCase: GetPopularLocationsUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    init {
        val latitude = savedStateHandle.get<Double>(PARAM_KEY_DEVICE_LATITUDE)
        val longitude = savedStateHandle.get<Double>(PARAM_KEY_DEVICE_LONGITUDE)
        //getPopularLocations(latitude, longitude)
    }

    private fun getPopularLocations(latitude: Double, longitude: Double) {
        getPopularLocationsUseCase(latitude = latitude, longitude = longitude).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = result.data?.let { MainScreenState(businesses = it.businesses) }!!
                }
                is Resource.Error -> {
                    _state.value = MainScreenState(error = result.message ?:
                    "An unexpected error occurred.")
                }
                is Resource.Loading -> {
                    _state.value = MainScreenState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}