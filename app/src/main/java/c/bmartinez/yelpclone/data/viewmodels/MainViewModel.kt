@file:Suppress("PackageDirectoryMismatch")
//package c.bmartinez.yelpclone.data.viewmodels
//
//import android.util.Log
//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import c.bmartinez.yelpclone.data.model.*
//import c.bmartinez.yelpclone.network.repository.YelpRepository
//import c.bmartinez.yelpclone.utils.EMPTY_BUSINESS_DETAILS_OBJECT
//import kotlinx.coroutines.*
//import kotlinx.coroutines.flow.emptyFlow
//
///*
//    Primary viewmodel that connects the model and the view. Calls both pizza and beer endpoints with coroutines
// */
//class MainViewModel constructor(private val yelpRepository: YelpRepository): ViewModel() {
//
//    private val TAG: String = MainViewModel::class.java.name
//    val popularLocationResults: MutableState<List<Results>> = mutableStateOf(listOf())
//    val locationBusinessDetails: MutableState<YelpBusinessDetails> = mutableStateOf(EMPTY_BUSINESS_DETAILS_OBJECT)
//    var loading = mutableStateOf(false)
//    val searchTermQuery = mutableStateOf("")
//
//    //Calls endpoint to retrieve business details
//    fun getBusinessDetails(id: String){
//        viewModelScope.launch(Dispatchers.IO) {
//            try{
//                loading.value = true
//                val response = yelpRepository.getBusinessDetails(id)
//                if(response.body() != null){
//                    locationBusinessDetails.value = response.body()!!
//                } else {
//                    Log.d(TAG, response.message())
//                }
//                loading.value = false
//            } catch (e: Exception) {
//                loading.value = false
//                Log.d(TAG, "Inside businessDetails catch: ${e.stackTraceToString()}")
//            }
//        }
//    }
//
//    //Calls search endpoint to get locations based on the search term
//    fun getSearchResults(searchTerm: String, latitude: Double?, longitude: Double?) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try{
//                loading.value = true
//                val response = yelpRepository.getSearchResults(searchTerm, latitude!!, longitude!!)
//                if(response.body() != null){
//                    //data.postValue(response.body()!!.restaurants.toList())
//                } else {
//                    Log.d(TAG, response.message())
//                }
//                loading.value = false
//            } catch(e: Exception) {
//                loading.value = false
//                Log.d(TAG, "Inside searchResults catch: ${e.stackTraceToString()}")
//            }
//        }
//    }
//
//    //Calls popular endpoint to get popular locations near by
//    fun getPopularLocations(latitude: Double?, longitude: Double?) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                loading.value = true
//                val response = yelpRepository.getPopularLocations(latitude!!, longitude!!)
//                if(response.body() != null){
//                    popularLocationResults.value = response.body()!!.restaurants
//                } else {
//                    Log.d(TAG, response.message())
//                }
//                loading.value = false
//            } catch(e: Exception) {
//                loading.value = false
//                Log.d(TAG,"Inside catch: ${e.stackTraceToString()}")
//            }
//        }
//    }
//
//    fun onSearchTermChanged(newTerm: String) {
//        this.searchTermQuery.value = newTerm
//    }
//}