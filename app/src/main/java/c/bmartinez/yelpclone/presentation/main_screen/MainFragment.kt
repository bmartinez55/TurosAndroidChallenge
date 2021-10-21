@file:Suppress("PackageDirectoryMismatch")
//package c.bmartinez.yelpclone.presentation.main_screen
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.location.Location
//import android.os.Bundle
//import android.os.Looper
//import android.util.Log
//import android.view.*
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.*
//import androidx.compose.material.Icon
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.material.TextField
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.focus.FocusManager
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.ComposeView
//import androidx.compose.ui.platform.LocalFocusManager
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//import c.bmartinez.yelpclone.utils.LocationUtils
//import c.bmartinez.yelpclone.utils.SharedPreferencesUtils
//import c.bmartinez.yelpclone.utils.YelpConstants
//import com.google.android.gms.location.*
//import dagger.hilt.android.AndroidEntryPoint
//import java.lang.Exception
//
//@AndroidEntryPoint
//class MainFragment: Fragment() {
//
//    private val TAG = MainFragment::class.java.name
//
//    private var isPermitted: Int = 0
//
//
//    @ExperimentalFoundationApi
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        Log.d(TAG, "Inside onCreateView()")
//        isPermitted = SharedPreferencesUtils.getIntegerPref(requireContext(), SharedPreferencesUtils.LOCATION_PERMISSION_SPF, SharedPreferencesUtils.LOCATION_GRANTED, 0)!!
//        val view: View
//
//        if(isPermitted == 1){
//            view = ComposeView(requireContext()).apply {
//                setContent {
//                    //val popularLocations = viewModel.popularLocationResults.value
//                    val searchQueryTerm = remember { mutableStateOf("") }
//                    val keyBoardController = LocalFocusManager.current
//                    val navController = findNavController()
//
//                    Column {
//                        ToolBarMain(query = searchQueryTerm, keyboard = keyBoardController)
//                        //ParentFragRecyclerView(popularLocations = popularLocations, navigationController = navController)
//                    }
//                }
//            }
//        } else{
//            view = ComposeView(requireContext()).apply {
//                setContent {
//
//                }
//            }
//        }
//        return view
//    }
//
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        Log.d(TAG, "Inside onViewCreated()")
//        setHasOptionsMenu(true)
//
//        //initView(isPermitted)
//    }
//
//
//
//    @SuppressLint("CommitPrefEdits")
//    override fun onResume() {
//        super.onResume()
//        Log.d(TAG, "Inside onResume")
//
//    }
//
////    private fun initView(isPermitted: Int){
////
////        if(isPermitted == 1){
////            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
////            retrofitService = RetrofitService.getInstance(requireContext())
////            yelpRepository = YelpRepository(retrofitService)
////            viewModel = ViewModelProvider(this, MyViewModelFactory(yelpRepository)).get(MainViewModel::class.java)
////
////            Log.d(TAG, "Before calling getLastLocation()")
////            CoroutineScope(IO).launch { getLastLocation(requireContext()) }
////        }
////    }
//
////    private fun searchData(searchTerm: String){
////        val latitude: Double? = SharedPreferencesUtils.getFloatPref(requireContext(), SharedPreferencesUtils().COORDINATES_SPF, SharedPreferencesUtils().COORDINATES_LAT, 0.0f)?.toDouble()
////        val longitude: Double? = SharedPreferencesUtils.getFloatPref(requireContext(), SharedPreferencesUtils().COORDINATES_SPF, SharedPreferencesUtils().COORDINATES_LONG, 0.0f)?.toDouble()
////        if(latitude != 0.0 || longitude != 0.0){
////            progressDialog.visibility = View.VISIBLE
////            viewModel.getSearchResults(searchTerm, latitude, longitude)
////            viewModel.data.observe(this, {
////                if(it.isEmpty()){
////                    Log.d(TAG, "Search came back empty")
////                    progressDialog.visibility = View.GONE
////                } else {
////                    if(data.isNotEmpty()){
////                        data.clear()
////                    }
////                    data.addAll(it)
////                    progressDialog.visibility = View.GONE
////                    adapter.notifyDataSetChanged()
////                }
////            })
////        } else {
////            Toast.makeText(requireContext(), "No last location coordinates available.", Toast.LENGTH_LONG).show()
////        }
////    }
//
//}