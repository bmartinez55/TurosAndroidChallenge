package c.bmartinez.yelpclone.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import c.bmartinez.yelpclone.data.model.*
import c.bmartinez.yelpclone.data.repo.YelpRepository
import com.google.gson.Gson
import kotlinx.coroutines.*
import org.json.JSONObject

/*
    Primary viewmodel that connects the model and the view. Calls both pizza and beer endpoints with coroutines
 */
class MainViewModel constructor(private val yelpRepository: YelpRepository): ViewModel() {

    private val TAG: String = MainViewModel::class.java.name
    val data = MutableLiveData<List<Results>>()
    val popularLocationResults: MutableState<List<Results>> = mutableStateOf(listOf())
    var loading = mutableStateOf(false)
    val searchTermQuery = mutableStateOf("")

    //Calls search endpoint to get locations based on the search term
    fun getSearchResults(searchTerm: String, latitude: Double?, longitude: Double?) {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                loading.value = true
                val response = yelpRepository.getSearchResults(searchTerm, latitude!!, longitude!!)
                if(response.body() != null){
                    data.postValue(response.body()!!.restaurants.toList())
                } else {
                    Log.d(TAG, response.message())
                }
                loading.value = false
            } catch(e: Exception) {
                loading.value = false
                Log.d(TAG, "Inside searchResults catch: ${e.stackTraceToString()}")
            }
        }
    }

    //Calls popular endpoint to get popular locations near by
    fun getPopularLocations(latitude: Double?, longitude: Double?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                loading.value = true
                val response = yelpRepository.getPopularLocations(latitude!!, longitude!!)
                if(response.body() != null){
                    data.postValue(response.body()!!.restaurants.toList())
                    popularLocationResults.value = response.body()!!.restaurants
                } else {
                    Log.d(TAG, response.message())
                }
                loading.value = false
            } catch(e: Exception) {
                loading.value = false
                Log.d(TAG,"Inside catch: ${e.stackTraceToString()}")
            }
        }
    }

    //Calls endpoint and gets the response and either add data to list or send error message to onError method
//    fun getAllBeerLocations() {
//        viewModelScope.launch {
//            val response = yelpRepository.getAllBeerLocations()
//            //withContext(Dispatchers.Main) {
//                if(response.isSuccessful){
//                    Log.d(MainViewModel::class.java.name, response.body().toString())
//                    //beerData.postValue(response.body())
//                    //loading.value = false
//                } else {
//                    Log.d(MainViewModel::class.java.name, response.message())
//                    try{
//                        onError("Error: ${response.message()}")
//                    } catch(e: Exception) { Log.d(MainViewModel::class.java.name, e.localizedMessage)}
//                }
//            //}
//        }
//    }

    fun onSearchTermChanged(newTerm: String) {
        this.searchTermQuery.value = newTerm
    }
}