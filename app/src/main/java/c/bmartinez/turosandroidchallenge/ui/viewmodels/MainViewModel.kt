package c.bmartinez.turosandroidchallenge.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import c.bmartinez.turosandroidchallenge.data.model.YelpSearchBeerResults
import c.bmartinez.turosandroidchallenge.data.model.YelpSearchPizzaResults
import c.bmartinez.turosandroidchallenge.data.repo.YelpRepository
import kotlinx.coroutines.*

class MainViewModel constructor(private val yelpRepository: YelpRepository): ViewModel() {

    private val errorMessage = MutableLiveData<String>()
    private val pizzaData = MutableLiveData<List<YelpSearchPizzaResults>>()
    private val beerData = MutableLiveData<List<YelpSearchBeerResults>>()
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
                    onError("Error: ${response.message()}")
                }
            }
        }
    }

    fun getAllBeerLocations() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = yelpRepository.getAllBeerLocations()
            withContext(Dispatchers.Main) {
                if(response.isSuccessful){
                    beerData.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error: ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}