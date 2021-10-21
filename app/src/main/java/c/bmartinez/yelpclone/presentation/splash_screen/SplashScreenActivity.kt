package c.bmartinez.yelpclone.presentation.splash_screen

import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import c.bmartinez.yelpclone.presentation.MainActivity
import c.bmartinez.yelpclone.presentation.splash_screen.components.SplashImage
import c.bmartinez.yelpclone.utils.LocationUtils
import c.bmartinez.yelpclone.utils.SharedPreferencesUtils
import c.bmartinez.yelpclone.utils.services.DeviceLocationService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreenActivity: ComponentActivity() {

    val TAG = SplashScreenActivity::class.java.name

    companion object {
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99
    }

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column (
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                Arrangement.Center,
                Alignment.CenterHorizontally
            ) {
                SplashImage()
            }
        }
        Log.d(TAG, "Inside onCreate()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Inside onResume()")

        Handler(Looper.getMainLooper()).postDelayed({
            CoroutineScope(IO).launch { checkLocationPermissions() }
        }, 5000)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(LocationUtils().checkLocationPermissions(this)) {
                        CoroutineScope(IO).launch { startMainActivity(true) }
                    }
                } else {
                    Toast.makeText(this, "Permission Denied: certain features will not work properly", Toast.LENGTH_SHORT).show()
                    CoroutineScope(IO).launch { startMainActivity(false) }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun checkLocationPermissions(){
        if(!LocationUtils().checkLocationPermissions(this)) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
        } else {
            CoroutineScope(IO).launch { startMainActivity(true) }
        }
    }

    private suspend fun startMainActivity(permissionGranted: Boolean){
        Log.d(TAG, "Before switching to MainActivity")

        if(permissionGranted){
            startService(Intent(this, DeviceLocationService::class.java))
            SharedPreferencesUtils.setIntegerPref(applicationContext, SharedPreferencesUtils.LOCATION_PERMISSION_SPF, SharedPreferencesUtils.LOCATION_GRANTED, 1)
        }
        else { SharedPreferencesUtils.setIntegerPref(applicationContext, SharedPreferencesUtils.LOCATION_PERMISSION_SPF, SharedPreferencesUtils.LOCATION_GRANTED, 0)}

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}