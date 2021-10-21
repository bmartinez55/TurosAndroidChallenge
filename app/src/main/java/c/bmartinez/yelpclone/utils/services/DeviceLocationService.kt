package c.bmartinez.yelpclone.utils.services

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.os.Looper
import android.util.Log
import c.bmartinez.yelpclone.utils.LocationUtils
import c.bmartinez.yelpclone.utils.SharedPreferencesUtils
import c.bmartinez.yelpclone.utils.YelpConstants
import com.google.android.gms.location.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.Exception

class DeviceLocationService: Service() {
    companion object {
        private val TAG = DeviceLocationService::class.java.name
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var context: Context

    override fun onCreate() {
        super.onCreate()
        context = this
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        CoroutineScope(IO).launch { getLastLocation(context) }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
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
                                Log.d(TAG, "Inside getLastLocation()...")
                                Log.d(TAG, "Latitude: ${location.latitude} Longitude: ${location.longitude}")
                                SharedPreferencesUtils.setFloatPref(context, SharedPreferencesUtils.COORDINATES_SPF, SharedPreferencesUtils.COORDINATES_LAT, location.latitude.toFloat())
                                SharedPreferencesUtils.setFloatPref(context, SharedPreferencesUtils.COORDINATES_SPF, SharedPreferencesUtils.COORDINATES_LONG, location.longitude.toFloat())
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
        locationRequest.interval = YelpConstants.REQUEST_LOCATION_INTERVAL
        locationRequest.fastestInterval = 0
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private val locationCallback = object: LocationCallback() {
        override fun onLocationResult(location: LocationResult) {
            Log.d(TAG, "Inside locationCallback()...")
            Log.d(TAG, "Latitude: ${location.lastLocation.latitude} Longitude: ${location.lastLocation.longitude}")
            SharedPreferencesUtils.setFloatPref(context, SharedPreferencesUtils.COORDINATES_SPF, SharedPreferencesUtils.COORDINATES_LAT, location.lastLocation.latitude.toFloat())
            SharedPreferencesUtils.setFloatPref(context, SharedPreferencesUtils.COORDINATES_SPF, SharedPreferencesUtils.COORDINATES_LONG, location.lastLocation.longitude.toFloat())
        }
    }
}