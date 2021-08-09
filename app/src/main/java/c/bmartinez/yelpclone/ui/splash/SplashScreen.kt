package c.bmartinez.yelpclone.ui.splash

import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import c.bmartinez.yelpclone.R
import c.bmartinez.yelpclone.ui.views.MainActivity
import c.bmartinez.yelpclone.utils.LocationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SplashScreen: AppCompatActivity() {

    val TAG = SplashScreen::class.java.name

    companion object {
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Log.d(TAG, "Inside onCreate()")

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Inside onResume()")

        Handler(Looper.getMainLooper()).postDelayed({
            GlobalScope.launch(Dispatchers.IO) { checkLocationPermissions() }
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
                        startMainActivity()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied: certain features will not work properly", Toast.LENGTH_SHORT).show()
                    startMainActivity()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun checkLocationPermissions(){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
        } else {
            startMainActivity()
        }
    }

    private fun startMainActivity(){
        Log.d(TAG, "Before switching to MainActivity")
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}