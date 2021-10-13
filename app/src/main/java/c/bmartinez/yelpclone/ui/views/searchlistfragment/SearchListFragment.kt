package c.bmartinez.yelpclone.ui.views.searchlistfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import c.bmartinez.yelpclone.data.viewmodels.MainViewModel
import c.bmartinez.yelpclone.data.viewmodels.MyViewModelFactory
import c.bmartinez.yelpclone.network.repository.YelpRepository
import c.bmartinez.yelpclone.network.services.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchListFragment: Fragment() {

    private val TAG = SearchListFragment::class.java.name

    private lateinit var searchTerm: String
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    lateinit var viewModel: MainViewModel
    private lateinit var retrofitService: RetrofitService
    private lateinit var yelpRepository: YelpRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getString("searchTerm")?.let { term ->
            this.searchTerm = term
        }
        arguments?.getDouble("latitude")?.let { lat ->
            this.latitude = lat
        }
        arguments?.getDouble("longitude")?.let { long ->
            this.longitude = long
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val searchData = viewModel.searchBusinessesResults.value
                val navController = findNavController()

                Column(modifier = Modifier.fillMaxSize()) {

                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateView()
    }

    private fun populateView() {
        retrofitService = RetrofitService.getInstance(requireContext())
        yelpRepository = YelpRepository(retrofitService)
        viewModel = ViewModelProvider(this, MyViewModelFactory(yelpRepository)).get(MainViewModel::class.java)

        Log.d(TAG, "Before calling getSearchResults")
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getSearchResults(
                searchTerm = searchTerm,
                latitude = latitude,
                longitude = longitude
            )
        }
    }
}