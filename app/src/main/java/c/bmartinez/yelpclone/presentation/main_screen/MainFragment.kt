package c.bmartinez.yelpclone.ui.views.mainfragment

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import c.bmartinez.yelpclone.R
import c.bmartinez.yelpclone.network.repository.YelpRepository
import c.bmartinez.yelpclone.network.services.RetrofitService
import c.bmartinez.yelpclone.data.viewmodels.MainViewModel
import c.bmartinez.yelpclone.data.viewmodels.MyViewModelFactory
import c.bmartinez.yelpclone.ui.components.mainfrag.ParentFragRecyclerView
import c.bmartinez.yelpclone.utils.LocationUtils
import c.bmartinez.yelpclone.utils.REQUEST_LOCATION_INTERVAL
import c.bmartinez.yelpclone.utils.SharedPreferencesUtils
import com.google.android.gms.location.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.Exception

class MainFragment: Fragment() {

    private val TAG = MainFragment::class.java.name
    lateinit var viewModel: MainViewModel
    lateinit var menu: Menu
    private lateinit var retrofitService: RetrofitService
    private lateinit var yelpRepository: YelpRepository

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    private var isPermitted: Int = 0

    private var isProgressDisplayed: Boolean = false

    @ExperimentalFoundationApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "Inside onCreateView()")
        //val view = inflater.inflate(R.layout.main_frag_layout, container, false)
        isPermitted = SharedPreferencesUtils.getIntegerPref(requireContext(), SharedPreferencesUtils().LOCATION_PERMISSION_SPF, SharedPreferencesUtils().LOCATION_GRANTED, 0)!!
        val view: View

        if(isPermitted == 1){
            //isProgressDisplayed = viewModel.loading.value
            view = ComposeView(requireContext()).apply {
                setContent {
                    val popularLocations = viewModel.popularLocationResults.value
                    val searchQueryTerm = remember { mutableStateOf("") }
                    val keyBoardController = LocalFocusManager.current
                    val navController = findNavController()

                    Column {
                        ToolBarMain(query = searchQueryTerm, keyboard = keyBoardController)
                        ParentFragRecyclerView(popularLocations = popularLocations, navigationController = navController)
                    }
                }
            }
        } else{
            view = ComposeView(requireContext()).apply {
                setContent {

                }
            }
        }
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "Inside onViewCreated()")
        setHasOptionsMenu(true)

        initView(isPermitted)
    }

    @Composable
    fun ToolBarMain(query: MutableState<String>, keyboard: FocusManager) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            elevation = 8.dp,
        ){
            Column {
                Row(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .padding(8.dp),
                        value = query.value,
                        onValueChange = { newValue ->
                            viewModel.onSearchTermChanged(newValue)
                        },
                        label = {
                            Text(text = "Search")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Search
                        ),
                        leadingIcon = {
                            Icon(Icons.Filled.Search, contentDescription = null)
                        },
//                    trailingIcon = {
//                        if (query.value != TextFieldValue("").text) {
//                            IconButton(
//                                onClick = {
//                                    //viewModel.onSearchTermChanged("")
//                                }
//                            ) {
//                                Icon(
//                                    Icons.Default.Close,
//                                    contentDescription = null,
//                                    modifier = Modifier
//                                        .padding(15.dp)
//                                        .size(24.dp)
//                                )
//                            }
//                        }
//                    },
                        keyboardActions = KeyboardActions(onSearch = {
                            //viewModel.getSearchResults(searchQueryTerm.value)
                            keyboard.clearFocus(true)
                        }),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = MaterialTheme.colors.surface,
                            cursorColor = Color.Black,

                            ),
                        textStyle = TextStyle(color = MaterialTheme.colors.onSurface)
                    )
                }
            }

        }
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
                    //searchData(input)
                    return false
                }
                return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }

    private fun initView(isPermitted: Int){

        if(isPermitted == 1){
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
            retrofitService = RetrofitService.getInstance(requireContext())
            yelpRepository = YelpRepository(retrofitService)
            viewModel = ViewModelProvider(this, MyViewModelFactory(yelpRepository)).get(MainViewModel::class.java)

            Log.d(TAG, "Before calling getLastLocation()")
            CoroutineScope(IO).launch { getLastLocation(requireContext()) }
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
                                CoroutineScope(IO).launch { viewModel.getPopularLocations(location.latitude, location.longitude) }
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
        locationRequest.interval = REQUEST_LOCATION_INTERVAL
        locationRequest.fastestInterval = 0
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private val locationCallback = object: LocationCallback() {
        override fun onLocationResult(location: LocationResult) {
            SharedPreferencesUtils.setFloatPref(context!!, SharedPreferencesUtils().COORDINATES_SPF, SharedPreferencesUtils().COORDINATES_LAT, location.lastLocation.latitude.toFloat())
            SharedPreferencesUtils.setFloatPref(context!!, SharedPreferencesUtils().COORDINATES_SPF, SharedPreferencesUtils().COORDINATES_LONG, location.lastLocation.longitude.toFloat())
            CoroutineScope(IO).launch { viewModel.getPopularLocations(location.lastLocation.latitude, location.lastLocation.longitude) }
        }
    }

//    private fun searchData(searchTerm: String){
//        val latitude: Double? = SharedPreferencesUtils.getFloatPref(requireContext(), SharedPreferencesUtils().COORDINATES_SPF, SharedPreferencesUtils().COORDINATES_LAT, 0.0f)?.toDouble()
//        val longitude: Double? = SharedPreferencesUtils.getFloatPref(requireContext(), SharedPreferencesUtils().COORDINATES_SPF, SharedPreferencesUtils().COORDINATES_LONG, 0.0f)?.toDouble()
//        if(latitude != 0.0 || longitude != 0.0){
//            progressDialog.visibility = View.VISIBLE
//            viewModel.getSearchResults(searchTerm, latitude, longitude)
//            viewModel.data.observe(this, {
//                if(it.isEmpty()){
//                    Log.d(TAG, "Search came back empty")
//                    progressDialog.visibility = View.GONE
//                } else {
//                    if(data.isNotEmpty()){
//                        data.clear()
//                    }
//                    data.addAll(it)
//                    progressDialog.visibility = View.GONE
//                    adapter.notifyDataSetChanged()
//                }
//            })
//        } else {
//            Toast.makeText(requireContext(), "No last location coordinates available.", Toast.LENGTH_LONG).show()
//        }
//    }

}