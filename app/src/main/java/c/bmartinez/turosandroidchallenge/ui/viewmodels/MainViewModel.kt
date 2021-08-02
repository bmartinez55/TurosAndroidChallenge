package c.bmartinez.turosandroidchallenge.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import c.bmartinez.turosandroidchallenge.data.model.*
import c.bmartinez.turosandroidchallenge.data.repo.YelpRepository
import kotlinx.coroutines.*

/*
    Primary viewmodel that connects the model and the view. Calls both pizza and beer endpoints with coroutines
 */
class MainViewModel constructor(private val yelpRepository: YelpRepository): ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val pizzaData = MutableLiveData<List<Data>>()
    val beerData = MutableLiveData<List<Data>>()
    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler{ _, exception ->
        onError("Exceptoin handled: ${exception.localizedMessage}")
    }
    private val loading = MutableLiveData<Boolean>()

    fun getAllPizzaLocations() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = yelpRepository.getAllPizzaLocations()
            withContext(Dispatchers.Main) {
                if(response.isSuccessful){
                    pizzaData.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error: ${response.message()} ")
                }
            }
        }
    }

    fun getAllBeerLocations() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = yelpRepository.getAllBeerLocations()
            withContext(Dispatchers.Main) {
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
            }
        }
    }

    private fun onError(message: String) {
        try{
            errorMessage.value = message
            loading.value = false
        } catch(e: Exception) { Log.d(MainViewModel::class.java.name, e.localizedMessage) }

    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}