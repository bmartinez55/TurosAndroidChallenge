package c.bmartinez.yelpclone.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import c.bmartinez.yelpclone.network.repository.YelpRepository
import java.lang.IllegalArgumentException

/*
    Helps creating an instance of viewmodel(s)
 */
class MyViewModelFactory constructor(private val repository: YelpRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.repository) as T
        }
        else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}