package c.bmartinez.yelpclone.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import c.bmartinez.yelpclone.R

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}