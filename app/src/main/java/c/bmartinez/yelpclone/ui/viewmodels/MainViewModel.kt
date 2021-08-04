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
    val errorMessage = MutableLiveData<String>()
    val pizzaData = MutableLiveData<List<Results>>()
    val beerData = MutableLiveData<List<YelpSearchResults>>()

    private val loading = MutableLiveData<Boolean>()

    //Calls endpoint and gets the response and either add data to list or send error message to onError method
    fun getAllPizzaLocations() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = yelpRepository.getAllPizzaLocations()
                if(response.body() != null){
                    pizzaData.postValue(response.body()!!.restaurants.toList())
                    loading.postValue(false)
                } else {
                    Log.d(TAG, response.message())
                }
            } catch(e: Exception) { Log.d(TAG,"Inside catch: ${e.stackTraceToString()}") }
        }
    }

    //Calls endpoint and gets the response and either add data to list or send error message to onError method
    fun getAllBeerLocations() {
        viewModelScope.launch {
            val response = yelpRepository.getAllBeerLocations()
            //withContext(Dispatchers.Main) {
                if(response.isSuccessful){
                    Log.d(MainViewModel::class.java.name, response.body().toString())
                    //beerData.postValue(response.body())
                    //loading.value = false
                } else {
                    Log.d(MainViewModel::class.java.name, response.message())
//                    try{
//                        onError("Error: ${response.message()}")
//                    } catch(e: Exception) { Log.d(MainViewModel::class.java.name, e.localizedMessage)}
                }
            //}
        }
    }

//    private fun onError(message: String) {
//        try{
//            errorMessage.value = message
//            loading.value = false
//        } catch(e: Exception) { Log.d(MainViewModel::class.java.name,"onError: " + e.localizedMessage) }
//
//    }

}