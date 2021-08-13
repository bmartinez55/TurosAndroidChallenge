package c.bmartinez.yelpclone.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
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

    private var isPermitted: Int = 0

    //Variables for location services turned off
    private lateinit var nonLocImageView: ImageView
    private lateinit var nonLocTextView: TextView

    companion object {
        const val REQUEST_INTERVAL: Long = 1000 * 60 * 30
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "Inside onCreateView()")
        return inflater.inflate(R.layout.main_frag_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "Inside onViewCreated()")
        setHasOptionsMenu(true)

        initView(view)
    }

    @SuppressLint("CommitPrefEdits")
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Inside onResume")

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

    private fun initView(view: View){
        recyclerView = view.findViewById(R.id.recyclerView)
        progressDialog = view.findViewById(R.id.progress_dialog)
        nonLocImageView = view.findViewById(R.id.nonLocImageView)
        nonLocTextView = view.findViewById(R.id.nonLocTextView)

        isPermitted = SharedPreferencesUtils.getIntegerPref(requireContext(), SharedPreferencesUtils().LOCATION_PERMISSION_SPF, SharedPreferencesUtils().LOCATION_GRANTED, 0)!!

        if(isPermitted == 1){
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

            recyclerView.visibility = View.GONE
            progressDialog.visibility = View.VISIBLE
            nonLocImageView.visibility = View.GONE
            nonLocTextView.visibility = View.GONE

            adapter = ResultsAdapter(requireContext(), data)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            retrofitService = RetrofitService.getInstance()
            yelpRepository = YelpRepository(retrofitService)
            viewModel = ViewModelProvider(this, MyViewModelFactory(yelpRepository)).get(MainViewModel::class.java)

            Log.d(TAG, "Before calling getLastLocation()")
            GlobalScope.launch(Dispatchers.IO) { getLastLocation(requireContext()) }
        } else {
            recyclerView.visibility = View.GONE
            progressDialog.visibility = View.GONE
            nonLocImageView.visibility = View.VISIBLE
            nonLocTextView.visibility = View.VISIBLE
        }
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
                                SharedPreferencesUtils.setFloatPref(context, SharedPreferencesUtils().COORDINATES_SPF, SharedPreferencesUtils().COORDINATES_LAT, location.latitude.toFloat())
                                SharedPreferencesUtils.setFloatPref(context, SharedPreferencesUtils().COORDINATES_SPF, SharedPreferencesUtils().COORDINATES_LONG, location.longitude.toFloat())
                            }
                        }
                        .addOnFailureListener {
                            Log.d(TAG, "Landed on the Failure listener")
                        }
                } else {
                    Log.d(TAG, "Location Service is off. Turning it on...")
                    getNewLocation()
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
            SharedPreferencesUtils.setFloatPref(context!!, SharedPreferencesUtils().COORDINATES_SPF, SharedPreferencesUtils().COORDINATES_LAT, location.lastLocation.latitude.toFloat())
            SharedPreferencesUtils.setFloatPref(context!!, SharedPreferencesUtils().COORDINATES_SPF, SharedPreferencesUtils().COORDINATES_LONG, location.lastLocation.longitude.toFloat())
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
        val latitude: Double? = SharedPreferencesUtils.getFloatPref(requireContext(), SharedPreferencesUtils().COORDINATES_SPF, SharedPreferencesUtils().COORDINATES_LAT, 0.0f)?.toDouble()
        val longitude: Double? = SharedPreferencesUtils.getFloatPref(requireContext(), SharedPreferencesUtils().COORDINATES_SPF, SharedPreferencesUtils().COORDINATES_LONG, 0.0f)?.toDouble()
        if(latitude != 0.0 || longitude != 0.0){
            progressDialog.visibility = View.VISIBLE
            viewModel.getSearchResults(searchTerm, latitude, longitude)
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
        } else {
            Toast.makeText(requireContext(), "No last location coordinates available.", Toast.LENGTH_LONG).show()
        }
    }

}