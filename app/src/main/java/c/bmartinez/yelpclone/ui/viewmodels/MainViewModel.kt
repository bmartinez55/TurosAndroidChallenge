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
    val pizzaData = MutableLiveData<List<Pizza>>()
    val beerData = MutableLiveData<List<YelpSearchBeerResults>>()
    var job: Job? = null
    var job2: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler{ _, exception ->
        onError("Exception handled: ${exception.localizedMessage}")
    }
    private val loading = MutableLiveData<Boolean>()

    //Calls endpoint and gets the response and either add data to list or send error message to onError method
    fun getAllPizzaLocations() {
        job = viewModelScope.launch {
            val response = yelpRepository.getAllPizzaLocations()
            try {
                if(response.isSuccessful){
                    pizzaData.postValue(response.body()?.get(0)?.restaurants)
                    loading.postValue(false)
                } else {
                    Log.d(TAG, response.message())
                    try{
                        onError("Error: ${response.message()}")
                    } catch(e: Exception) { Log.d(TAG, e.stackTraceToString())}
                }
            } catch(e: Exception) { Log.d(TAG, e.stackTraceToString()) }
        }

//        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
//            val response = yelpRepository.getAllPizzaLocations()
//            withContext(Dispatchers.Main) {
//                if(response.isSuccessful){
//                    pizzaData.postValue(response.body())
//                    loading.value = false
//                } else {
//                    onError("Error: ${response.message()} ")
//                }
//            }
//        }
    }
    //Calls endpoint and gets the response and either add data to list or send error message to onError method
    fun getAllBeerLocations() {
        job2 = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = yelpRepository.getAllBeerLocations()
            //withContext(Dispatchers.Main) {
                if(response.isSuccessful){
                    Log.d(MainViewModel::class.java.name, response.body().toString())
                    beerData.postValue(response.body())
                    loading.value = false
                } else {
                    Log.d(MainViewModel::class.java.name, response.message())
                    try{
                        onError("Error: ${response.message()}")
                    } catch(e: Exception) { Log.d(MainViewModel::class.java.name, e.localizedMessage)}
                }
            //}
        }
    }

    private fun onError(message: String) {
        try{
            errorMessage.value = message
            loading.value = false
        } catch(e: Exception) { Log.d(MainViewModel::class.java.name,"onError: " + e.localizedMessage) }

    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}