package c.bmartinez.turosandroidchallenge.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import c.bmartinez.turosandroidchallenge.data.repo.YelpRepository
import java.lang.IllegalArgumentException

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