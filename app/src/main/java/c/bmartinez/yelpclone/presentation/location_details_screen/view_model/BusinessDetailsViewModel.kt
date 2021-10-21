package c.bmartinez.yelpclone.presentation.location_details_screen.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import c.bmartinez.yelpclone.domain.use_case.get_business_details.GetBusinessDetailsUseCase
import c.bmartinez.yelpclone.utils.Resource
import c.bmartinez.yelpclone.utils.YelpConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BusinessDetailsViewModel @Inject constructor(
    private val getBusinessDetailsUseCase: GetBusinessDetailsUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _state = mutableStateOf(DetailsScreenState())
    val state: State<DetailsScreenState> = _state

    init {
        savedStateHandle.get<String>(YelpConstants.PARAM_KEY_BUSINESS_ID)?.let { businessId ->
            getBusinessDetails(businessId)
        }
    }

    private fun getBusinessDetails(businessId: String) {
        getBusinessDetailsUseCase(businessId).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = result.data?.let { DetailsScreenState(business = it) }!!
                }
                is Resource.Error -> {
                    _state.value = DetailsScreenState(error = result.message ?:
                    "An unexpected error occurred.")
                }
                is Resource.Loading -> {
                    _state.value = DetailsScreenState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}