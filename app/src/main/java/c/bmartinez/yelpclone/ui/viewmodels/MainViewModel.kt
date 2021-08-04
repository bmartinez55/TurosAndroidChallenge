package c.bmartinez.yelpclone.ui.viewmodels

import android.util.Log
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

    val TAG = MainViewModel::class.java.name
    val data = MutableLiveData<List<Results>>()

    //Calls search endpoint to get locations based on the search term
    fun getSearchResults(searchTerm: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val response = yelpRepository.getSearchResults(searchTerm)
                if(response.body() != null){
                    data.postValue(response.body()!!.restaurants.toList())
                } else {
                    Log.d(TAG, response.message())
                }
            } catch(e: Exception) { Log.d(TAG, "Inside searchResults catch: ${e.stackTraceToString()}")}
        }
    }

    //Calls popular endpoint to get popular locations near by
    fun getPopularLocations() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = yelpRepository.getPopularLocations()
                if(response.body() != null){
                    data.postValue(response.body()!!.restaurants.toList())
                } else {
                    Log.d(TAG, response.message())
                }
            } catch(e: Exception) { Log.d(TAG,"Inside catch: ${e.stackTraceToString()}") }
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

}