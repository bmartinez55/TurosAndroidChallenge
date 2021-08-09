package c.bmartinez.yelpclone.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.bmartinez.yelpclone.R
import c.bmartinez.yelpclone.data.model.Results
import c.bmartinez.yelpclone.data.repo.YelpRepository
import c.bmartinez.yelpclone.data.services.RetrofitService
import c.bmartinez.yelpclone.ui.viewmodels.MainViewModel
import c.bmartinez.yelpclone.ui.viewmodels.MyViewModelFactory
import c.bmartinez.yelpclone.utils.LocationUtils
import c.bmartinez.yelpclone.utils.SharedPreferencesUtils
import com.google.android.gms.location.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainFragment: Fragment() {

    private val TAG = MainFragment::class.java.name
    lateinit var viewModel: MainViewModel
    lateinit var adapter: ResultsAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progressDialog: ProgressBar
    lateinit var menu: Menu
    private lateinit var retrofitService: RetrofitService
    private lateinit var yelpRepository: YelpRepository
    var data = mutableListOf<Results>()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private var sharedPreferences: SharedPreferences? = null

    companion object {
        val REQUEST_INTERVAL: Long = 1000 * 60 * 30
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "Inside onCreateView()")
        return inflater.inflate(R.layout.main_frag_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "Inside onViewCreated()")
        setHasOptionsMenu(true)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        sharedPreferences = context?.getSharedPreferences(SharedPreferencesUtils().COORDINATES_SPF, Context.MODE_PRIVATE)

        recyclerView = view.findViewById(R.id.recyclerView)
        progressDialog = view.findViewById(R.id.progress_dialog)
        recyclerView.visibility = View.GONE
        progressDialog.visibility = View.VISIBLE
        adapter = ResultsAdapter(this.requireContext(), data)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        retrofitService = RetrofitService.getInstance()
        yelpRepository = YelpRepository(retrofitService)
        viewModel = ViewModelProvider(this, MyViewModelFactory(yelpRepository)).get(MainViewModel::class.java)

        Log.d(TAG, "Before calling getLastLocation()")
        GlobalScope.launch(Dispatchers.IO) { getLastLocation(requireContext()) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        this.menu = menu
        menu.clear()
        inflater.inflate(R.menu.main_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.search)
        val searchView: androidx.appcompat.widget.SearchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(input: String?): Boolean {
                if(!input.isNullOrBlank()){
                    searchData(input)
                    return false
                }
                return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(context: Context){
        try {
            if(LocationUtils().checkLocationPermissions(context)) {
                if(LocationUtils().isLocationEnabled(context)) {
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location: Location? ->
                            if(location == null){
                                getNewLocation()
                            } else {
                                getPopularLocations(location.latitude, location.longitude)
                                sharedPreferences?.edit()?.putFloat(SharedPreferencesUtils().COORDINATES_LAT, location.latitude.toFloat())?.apply()
                                sharedPreferences?.edit()?.putFloat(SharedPreferencesUtils().COORDINATES_LONG, location.longitude.toFloat())?.apply()
                            }
                        }
                        .addOnFailureListener {
                            Log.d(TAG, "Landed on the Failure listener")
                        }
                }

            }
        } catch(e: Exception) { Log.d(TAG, e.stackTraceToString()) }
    }

    @SuppressLint("MissingPermission")
    private fun getNewLocation() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = REQUEST_INTERVAL
        locationRequest.fastestInterval = 0
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private val locationCallback = object: LocationCallback() {
        override fun onLocationResult(location: LocationResult) {
            getPopularLocations(location.lastLocation.latitude, location.lastLocation.longitude)
        }
    }

    private fun getPopularLocations(latitude: Double?, longitude: Double?) {
        viewModel.getPopularLocations(latitude, longitude)
        viewModel.data.observe(viewLifecycleOwner, {
            if(it.isEmpty()){
                Log.d(TAG, "Search came back empty")
            } else {
                if(data.isNotEmpty()){
                    data.clear()
                }
                data.addAll(it)
                progressDialog.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun searchData(searchTerm: String){
        progressDialog.visibility = View.VISIBLE
        viewModel.getSearchResults(searchTerm)
        viewModel.data.observe(this, {
            if(it.isEmpty()){
                Log.d(TAG, "Search came back empty")
                progressDialog.visibility = View.GONE
            } else {
                if(data.isNotEmpty()){
                    data.clear()
                }
                data.addAll(it)
                progressDialog.visibility = View.GONE
                adapter.notifyDataSetChanged()
            }
        })
    }


}