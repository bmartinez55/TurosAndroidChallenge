package c.bmartinez.yelpclone.ui.splash

import android.content.Intent
import android.os.*
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import c.bmartinez.yelpclone.R
import c.bmartinez.yelpclone.ui.views.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.core.widget.ImageViewCompat as ImageViewCompat1

class SplashScreen: AppCompatActivity() {

    val TAG = SplashScreen::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Log.d(TAG, "Inside onCreate()")
        Log.d(TAG, "After setting the content view")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Inside onResume()")
        Log.d(TAG, "Before calling GlobalScope")
        val mContext = this.applicationContext
        GlobalScope.launch(Dispatchers.IO) {
            Handler(Looper.getMainLooper()).postDelayed({
                Log.d(TAG, "Inside the handler")
                val intent = Intent(mContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 10000)
        }
    }
}